package no.helgeby.hangman.command;

import java.util.StringTokenizer;

import no.helgeby.hangman.command.result.CommandResult;

/**
 * Handles one or more commands.
 */
public interface CommandHandler {

	/**
	 * Handles one or more commands.
	 * 
	 * @param commandLine The full command line.
	 * @param baseCommand The first word of the command when splitting by spaces.
	 * @param tokens      A tokenizer positioned at the first word after the base
	 *                    command.
	 * 
	 * @return A result object with a message. If the command failed it with an
	 *         exception it also contains the exception.
	 */
	CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens);

	/**
	 * @return Gets the command name.
	 */
	String getName();

	// TODO: A method to declare syntax?
}
