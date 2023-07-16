package no.helgeby.hangman.event;

import no.helgeby.hangman.model.Difficulty;

/**
 * A GameEventListener that does nothing. Extend this class to only implement
 * events of interest.
 */
public class EmptyGameEventListener implements GameEventListener {

	@Override
	public void gameWon() {
	}

	@Override
	public void gameLost() {
	}

	@Override
	public void guessMade(char key) {
	}

	@Override
	public void correctGuessMade(char key) {
	}

	@Override
	public void incorrectGuessMade(char key) {
	}

	@Override
	public void difficultyCanged(Difficulty oldDifficulty, Difficulty newDifficulty) {
	}

	@Override
	public void newGame() {
	}

}
