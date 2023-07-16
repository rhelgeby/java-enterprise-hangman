package no.helgeby.hangman.command;

import static no.helgeby.hangman.command.result.SuccessfulCommandResult.SUCCESS;
import static no.helgeby.hangman.model.Difficulty.of;

import java.util.Objects;
import java.util.StringTokenizer;

import no.helgeby.hangman.command.result.CommandResult;
import no.helgeby.hangman.command.result.FailureCommandResult;
import no.helgeby.hangman.model.GameModel;

public class DifficultyCommand implements CommandHandler {

	public static final String NAME = "difficulty";
	private GameModel model;

	public DifficultyCommand(GameModel model) {
		this.model = Objects.requireNonNull(model, "model");
	}

	@Override
	public CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens) {
		try {
			String value = tokens.nextToken();
			model.setDifficulty(of(value));
			return SUCCESS;
		} catch (IllegalArgumentException e) {
			return new FailureCommandResult(e.getMessage(), e);
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

}
