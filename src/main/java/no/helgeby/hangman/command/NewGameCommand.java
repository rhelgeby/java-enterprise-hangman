package no.helgeby.hangman.command;

import static no.helgeby.hangman.command.result.SuccessfulCommandResult.SUCCESS;

import java.util.Objects;
import java.util.StringTokenizer;

import no.helgeby.hangman.command.result.CommandResult;
import no.helgeby.hangman.model.GameModel;

public class NewGameCommand implements CommandHandler {

	public static final String NAME = "newGame";
	private GameModel model;

	public NewGameCommand(GameModel model) {
		this.model = Objects.requireNonNull(model, "model");
	}

	@Override
	public CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens) {
		model.newWord();
		return SUCCESS;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
