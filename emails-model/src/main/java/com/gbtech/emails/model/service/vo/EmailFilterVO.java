package com.gbtech.emails.model.service.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;

/**
 * The Class EmailFilterVO.
 */
@Data
public class EmailFilterVO {

	private Date dateFrom;

	private Date dateTo;

	private String sender;

	private boolean allowSenderPartialMatch;

	@Getter(lazy = true)
	private final List<Short> states = new ArrayList<>();
}
