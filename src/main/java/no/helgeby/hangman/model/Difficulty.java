package no.helgeby.hangman.model;

import org.apache.commons.lang3.StringUtils;

public enum Difficulty {

	/**
	 * Include all words in the list.
	 */
	ALL("all"),

	/**
	 * Includes most common words.
	 */
	EASY("easy"),

	/**
	 * Includes less frequently used word.
	 */
	MEDIUM("medium"),

	/**
	 * Includes rarely used words.
	 */
	HARD("hard");

	private String value;

	private Difficulty(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public static Difficulty of(String text) {
		if (StringUtils.isBlank(text)) {
			throw new IllegalArgumentException("Parameter value must not be null or blank.");
		}
		for (Difficulty d : Difficulty.values()) {
			if (d.value.equalsIgnoreCase(text)) {
				return d;
			}
		}
		throw new IllegalArgumentException("Unknown value for difficulty: " + text);
	}
}
