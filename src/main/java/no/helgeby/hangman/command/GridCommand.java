package no.helgeby.hangman.command;

import java.util.Objects;
import java.util.StringTokenizer;

import org.apache.commons.lang3.BooleanUtils;

import no.helgeby.hangman.command.result.CommandResult;
import no.helgeby.hangman.command.result.InformationCommandResult;
import no.helgeby.hangman.command.result.SuccessfulCommandResult;
import no.helgeby.hangman.gui.painter.PainterManager;

public class GridCommand implements CommandHandler {

	public static final String NAME = "grid";
	private PainterManager manager;

	public GridCommand(PainterManager manager) {
		this.manager = Objects.requireNonNull(manager, "manager");
	}

	@Override
	public CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens) {
		if (!tokens.hasMoreTokens()) {
			return usage();
		}

		String value = tokens.nextToken();
		boolean drawGrid = BooleanUtils.toBoolean(value);
		manager.setDrawGrid(drawGrid);

		return new SuccessfulCommandResult("Grid flag changed to " + drawGrid + ". Will be updated on next drawing.");
	}

	private CommandResult usage() {
		return new InformationCommandResult("Turns the grid on or off. Syntax: grid <on|off>");
	}

	@Override
	public String getName() {
		return NAME;
	}

}
