package com.gbtech.emails.model.service.exceptions;

/**
 * The Class StateErrorException.
 */
public class StateErrorException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3765515296401132960L;

	/**
	 * Instantiates a new state error exception.
	 */
	public StateErrorException() {
	}

	/**
	 * Instantiates a new state error exception.
	 *
	 * @param message            the message
	 * @param cause              the cause
	 * @param enableSuppression  the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public StateErrorException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new state error exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public StateErrorException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new state error exception.
	 *
	 * @param message the message
	 */
	public StateErrorException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new state error exception.
	 *
	 * @param cause the cause
	 */
	public StateErrorException(final Throwable cause) {
		super(cause);
	}

}
