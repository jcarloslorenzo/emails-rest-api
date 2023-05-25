package com.gbtech.emails.model.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gbtech.emails.model.repository.entity.EmailEntity;

public interface EmailRepository extends JpaRepository<EmailEntity, BigInteger> {

	/**
	 * Searches for emails that meet the requirements set by the parameters.
	 *
	 * @param fromDate Search Range Start Date.
	 * @param toDate   Search Range End Date.
	 * @param states   The list of allowed states.
	 * @param sender   The sender.
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	List<EmailEntity> searchEmails(Date fromDate, Date toDate, List<Short> states, String sender);

}
