package no.helgeby.hangman.gui;

import java.util.Objects;

import javax.swing.JFrame;

import no.helgeby.hangman.command.CommandListener;
import no.helgeby.hangman.model.GameModel;

public class SettingsFrame extends JFrame {

	private static final long serialVersionUID = -1657985245575807619L;

	public SettingsFrame(CommandListener listener, GameModel gameModel) {
		Objects.requireNonNull(listener, "listener");

		setTitle("Hangman settings");
		setLocationByPlatform(true);

		add(new SettingsPanel(listener, gameModel));

		pack();
	}

}
