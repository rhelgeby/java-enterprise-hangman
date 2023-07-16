package no.helgeby.hangman.command;

import static no.helgeby.hangman.command.result.SuccessfulCommandResult.SUCCESS;

import java.util.Objects;
import java.util.StringTokenizer;

import no.helgeby.hangman.command.result.CommandResult;
import no.helgeby.hangman.command.result.InformationCommandResult;
import no.helgeby.hangman.model.GameModel;

public class KeyCommand implements CommandHandler {

	public static final String NAME = "key";
	private GameModel model;

	public KeyCommand(GameModel model) {
		this.model = Objects.requireNonNull(model, "model");
	}

	@Override
	public CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens) {
		if (!tokens.hasMoreTokens() || tokens.countTokens() != 1) {
			// No arguments given. Return the syntax.
			return syntax();
		}
		String token = tokens.nextToken();
		if (token.length() > 1) {
			return syntax();
		}
		char key = token.charAt(0);
		model.makeGuess(key);
		return SUCCESS;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private CommandResult syntax() {
		return new InformationCommandResult("Syntax: key <letter>");
	}
}
