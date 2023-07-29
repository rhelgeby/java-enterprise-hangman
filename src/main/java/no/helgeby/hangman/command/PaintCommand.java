package no.helgeby.hangman.command;

import static no.helgeby.hangman.command.result.SuccessfulCommandResult.SUCCESS;

import java.util.Objects;
import java.util.StringTokenizer;

import no.helgeby.hangman.command.result.CommandResult;
import no.helgeby.hangman.command.result.InformationCommandResult;
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
		switch (imageName) {
		case "man":
			manager.paintMan();
			return SUCCESS;
		case "woman":
			manager.paintWoman();
			return SUCCESS;
		}

		return usage();
	}

	private CommandResult usage() {
		return new InformationCommandResult("Paint something. Syntax: paint <man|woman>");
	}

	@Override
	public String getName() {
		return NAME;
	}

}
