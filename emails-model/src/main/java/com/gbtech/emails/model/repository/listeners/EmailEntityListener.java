package com.gbtech.emails.model.repository.listeners;

import java.sql.Timestamp;
import java.time.Instant;

import com.gbtech.emails.model.repository.entity.EmailEntity;
import com.gbtech.emails.model.service.enums.EmailState;
import com.gbtech.emails.model.utils.EmailUtils;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

/**
 * The listener interface for receiving emailEntity events. The class that is interested in processing a emailEntity
 * event implements this interface, and the object created with that class is registered with a component using the
 * component's <code>addEmailEntityListener<code> method. When the emailEntity event occurs, that object's appropriate
 * method is invoked.
 *
 * @see EmailEntityEvent
 *
 */
public class EmailEntityListener {

	/**
	 * Perform pre persist actions.
	 *
	 * @param entity the entity
	 */
	@PrePersist
	public void performPrePersistActions(final EmailEntity entity) {
		if (entity.getState() == null) {
			entity.setState(EmailState.DRAFT.getValue());
		}
		entity.setCreationDate(Timestamp.from(Instant.now()));
	}

	/**
	 * Perform pre update actions.
	 *
	 * @param entity the entity
	 */
	@PreUpdate
	public void performPreUpdateActions(final EmailEntity entity) {
		entity.setModificationDate(Timestamp.from(Instant.now()));
	}

	/**
	 * Perform post persist actions.
	 *
	 * @param entity the entity
	 */
	@PostPersist
	public void performPostPersistActions(final EmailEntity entity) {
		EmailUtils.setEmailRecipientIds(entity);
	}

}
