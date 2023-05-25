package com.gbtech.emails.rest.controller.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class NotSuccessfulActionResponse.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "code", "message", "timestamp" })
public class NotSuccessfulActionResponse {

	@JsonProperty("code")
	int code;
	@JsonProperty("message")
	String message;
	@JsonProperty("timestamp")
	Date timestamp;
}
