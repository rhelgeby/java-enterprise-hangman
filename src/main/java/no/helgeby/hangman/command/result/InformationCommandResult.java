package no.helgeby.hangman.command.result;

/**
 * A result with information about the command.
 * <p>
 * In this case the command did not execute, but did not fail either.
 */
public class InformationCommandResult extends CommandResult {

	public InformationCommandResult(String message) {
		super(message);
	}

}
