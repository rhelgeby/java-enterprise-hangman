package no.helgeby.hangman.event;

import java.util.EventListener;

import no.helgeby.hangman.model.Difficulty;

/**
 * An interface for various game events.
 * <p>
 * A view may listen to these events to be notified changes of interest and
 * update itself.
 */
public interface GameEventListener extends EventListener {

	void gameWon();

	void gameLost();

	void guessMade(char key);

	void correctGuessMade(char key);

	void incorrectGuessMade(char key);

	void difficultyCanged(Difficulty oldDifficulty, Difficulty newDifficulty);

	void newGame();

	void modelChangedExternally();

}
