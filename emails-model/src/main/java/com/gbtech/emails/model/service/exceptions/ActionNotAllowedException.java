package com.gbtech.emails.model.service.exceptions;

/**
 * The Class ActionNotAllowedException.
 *
 */
public class ActionNotAllowedException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8549271208303519959L;

	/**
	 * Instantiates a new action not allowed exception.
	 */
	public ActionNotAllowedException() {
	}

	/**
	 * Instantiates a new action not allowed exception.
	 *
	 * @param message the message
	 */
	public ActionNotAllowedException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new action not allowed exception.
	 *
	 * @param cause the cause
	 */
	public ActionNotAllowedException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new action not allowed exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public ActionNotAllowedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new action not allowed exception.
	 *
	 * @param message            the message
	 * @param cause              the cause
	 * @param enableSuppression  the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public ActionNotAllowedException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
