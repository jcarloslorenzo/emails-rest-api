package com.gbtech.emails.model.service;

import java.math.BigInteger;
import java.util.List;

import com.gbtech.emails.model.service.enums.EmailState;
import com.gbtech.emails.model.service.exceptions.ActionNotAllowedException;
import com.gbtech.emails.model.service.exceptions.DataNotFoundException;
import com.gbtech.emails.model.service.exceptions.StateErrorException;
import com.gbtech.emails.model.service.vo.EmailFilterVO;
import com.gbtech.emails.model.service.vo.EmailVO;

/**
 * The Interface EmailService.
 */
public interface EmailService {

	/**
	 * Searches for emails that meet the requirements set in the filter.
	 *
	 * @param filter The filter with the requirements that the emails must meet.
	 * @return The list of emails found.
	 */
	List<EmailVO> searchEmails(EmailFilterVO filter);

	/**
	 * Gets the email with the id received as a parameter
	 *
	 * @param emailId The email id
	 * @return The email found.
	 * @throws DataNotFoundException If the id does not correspond to any email
	 */
	EmailVO getById(BigInteger emailId) throws DataNotFoundException;

	/**
	 * Save the received email.
	 *
	 * @param email The email to save.
	 * @return The saved email.
	 * @throws ActionNotAllowedException If the email cannot be saved.
	 */
	EmailVO save(EmailVO email) throws ActionNotAllowedException;

	/**
	 * Save all the received emails.
	 *
	 * @param emailList The emails to save.
	 * @return The list of saved emails.
	 * @throws ActionNotAllowedException If any email cannot be saved.
	 */
	List<EmailVO> saveAll(List<EmailVO> emailList) throws ActionNotAllowedException;

	/**
	 * Updates the recieved email.<br>
	 * Only emails with state {@link EmailState#DRAFT} can be update.
	 *
	 * @param emailId the id of the email that will be updated
	 * @param email   the new content of the email to be updated.
	 * @return The updated email
	 * @throws DataNotFoundException     If no email is found with the id received as a parameter.
	 * @throws StateErrorException       If the email state is not {@link EmailState#DRAFT}.
	 * @throws ActionNotAllowedException If the email cannot be updated.
	 */
	EmailVO update(BigInteger emailId, EmailVO email)
			throws DataNotFoundException, StateErrorException, ActionNotAllowedException;

	/**
	 * Updates all recieved email.<br>
	 * Only emails with state {@link EmailState#DRAFT} can be update.
	 *
	 * @param email the email
	 * @return the list
	 * @throws DataNotFoundException     If any of the emails are not found in the database.
	 * @throws StateErrorException       If any of the emails state are not {@link EmailState#DRAFT}.
	 * @throws ActionNotAllowedException If any of the emails cannot be updated.
	 */
	List<EmailVO> updateAll(List<EmailVO> email)
			throws DataNotFoundException, StateErrorException, ActionNotAllowedException;

	/**
	 * Sets all email states to {@link EmailState#DELETED}.
	 *
	 * @param emailIds the email ids to be delete.
	 * @throws DataNotFoundException     If any of the emails are not found in the database.
	 * @throws ActionNotAllowedException If any of the emails cannot be deleted.
	 */
	void delete(List<BigInteger> emailIds) throws DataNotFoundException, ActionNotAllowedException;

}
