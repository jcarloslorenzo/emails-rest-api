package com.gbtech.emails.model.repository.entity;

import java.io.Serializable;

import com.gbtech.emails.model.repository.listeners.EmailRecipientListener;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode(of = "address")
@EntityListeners(EmailRecipientListener.class)
public abstract class EmailRecipientEntity implements Serializable {

	private static final long serialVersionUID = -5668578423476667508L;

	@EmbeddedId
	private EmailRecipientEntityId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(insertable = false, updatable = false, name = "email_id")
	private EmailEntity email;

	@Column(name = "address")
	private String address;

}
