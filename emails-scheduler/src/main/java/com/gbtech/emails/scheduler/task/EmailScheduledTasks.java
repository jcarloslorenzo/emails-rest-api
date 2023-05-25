package com.gbtech.emails.scheduler.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gbtech.emails.model.service.EmailService;
import com.gbtech.emails.model.service.enums.EmailState;
import com.gbtech.emails.model.service.exceptions.ActionNotAllowedException;
import com.gbtech.emails.model.service.vo.EmailFilterVO;
import com.gbtech.emails.model.service.vo.EmailVO;

@Component
public class EmailScheduledTasks {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailScheduledTasks.class);

	@Autowired
	private EmailService emailService;

	/**
	 * Scheduled task to scan for potential spam emails.<br>
	 * Search for all emails whose sender is 'carl@gbtec.es' and whose status is different from {@link EmailState#SPAM}
	 * to assign that status to them.<br>
	 * <br>
	 * This task runs every day at 10 am.
	 */
	@Scheduled(cron = "0 0 10 ? * * ")
	public void cleanSpam() {

		final EmailFilterVO filter = new EmailFilterVO();
		filter.setSender("carl@gbtec.es");
		filter.getStates().addAll(EmailState.nonSpamStatesCodes());

		final List<EmailVO> emails = this.emailService.searchEmails(filter);
		LOGGER.debug("Found {} spam emails", emails.size());
		emails.forEach(email -> email.setState(EmailState.SPAM.getValue()));
		try {
			this.emailService.saveAll(emails);
		} catch (final ActionNotAllowedException e) {
			LOGGER.error("An error occurred while marking emails as SPAM.", e);
		}
	}

}
