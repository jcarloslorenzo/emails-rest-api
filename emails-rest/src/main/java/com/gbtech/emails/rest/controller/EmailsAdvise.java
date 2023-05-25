package com.gbtech.emails.rest.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gbtech.emails.model.service.exceptions.ActionNotAllowedException;
import com.gbtech.emails.model.service.exceptions.DataNotFoundException;
import com.gbtech.emails.model.service.exceptions.StateErrorException;
import com.gbtech.emails.rest.controller.bean.NotSuccessfulActionResponse;

/**
 * The Class EmailsAdvise.
 */
@RestControllerAdvice
public class EmailsAdvise extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailsAdvise.class);

	/**
	 * Handles {@link DataNotFoundException}
	 *
	 * @param ex      the exception
	 * @param request the request
	 * @return the response entity
	 */
	@ResponseBody
	@ExceptionHandler(DataNotFoundException.class)
	ResponseEntity<Object> handleDataNotFoundException(final DataNotFoundException ex, final WebRequest request) {
		final HttpStatus responseStatus = HttpStatus.NOT_FOUND;
		return this.handleExceptionInternal(
				ex, this.generateResponse(responseStatus, ex.getMessage()),
				new HttpHeaders(), responseStatus, request);
	}

	/**
	 * Handles {@link StateErrorException}
	 *
	 * @param ex      the exception
	 * @param request the request
	 * @return the response entity
	 */
	@ResponseBody
	@ExceptionHandler(StateErrorException.class)
	ResponseEntity<Object> handleStateErrorException(final StateErrorException ex, final WebRequest request) {
		final HttpStatus responseStatus = HttpStatus.CONFLICT;
		return this.handleExceptionInternal(
				ex, this.generateResponse(responseStatus, ex.getMessage()),
				new HttpHeaders(), responseStatus, request);
	}

	/**
	 * Handles {@link ActionNotAllowedException}
	 *
	 * @param ex      the exception
	 * @param request the request
	 * @return the response entity
	 */
	@ResponseBody
	@ExceptionHandler(ActionNotAllowedException.class)
	ResponseEntity<Object> handleActionNotAllowedException(final ActionNotAllowedException ex,
			final WebRequest request) {
		final HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
		return this.handleExceptionInternal(
				ex, this.generateResponse(responseStatus, ex.getMessage()),
				new HttpHeaders(), responseStatus, request);
	}

	/**
	 * Handles {@link MethodArgumentNotValidException}
	 *
	 * @param ex      the exception
	 * @param request the request
	 * @return the response entity
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatusCode status,
			final WebRequest request) {

		final List<String> errors = new ArrayList<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			final String fieldName = ((FieldError) error).getField();
			final String errorMessage = error.getDefaultMessage();
			errors.add(String.join(": ", fieldName, errorMessage));
		});
		final HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
		return this.handleExceptionInternal(
				ex,
				this.generateResponse(responseStatus, String.join(System.lineSeparator(), errors)),
				headers,
				responseStatus,
				request);
	}

	/**
	 * Handles all {@link Exception} that don't match the other handlers.
	 *
	 * @param ex      the exception
	 * @param request the request
	 * @return the response entity
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> unhandledException(final Exception ex, final WebRequest request) {
		LOGGER.error("ERROR", ex);
		final HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		return this.handleExceptionInternal(
				ex, this.generateResponse(responseStatus, "An unexpected error has ocurred."),
				new HttpHeaders(), responseStatus, request);
	}

	private NotSuccessfulActionResponse generateResponse(final HttpStatus httpStatus, final String message) {
		return new NotSuccessfulActionResponse(httpStatus.value(), message, Date.from(Instant.now()));
	}

}
