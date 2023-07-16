package no.helgeby.hangman.gui;

import java.awt.GridBagConstraints;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.helgeby.hangman.command.CommandListener;
import no.helgeby.hangman.command.CommandResult;
import no.helgeby.hangman.event.GameEventListener;
import no.helgeby.hangman.model.Difficulty;

public class ConsolePanel extends JPanel implements GameEventListener {

	private static final long serialVersionUID = 6592829929043947025L;

	private JTextArea consoleText;

	/**
	 * Creates a simple console panel.
	 * 
	 * @param commandListener The listeners that handles the console commands.
	 */
	public ConsolePanel(CommandListener commandListener) {
		Objects.requireNonNull(commandListener, "commandListener");

		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);

		consoleText = new JTextArea(10, 80);
		consoleText.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(consoleText);

		JTextField commandField = new JTextField(80);
		commandField.addActionListener(e -> {
			// Clear text and forward the command to the real listener.
			commandField.setText(null);
			String commandLine = e.getActionCommand();
			appendLine("> " + commandLine);

			CommandResult result = commandListener.handleCommand(commandLine);

			// Only print the message when there is something of interest.
			if (!result.isDefaultSuccessResult()) {
				appendLine(result.getMessage());
			}
		});

		add(scrollPane, GridBagConstraints.REMAINDER);
		add(commandField);
	}

	private void appendLine(String text) {
		consoleText.append(text + "\n");
	}

	/**
	 * Clears the console.
	 */
	public void clear() {
		consoleText.setText(null);
	}

	@Override
	public void gameWon() {
		appendLine("Game won.");
	}

	@Override
	public void gameLost() {
		appendLine("Game lost.");
	}

	@Override
	public void guessMade(char key) {
		appendLine("Guess made: " + key);
	}

	@Override
	public void correctGuessMade(char key) {
		appendLine("Correct guess.");
	}

	@Override
	public void incorrectGuessMade(char key) {
		appendLine("Incorrect guess.");
	}

	@Override
	public void difficultyCanged(Difficulty oldDifficulty, Difficulty newDifficulty) {
		appendLine("Difficulty changed: " + newDifficulty);
	}

	@Override
	public void newGame() {
		appendLine("New game.");
	}
}
