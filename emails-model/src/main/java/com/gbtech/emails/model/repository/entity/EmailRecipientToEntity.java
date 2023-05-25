package com.gbtech.emails.model.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(schema = "public", name = "email_recipient_to")
public class EmailRecipientToEntity extends EmailRecipientEntity {

	private static final long serialVersionUID = -5364685789457150316L;

}
