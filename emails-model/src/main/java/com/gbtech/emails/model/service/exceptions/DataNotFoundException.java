package com.gbtech.emails.model.service.exceptions;

/**
 * The Class DataNotFoundException.
 */
public class DataNotFoundException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2509985889257252405L;

	/**
	 * Instantiates a new data not found exception.
	 */
	public DataNotFoundException() {
	}

	/**
	 * Instantiates a new data not found exception.
	 *
	 * @param message            the message
	 * @param cause              the cause
	 * @param enableSuppression  the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public DataNotFoundException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new data not found exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public DataNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new data not found exception.
	 *
	 * @param message the message
	 */
	public DataNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new data not found exception.
	 *
	 * @param cause the cause
	 */
	public DataNotFoundException(final Throwable cause) {
		super(cause);

	}

}
