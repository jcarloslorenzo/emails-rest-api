package com.gbtech.emails.rest.controller.bean;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EmailSearchFilter {

	@JsonProperty("creationDateFrom")
	private Date dateFrom;

	@JsonProperty("creationDateTo")
	private Date dateTo;

	@JsonProperty("sender")
	private String sender;

	@JsonProperty("allowSenderPartialMatch")
	private boolean allowSenderPartialMatch;

	@JsonProperty("states")

	private List<Short> states;
}
