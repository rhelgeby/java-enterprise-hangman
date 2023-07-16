package no.helgeby.hangman.command;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents the outcome of a command.
 */
public class CommandResult {

	/**
	 * The default message used for successful commands.
	 */
	public static final String DEFAULT_SUCCESS_MESSAGE = "Command handled.";

	/**
	 * The default message used for failed commands.
	 */
	public static final String DEFAULT_ERROR_MESSAGE = "Command failed.";

	/**
	 * A result that indicates the command completed successfully.
	 */
	public static final CommandResult SUCCESS = new CommandResult(DEFAULT_SUCCESS_MESSAGE);

	/**
	 * A result that indicates the command failed.
	 */
	public static final CommandResult ERROR = new CommandResult(DEFAULT_ERROR_MESSAGE);

	private String message;
	private Throwable error;

	// TODO: Field to indicate the state of the result.

	/**
	 * Creates a result with a message and an exception.
	 * 
	 * @param message The message given to the issuer of the command.
	 * @param error   The error that caused the command to fail.
	 */
	public CommandResult(String message, Throwable error) {
		this(message);
		this.error = Objects.requireNonNull(error, "error");
	}

	/**
	 * Creates a result with a message.
	 * 
	 * @param message The message given to the issuer of the command.
	 */
	public CommandResult(String message) {
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

	/**
	 * @return The error that caused the command to fail if available, otherwise
	 *         null.
	 */
	public Throwable getError() {
		return error;
	}

	/**
	 * @return Whether an exception object exists.
	 */
	public boolean hasException() {
		return error != null;
	}

	public boolean isDefaultSuccessResult() {
		return this == SUCCESS;
	}
}
