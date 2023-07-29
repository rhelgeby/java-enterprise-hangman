package no.helgeby.hangman.command;

import static no.helgeby.hangman.command.result.SuccessfulCommandResult.SUCCESS;

import java.util.Objects;
import java.util.StringTokenizer;

import no.helgeby.hangman.command.result.CommandResult;
import no.helgeby.hangman.command.result.InformationCommandResult;
import no.helgeby.hangman.model.GameModel;

public class StageCommand implements CommandHandler {

	public static final String NAME = "stage";

	private GameModel model;

	public StageCommand(GameModel model) {
		this.model = Objects.requireNonNull(model, "model");
	}

	@Override
	public CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens) {
		if (!tokens.hasMoreTokens()) {
			return usage();
		}

		int stage = Integer.parseInt(tokens.nextToken());
		model.gallowsModel().setStage(stage);
		model.notifyExternalModelChange();

		return SUCCESS;
	}

	private CommandResult usage() {
		return new InformationCommandResult("Sets the gallows stage. Syntax: stage <number from 1 to 11>");
	}

	@Override
	public String getName() {
		return NAME;
	}

}
