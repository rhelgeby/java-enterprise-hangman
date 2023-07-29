package no.helgeby.hangman.command;

import static no.helgeby.hangman.command.result.SuccessfulCommandResult.SUCCESS;

import java.util.Objects;
import java.util.StringTokenizer;

import no.helgeby.hangman.command.result.CommandResult;
import no.helgeby.hangman.command.result.InformationCommandResult;
import no.helgeby.hangman.model.GallowsModel;
import no.helgeby.hangman.model.GameModel;

public class StageCommand implements CommandHandler {

	public static final String NAME = "stage";

	private GameModel model;
	private GallowsModel gallows;

	public StageCommand(GameModel model) {
		this.model = Objects.requireNonNull(model, "model");
		gallows = model.gallowsModel();
	}

	@Override
	public CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens) {
		if (!tokens.hasMoreTokens()) {
			return usage();
		}

		String stageString = tokens.nextToken().toLowerCase();
		int stage;
		switch (stageString) {
		case "lastalive":
			stage = gallows.lastAliveStage();
			break;
		case "dead":
			stage = gallows.deadStage();
			break;
		default:
			stage = Integer.parseInt(stageString);
		}

		model.gallowsModel().setStage(stage);
		model.notifyExternalModelChange();

		return SUCCESS;
	}

	private CommandResult usage() {
		return new InformationCommandResult("Sets the gallows stage. Syntax: stage <lastalive|dead|number from 1 to 11>");
	}

	@Override
	public String getName() {
		return NAME;
	}

}
