package no.helgeby.hangman.gui;

import java.awt.Insets;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import no.helgeby.hangman.command.CommandListener;
import no.helgeby.hangman.event.EmptyGameEventListener;
import no.helgeby.hangman.model.Difficulty;
import no.helgeby.hangman.model.GameModel;

public class SettingsPanel extends JPanel {

	private static final long serialVersionUID = -8703615699841321592L;

	private JRadioButton diff1;
	private JRadioButton diff2;
	private JRadioButton diff3;
	private JRadioButton diff4;

	private CommandListener listener;

	public SettingsPanel(CommandListener listener, GameModel gameModel) {
		this.listener = Objects.requireNonNull(listener, "listener");
		Objects.requireNonNull(gameModel, "gameModel");

		EmptyBorder border = new EmptyBorder(new Insets(10, 10, 10, 10));
		this.setBorder(border);

		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);

		add(new DifficultyPanel());
		ConsolePanel consolePanel = new ConsolePanel(listener);
		add(consolePanel);

		ModelListener modelListener = new ModelListener();
		gameModel.addListener(modelListener);
		gameModel.addListener(consolePanel);

		// Initialize the current values.
		setDifficulty(gameModel.difficulty());
	}

	private void setDifficulty(Difficulty difficulty) {
		switch (difficulty) {
		case ALL:
			diff1.setSelected(true);
			break;
		case EASY:
			diff2.setSelected(true);
			break;
		case MEDIUM:
			diff3.setSelected(true);
			break;
		case HARD:
			diff4.setSelected(true);
			break;
		}
	}

	private class DifficultyPanel extends JPanel {
		private static final long serialVersionUID = 4118967997953114435L;

		public DifficultyPanel() {
			add(new JLabel("Difficulty:"));

			diff1 = new JRadioButton("All words");
			diff2 = new JRadioButton("Easy");
			diff3 = new JRadioButton("Medium");
			diff4 = new JRadioButton("Hard");

			diff1.addActionListener(listener);
			diff2.addActionListener(listener);
			diff3.addActionListener(listener);
			diff4.addActionListener(listener);

			diff1.setActionCommand("difficulty all");
			diff2.setActionCommand("difficulty easy");
			diff3.setActionCommand("difficulty medium");
			diff4.setActionCommand("difficulty hard");

			ButtonGroup diffGroup = new ButtonGroup();
			diffGroup.add(diff1);
			diffGroup.add(diff2);
			diffGroup.add(diff3);
			diffGroup.add(diff4);

			add(diff1);
			add(diff2);
			add(diff3);
			add(diff4);
		}
	}

	private class ModelListener extends EmptyGameEventListener {
		@Override
		public void difficultyCanged(Difficulty oldDifficulty, Difficulty newDifficulty) {
			setDifficulty(newDifficulty);
		}
	}
}
