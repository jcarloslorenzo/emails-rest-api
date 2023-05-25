package com.gbtech.emails.model.repository.entity;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.gbtech.emails.model.repository.listeners.EmailEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(schema = "public", name = "email")
@EntityListeners(EmailEntityListener.class)
@NamedQueries({
		@NamedQuery(name = "EmailEntity.searchEmails",
			query = "SELECT email "
					+ "FROM EmailEntity email "
					+ "WHERE (cast(:fromDate as date) IS NULL OR email.creationDate >= :fromDate) "
					+ "AND (cast(:toDate as date) IS NULL OR email.creationDate <= :toDate) "
					+ "AND (:sender IS NULL OR email.from like :sender) "
					+ "AND ((coalesce(:states) IS NULL AND email.state !=3) OR email.state IN (:states))"
					+ "ORDER BY email.id ASC")
})
public class EmailEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private BigInteger id;

	@Column(name = "sender")
	private String from;

	@Column(name = "subject")
	private String subject;

	@Column(name = "body")
	private String body;

	@Column(name = "state")
	private Short state;

	@Column(name = "creation_date")
	private Timestamp creationDate;

	@Column(name = "modification_date")
	private Timestamp modificationDate;

	@OneToMany(mappedBy = "email", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<EmailRecipientToEntity> to;

	@OneToMany(mappedBy = "email", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<EmailRecipientCcEntity> cc;

	@OneToMany(mappedBy = "email", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<EmailRecipientBccEntity> bcc;
}
