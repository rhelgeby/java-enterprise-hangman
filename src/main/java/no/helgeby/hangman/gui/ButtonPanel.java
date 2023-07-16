package no.helgeby.hangman.gui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import no.helgeby.hangman.command.ExitCommand;
import no.helgeby.hangman.command.NewGameCommand;
import no.helgeby.hangman.command.ShowSettingsCommand;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 9144378480320574063L;

	public ButtonPanel(ActionListener listener) {
		JButton btnExit = new JButton("Exit");
		btnExit.setActionCommand(ExitCommand.NAME);
		btnExit.addActionListener(listener);

		JButton btnNew = new JButton("New game");
		btnNew.setActionCommand(NewGameCommand.NAME);
		btnNew.addActionListener(listener);

		JButton btnSettings = new JButton("Settings");
		btnSettings.setActionCommand(ShowSettingsCommand.NAME);
		btnSettings.addActionListener(listener);

		add(btnExit);
		add(btnNew);
		add(btnSettings);
	}
}
