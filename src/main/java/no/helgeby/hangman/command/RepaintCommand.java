package no.helgeby.hangman.command;

import static no.helgeby.hangman.command.result.SuccessfulCommandResult.SUCCESS;

import java.util.Objects;
import java.util.StringTokenizer;

import no.helgeby.hangman.command.result.CommandResult;
import no.helgeby.hangman.gui.painter.PainterManager;

public class RepaintCommand implements CommandHandler {

	public static final String NAME = "repaint";
	private PainterManager manager;

	public RepaintCommand(PainterManager manager) {
		this.manager = Objects.requireNonNull(manager, "manager");
	}

	@Override
	public CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens) {
		manager.paint();
		return SUCCESS;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
