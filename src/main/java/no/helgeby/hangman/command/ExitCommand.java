package no.helgeby.hangman.command;

import static no.helgeby.hangman.command.result.SuccessfulCommandResult.SUCCESS;

import java.util.StringTokenizer;

import no.helgeby.hangman.command.result.CommandResult;

/**
 * Simply exits the application.
 */
public class ExitCommand implements CommandHandler {

	public static final String NAME = "exit";

	@Override
	public CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens) {
		// This is OK for stand-alone apps. If it's in a container we don't want to shut
		// down like this.
		System.exit(0);

		// No effect, but must be here to make compiler happy.
		return SUCCESS;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
