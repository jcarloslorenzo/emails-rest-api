package com.gbtech.emails.model.utils;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;

import com.gbtech.emails.model.repository.entity.EmailEntity;
import com.gbtech.emails.model.repository.entity.EmailRecipientEntity;
import com.gbtech.emails.model.repository.entity.EmailRecipientEntityId;

/**
 * Utility class for emails.
 */
public class EmailUtils {

	/**
	 * Sets the email recipient ids.
	 *
	 * @param entity the new email recipient ids
	 */
	public static void setEmailRecipientIds(final EmailEntity entity) {
		EmailUtils.setRecipientIds(entity.getTo(), entity.getId());
		EmailUtils.setRecipientIds(entity.getCc(), entity.getId());
		EmailUtils.setRecipientIds(entity.getBcc(), entity.getId());
	}

	private static <T extends EmailRecipientEntity> void setRecipientIds(final List<T> recipientList,
			final BigInteger emailId) {
		if (CollectionUtils.isNotEmpty(recipientList)) {
			IntStream.range(0, recipientList.size())
					.forEach(index -> recipientList.get(index).setId(
							new EmailRecipientEntityId(BigInteger.valueOf(index + 1), emailId)));
		}
	}

}
