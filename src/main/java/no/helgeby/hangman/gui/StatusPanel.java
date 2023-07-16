package no.helgeby.hangman.gui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import no.helgeby.hangman.event.EmptyGameEventListener;
import no.helgeby.hangman.model.GameModel;

public class StatusPanel extends JPanel {

	private static final long serialVersionUID = 5469624065528150750L;
	private JLabel statusLabel;
	private GameModel gameModel;

	public StatusPanel(ActionListener listener, GameModel gameModel) {
		this.gameModel = Objects.requireNonNull(gameModel, "gameModel");

		BorderLayout layout = new BorderLayout();
		setLayout(layout);

		EmptyBorder border = new EmptyBorder(new Insets(10, 10, 10, 10));
		this.setBorder(border);

		statusLabel = new JLabel("status");

		ButtonPanel buttons = new ButtonPanel(listener);

		add(statusLabel, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);

		updateStatus();
		gameModel.addListener(new ModelListener());
	}

	private void updateStatus() {
		setText("Word: " + gameModel.word() + " Attempts: " + gameModel.incorrect());
	}

	private void setText(String text) {
		SwingUtilities.invokeLater(() -> {
			statusLabel.setText(text);
		});
	}

	private class ModelListener extends EmptyGameEventListener {

		@Override
		public void gameWon() {
			setText("You win!");
		}

		@Override
		public void gameLost() {
			setText("Game over!");
		}

		@Override
		public void guessMade(char key) {
			updateStatus();
		}

		@Override
		public void newGame() {
			updateStatus();
		}

	}
}
