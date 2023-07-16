package no.helgeby.hangman.command.result;

/**
 * A result that indicates the command completed successfully.
 */
public class SuccessfulCommandResult extends CommandResult {

	/**
	 * The default message used for successful commands.
	 */
	public static final String DEFAULT_SUCCESS_MESSAGE = "Command handled.";

	/**
	 * The default result for successful commands.
	 */
	public static final CommandResult SUCCESS = new SuccessfulCommandResult();

	public SuccessfulCommandResult() {
		super(DEFAULT_SUCCESS_MESSAGE);
	}

	/**
	 * Creates a success result with a message.
	 * 
	 * @param message The message given to the issuer of the command.
	 */
	public SuccessfulCommandResult(String message) {
		super(message);
	}

	/**
	 * @return Whether this result is the default success result.
	 */
	public boolean isDefault() {
		return this == SUCCESS;
	}

}
