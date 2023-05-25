package com.gbtech.emails.queues.service.bean;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * The Class EmailQueueBean.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "from", "subject", "to", "cc", "bcc", "body", "state" })
public class EmailQueueBean {

	@JsonProperty("emailId")
	private BigInteger id;
	@JsonProperty("emailFrom")
	private String from;
	@JsonProperty("emailSubject")
	private String subject;
	@JsonProperty("emailTo")
	private List<Recipient> to;
	@JsonProperty("emailCC")
	private List<Recipient> cc;
	@JsonProperty("emailBCC")
	private List<Recipient> bcc;
	@JsonProperty("emailBody")
	private String body;
	@JsonProperty("emailState")
	private Short state;

	@ToString
	public static class Recipient {

		@JsonCreator
		public Recipient(@JsonProperty("email") final String email) {
			this.email = email;
		}

		@Getter
		@JsonProperty("email")
		private final String email;
	}
}
