package com.gbtech.emails.model.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(schema = "public", name = "email_recipient_cc")
public class EmailRecipientCcEntity extends EmailRecipientEntity {

	private static final long serialVersionUID = -1327365987963763263L;

}
