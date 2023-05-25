package com.gbtech.emails.model.service.enums;

import java.util.List;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enum EmailState.
 */
@AllArgsConstructor
public enum EmailState {

	/** SENT - 1 */
	SENT((short) 1),
	/** DRAFT - 2 */
	DRAFT((short) 2),
	/** DELETED - 3 */
	DELETED((short) 3),
	/** SPAM - 4 */
	SPAM((short) 4);

	@Getter
	private short value;

	public boolean inState(final Short value) {
		return value != null && this.value == value.shortValue();
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the email state
	 */
	public static EmailState valueOf(final Short value) {
		return Stream.of(EmailState.values())
				.filter(state -> value != null && state.value == value).findFirst()
				.orElse(null);
	}

	public static List<Short> nonSpamStatesCodes() {
		return Stream.of(EmailState.values()).filter(state -> !state.equals(SPAM)).map(EmailState::getValue).toList();
	}

}
