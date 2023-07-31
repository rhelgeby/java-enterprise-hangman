package no.helgeby.hangman.command;

import static no.helgeby.hangman.command.result.SuccessfulCommandResult.SUCCESS;

import java.util.Objects;
import java.util.StringTokenizer;

import no.helgeby.hangman.command.result.CommandResult;
import no.helgeby.hangman.command.result.InformationCommandResult;
import no.helgeby.hangman.gui.painter.Painter;
import no.helgeby.hangman.gui.painter.PainterManager;

/**
 * Paint something.
 */
public class PaintCommand implements CommandHandler {

	public static final String NAME = "paint";
	private PainterManager manager;

	public PaintCommand(PainterManager manager) {
		this.manager = Objects.requireNonNull(manager, "manager");
	}

	@Override
	public CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens) {
		if (!tokens.hasMoreTokens()) {
			return usage();
		}

		String imageName = tokens.nextToken().toLowerCase();

		int stage = 0;
		if (tokens.hasMoreTokens()) {
			stage = Integer.parseInt(tokens.nextToken());
		}

		switch (imageName) {
		case "gallows":
			manager.paintGallows();
			return SUCCESS;
		case "man":
			manager.paintMan();
			return SUCCESS;
		case "woman":
			manager.paintWoman();
			return SUCCESS;
		case "manreleased":
			paintStage(manager.painters.manReleasedPainter, stage);
			return SUCCESS;
		}

		return usage();
	}

	private void paintStage(Painter animation, int stage) {
		animation.stage = stage;
		manager.paint(animation);
	}

	private CommandResult usage() {
		return new InformationCommandResult("Paint something. Syntax: paint <gallows|man|woman|manreleased> [number]");
	}

	@Override
	public String getName() {
		return NAME;
	}

}
