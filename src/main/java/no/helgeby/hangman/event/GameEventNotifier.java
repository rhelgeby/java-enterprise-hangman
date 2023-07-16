package no.helgeby.hangman.event;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import no.helgeby.hangman.model.Difficulty;

/**
 * Registers listeners and notifies listeners to various game events.
 */
public class GameEventNotifier {

	private static final Logger log = LogManager.getLogger(GameEventNotifier.class);
	private List<GameEventListener> listeners;

	private enum EventType {
		GAME_WON, GAME_LOST, GUESS_MADE, CORRECT_GUESS, INCORRECT_GUESS, DIFFICULTY_CHANGED, NEW_GAME
	}

	public GameEventNotifier() {
		listeners = new ArrayList<>();
	}

	/**
	 * Adds a listener to be notified when the game model changes.
	 * 
	 * @param listener The listener to add.
	 */
	public void addListener(GameEventListener listener) {
		listeners.add(listener);
	}

	private void fireEvent(EventType type, Object... args) {
		log.info("Firing game event: " + type);
		for (GameEventListener listener : listeners) {
			switch (type) {
			case GAME_WON:
				listener.gameWon();
				break;
			case GAME_LOST:
				listener.gameLost();
				break;
			case GUESS_MADE:
				listener.guessMade((char) args[0]);
				break;
			case CORRECT_GUESS:
				listener.correctGuessMade((char) args[0]);
				break;
			case INCORRECT_GUESS:
				listener.incorrectGuessMade((char) args[0]);
				break;
			case DIFFICULTY_CHANGED:
				listener.difficultyCanged((Difficulty) args[0], (Difficulty) args[1]);
				break;
			case NEW_GAME:
				listener.newGame();
				break;
			}
		}
	}

	/**
	 * Triggers the "game won" event on all listeners.
	 */
	public void notifyGameWon() {
		fireEvent(EventType.GAME_WON);
	}

	/**
	 * Triggers the "game lost" event on all listeners.
	 */
	public void notifyGameLost() {
		fireEvent(EventType.GAME_LOST);
	}

	/**
	 * Triggers the "guess made" event on all listeners.
	 */
	public void notifyGuessMade(char key) {
		fireEvent(EventType.GUESS_MADE, key);
	}

	/**
	 * Triggers the "correct guess made" event on all listeners.
	 */
	public void notifyCorrectGuessMade(char key) {
		fireEvent(EventType.CORRECT_GUESS, key);
	}

	/**
	 * Triggers the "incorrect guess made" event on all listeners.
	 */
	public void notifyIncorrectGuessMade(char key) {
		fireEvent(EventType.INCORRECT_GUESS, key);
	}

	/**
	 * Triggers the "difficulty changed" event on all listeners.
	 */
	public void notifyDifficultyCanged(Difficulty oldDifficulty, Difficulty newDifficulty) {
		fireEvent(EventType.DIFFICULTY_CHANGED, oldDifficulty, newDifficulty);
	}

	/**
	 * Triggers the "new game" event on all listeners.
	 */
	public void notifyNewGame() {
		fireEvent(EventType.NEW_GAME);
	}

}
