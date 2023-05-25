package com.gbtech.emails.model.repository.entity;

import java.io.Serializable;
import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EmailRecipientEntityId implements Serializable {

	private static final long serialVersionUID = 4728774898281450845L;

	@NonNull
	@Column(name = "id")
	private BigInteger id;

	@NonNull
	@Column(name = "email_id")
	private BigInteger idEmail;

}
