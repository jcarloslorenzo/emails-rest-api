package com.gbtech.emails.model.service.impl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gbtech.emails.model.repository.EmailRepository;
import com.gbtech.emails.model.repository.entity.EmailEntity;
import com.gbtech.emails.model.service.EmailService;
import com.gbtech.emails.model.service.enums.EmailState;
import com.gbtech.emails.model.service.exceptions.ActionNotAllowedException;
import com.gbtech.emails.model.service.exceptions.DataNotFoundException;
import com.gbtech.emails.model.service.exceptions.StateErrorException;
import com.gbtech.emails.model.service.vo.EmailFilterVO;
import com.gbtech.emails.model.service.vo.EmailVO;
import com.gbtech.emails.model.service.vo.mapper.EmailVOMapper;
import com.gbtech.emails.model.utils.EmailUtils;
import com.gbtech.emails.queues.service.EmailQueueService;

/**
 * The Class EmailServiceImpl.
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
public class EmailServiceImpl implements EmailService {

	/** The email repo. */
	@Autowired
	private EmailRepository emailRepo;

	@Autowired
	private EmailQueueService emailPublisherService;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EmailVO> searchEmails(final EmailFilterVO filter) {

		return EmailVOMapper.MAPPER.toVOList(
				this.emailRepo.searchEmails(
						filter.getDateFrom(),
						filter.getDateTo(),
						CollectionUtils.isEmpty(filter.getStates())
								? null
								: filter.getStates(),
						StringUtils.isNotBlank(filter.getSender())
								? filter.isAllowSenderPartialMatch()
										? String.join(StringUtils.EMPTY, "%", filter.getSender(), "%")
										: filter.getSender()
								: null));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EmailVO getById(final BigInteger emailId) {
		return EmailVOMapper.MAPPER.toVO(this.emailRepo.findById(emailId).orElse(null));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
		rollbackFor = { ActionNotAllowedException.class })
	public EmailVO save(final EmailVO vo) throws ActionNotAllowedException {
		try {
			return this.saveAll(Arrays.asList(vo)).get(0);
		} catch (final Exception e) {
			throw new ActionNotAllowedException(e.getCause());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
		rollbackFor = { ActionNotAllowedException.class })
	public List<EmailVO> saveAll(final List<EmailVO> voList) throws ActionNotAllowedException {

		for (final EmailVO emailVO : voList) {
			if (emailVO.getId() != null) {
				throw new ActionNotAllowedException("It is not allowed to define an id for new emails.");
			}
		}

		try {

			final List<EmailVO> savedEmails = EmailVOMapper.MAPPER.toVOList(
					this.emailRepo.saveAll(EmailVOMapper.MAPPER.toEntityList(voList)));

			savedEmails.stream()
					.filter(email -> EmailState.SENT.inState(email.getState()))
					.forEach(this::enqueueEmail);

			return savedEmails;

		} catch (final Exception e) {
			throw new ActionNotAllowedException(e.getCause());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
		rollbackFor = { DataNotFoundException.class, ActionNotAllowedException.class, StateErrorException.class })
	public EmailVO update(final BigInteger emailId, final EmailVO email)
			throws ActionNotAllowedException, DataNotFoundException, StateErrorException {
		try {
			return this.updateEmail(emailId, email);
		} catch (final RuntimeException e) {
			if (e.getCause() instanceof ActionNotAllowedException) {
				throw (ActionNotAllowedException) e.getCause();
			}
			if (e.getCause() instanceof StateErrorException) {
				throw (StateErrorException) e.getCause();
			}
			if (e.getCause() instanceof DataNotFoundException) {
				throw (DataNotFoundException) e.getCause();
			}
			LOGGER.error("ERROR", e);
			throw new ActionNotAllowedException(e.getCause());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
		rollbackFor = { DataNotFoundException.class, ActionNotAllowedException.class, StateErrorException.class })
	public List<EmailVO> updateAll(final List<EmailVO> voList)
			throws ActionNotAllowedException, StateErrorException, DataNotFoundException {
		try {
			return voList.stream().map(item -> this.updateEmail(item.getId(), item)).toList();
		} catch (final RuntimeException e) {
			if (e.getCause() instanceof ActionNotAllowedException) {
				throw (ActionNotAllowedException) e.getCause();
			}
			if (e.getCause() instanceof StateErrorException) {
				throw (StateErrorException) e.getCause();
			}

			if (e.getCause() instanceof DataNotFoundException) {
				throw (DataNotFoundException) e.getCause();
			}
			throw new ActionNotAllowedException(e.getCause());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
		rollbackFor = { DataNotFoundException.class, ActionNotAllowedException.class })
	public void delete(final List<BigInteger> emailIds) throws DataNotFoundException, ActionNotAllowedException {
		try {
			emailIds.stream().forEach(this::deleteEmail);
		} catch (final RuntimeException e) {
			if (e.getCause() instanceof DataNotFoundException) {
				throw (DataNotFoundException) e.getCause();
			}
			throw new ActionNotAllowedException(e.getCause());
		}
	}

	/**
	 * Convenience method to perform the update operation encapsulating possible errors inside a RuntimeException.
	 *
	 * @param emailId the email id
	 * @param emailVO the email VO
	 * @return the email VO
	 */
	private EmailVO updateEmail(final BigInteger emailId, final EmailVO emailVO) {

		try {
			if (!emailId.equals(emailVO.getId())) {
				throw new ActionNotAllowedException("The email id does not match the data to be updated. ");
			}
			final EmailEntity emailEntity = this.emailRepo.findById(emailId)
					.orElseThrow(() -> new DataNotFoundException(String.format("No email found with id %s", emailId)));

			if (!EmailState.DRAFT.inState(emailEntity.getState())) {
				throw new StateErrorException(
						String.format(
								"Only email in state 2 (Draft) can be updated. Current state of email %s is %s",
								emailId.intValue(), emailVO.getState()));
			}
			final EmailState oldState = EmailState.valueOf(emailEntity.getState());
			final EmailState newState = EmailState.valueOf(emailVO.getState());

			EmailVOMapper.MAPPER.update(emailEntity, emailVO);

			EmailUtils.setEmailRecipientIds(emailEntity);

			final EmailVO updatedEmailVO = EmailVOMapper.MAPPER.toVO(this.emailRepo.save(emailEntity));

			if (!Objects.equals(oldState, newState) && EmailState.SENT.equals(newState)) {
				this.enqueueEmail(updatedEmailVO);
			}

			return updatedEmailVO;

		} catch (final RuntimeException e) {
			throw e;
		} catch (final StateErrorException | DataNotFoundException | ActionNotAllowedException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Convenience method to perform the delete operation encapsulating possible errors inside a RuntimeException.
	 *
	 * @param emailId the email id to delete
	 */
	private void deleteEmail(final BigInteger emailId) {
		try {
			final EmailEntity entity =
					this.emailRepo.findById(emailId)
							.orElseThrow(() -> new DataNotFoundException(
									String.format("No email found with id %s", emailId)));
			entity.setState(EmailState.DELETED.getValue());
			if (EmailState.DELETED.inState(entity.getState())) {
				throw new ActionNotAllowedException(
						String.format("The email with id %s has already been deleted before.", emailId));
			}
			this.emailRepo.save(entity);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sends the email received as a parameter to the output queue.
	 *
	 * @param email The email to be queued.
	 */
	private void enqueueEmail(final EmailVO email) {
		LOGGER.debug("Sending email to the output queue.");

		try {
			this.emailPublisherService.sendEmail(EmailVOMapper.MAPPER.toQueueBean(email));
		} catch (JsonProcessingException | AmqpException e) {
			LOGGER.error("An error occurred while trying to send the email to the output queue.", e);
		}
	}

}
