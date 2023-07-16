package no.helgeby.hangman.command.result;

import java.util.Objects;

/**
 * A result that indicates the command failed.
 */
public class FailureCommandResult extends CommandResult {

	/**
	 * The default message used for failed commands.
	 */
	public static final String DEFAULT_ERROR_MESSAGE = "Command failed.";

	/**
	 * A result that indicates the command failed.
	 */
	public static final FailureCommandResult ERROR = new FailureCommandResult();

	private Throwable error;

	/**
	 * Creates a failure result with a message.
	 * 
	 * @param message The message given to the issuer of the command.
	 * @param error   The error that caused the command to fail.
	 */
	public FailureCommandResult(String message, Throwable error) {
		this(message);
		this.error = Objects.requireNonNull(error, "error");
	}

	/**
	 * Creates a failure result with a message.
	 * 
	 * @param message The message given to the issuer of the command.
	 */
	public FailureCommandResult(String message) {
		super(message);
	}

	/**
	 * Creates a failure result with the default failure message.
	 */
	public FailureCommandResult() {
		super(DEFAULT_ERROR_MESSAGE);
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

}
