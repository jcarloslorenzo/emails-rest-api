package com.gbtech.emails.queues.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbtech.emails.queues.service.EmailQueueService;
import com.gbtech.emails.queues.service.bean.EmailQueueBean;

/**
 * The Class EmailQueueServiceImpl.
 */
@Service
public class EmailQueueServiceImpl implements EmailQueueService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailQueueServiceImpl.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendEmail(final EmailQueueBean email) throws JsonProcessingException, AmqpException {
		final ObjectMapper objectMapper = new ObjectMapper();
		this.rabbitTemplate.convertAndSend(objectMapper.writeValueAsString(email));
	}

	/**
	 * Receive messages throught a RabbitMQ queue.<br>
	 *
	 * This method is just a simulation of how messages can be received from a queue.<br>
	 * Nothing is going to be done with it because the mail received is the same one that the application sent to the
	 * queue a few moments ago (done on purpose with {@link #sendEmail(EmailQueueBean)}).<br>
	 * But, in a real production scenario, at this point the received email should be a new one and it should be stored
	 * in the database.
	 *
	 * @param incomingMessage the inncoming message from the queue.
	 */
	@RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
	protected void receiveIncomingMessages(final String incomingMessage) {
		LOGGER.info("New incoming message {}", incomingMessage);
		try {
			final EmailQueueBean receivedEmail = new ObjectMapper().readValue(incomingMessage, EmailQueueBean.class);
			LOGGER.info("Email received {}", receivedEmail);

		} catch (final JsonProcessingException e) {
			LOGGER.error("An error occurred during the transformation of the message received via the input queue.", e);
		}
	}

}
