package no.helgeby.hangman.command;

import static no.helgeby.hangman.command.result.SuccessfulCommandResult.SUCCESS;

import java.util.Objects;
import java.util.StringTokenizer;

import no.helgeby.hangman.command.result.CommandResult;
import no.helgeby.hangman.command.result.InformationCommandResult;
import no.helgeby.hangman.gui.painter.PainterManager;

public class PlayCommand implements CommandHandler {

	public static final String NAME = "play";
	private PainterManager manager;

	public PlayCommand(PainterManager manager) {
		this.manager = Objects.requireNonNull(manager, "manager");
	}

	@Override
	public CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens) {
		if (!tokens.hasMoreTokens()) {
			return usage();
		}

		String animationName = tokens.nextToken().toLowerCase();
		switch (animationName) {
		case "winningman":
			manager.startWinningManAnimation();
			return SUCCESS;
		}

		return usage();
	}

	private CommandResult usage() {
		return new InformationCommandResult("Play an animation. Syntax: play <winningman>");
	}

	@Override
	public String getName() {
		return NAME;
	}

}
