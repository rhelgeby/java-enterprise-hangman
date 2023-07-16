package no.helgeby.hangman.command;

import static no.helgeby.hangman.command.CommandResult.SUCCESS;

import java.util.StringTokenizer;

/**
 * Simply exits the application.
 */
public class ExitCommand implements CommandHandler {

	public static final String NAME = "exit";

	@Override
	public CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens) {
		System.exit(0);
		return SUCCESS;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
