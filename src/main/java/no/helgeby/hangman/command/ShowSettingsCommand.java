package no.helgeby.hangman.command;

import static no.helgeby.hangman.command.result.SuccessfulCommandResult.SUCCESS;

import java.util.Objects;
import java.util.StringTokenizer;

import no.helgeby.hangman.command.result.CommandResult;
import no.helgeby.hangman.gui.SettingsFrame;

/**
 * Shows the settings frame.
 */
public class ShowSettingsCommand implements CommandHandler {

	public static final String NAME = "showSettings";
	private SettingsFrame frame;

	public ShowSettingsCommand(SettingsFrame frame) {
		this.frame = Objects.requireNonNull(frame, "frame");
	}

	@Override
	public CommandResult handleCommand(String commandLine, String baseCommand, StringTokenizer tokens) {
		frame.setVisible(true);
		return SUCCESS;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
