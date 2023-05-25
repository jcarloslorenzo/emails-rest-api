package com.gbtech.emails.model.service.vo;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

/**
 * The Class EmailVO.
 */
@Data
public class EmailVO {

	private BigInteger id;
	private String from;
	private String subject;
	private String body;
	private Short state;

	private List<EmailRecipientVO> to;
	private List<EmailRecipientVO> cc;
	private List<EmailRecipientVO> bcc;

}
