package com.gbtech.emails.model.repository.listeners;

import java.sql.Timestamp;
import java.time.Instant;

import com.gbtech.emails.model.repository.entity.EmailRecipientEntity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class EmailRecipientListener {

	/**
	 * Perform pre persist actions.
	 *
	 * @param entity the entity
	 */
	@PrePersist
	public void performPrePersistActions(final EmailRecipientEntity entity) {
		if (entity.getEmail() != null) {
			if (entity.getEmail().getId() == null) {
				entity.getEmail().setCreationDate(Timestamp.from(Instant.now()));
			} else {
				entity.getEmail().setModificationDate(Timestamp.from(Instant.now()));
			}
		}
	}

	/**
	 * Perform pre update actions.
	 *
	 * @param entity the entity
	 */
	@PreUpdate
	public void performPreUpdateActions(final EmailRecipientEntity entity) {
		if (entity.getEmail() != null) {
			entity.getEmail().setModificationDate(Timestamp.from(Instant.now()));
		}
	}
}
