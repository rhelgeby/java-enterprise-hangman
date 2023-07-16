package no.helgeby.hangman.command.result;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents the outcome of a command.
 */
public class CommandResult {

	private String message;

	/**
	 * Creates a successful result with a message.
	 * 
	 * @param message The message given to the issuer of the command.
	 */
	protected CommandResult(String message) {
		if (StringUtils.isBlank(message)) {
			throw new IllegalArgumentException("Parameter message cannot be null or blank.");
		}
		this.message = message;
	}

	/**
	 * @return The message given to the issuer of the command.
	 */
	public String getMessage() {
		return message;
	}

}
