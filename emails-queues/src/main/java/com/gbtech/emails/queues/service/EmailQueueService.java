package com.gbtech.emails.queues.service;

import org.springframework.amqp.AmqpException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gbtech.emails.queues.service.bean.EmailQueueBean;

/**
 * The EmailPublisherService.
 */
public interface EmailQueueService {

	/**
	 * Send an email to a Rabbitmq queue.
	 *
	 * @param email the email
	 * @throws JsonProcessingException the json processing exception
	 * @throws AmqpException           the amqp exception
	 */
	void sendEmail(EmailQueueBean email) throws JsonProcessingException, AmqpException;

}
