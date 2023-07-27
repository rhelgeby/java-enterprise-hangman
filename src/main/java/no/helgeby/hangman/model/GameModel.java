package no.helgeby.hangman.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import no.helgeby.hangman.event.GameEventListener;
import no.helgeby.hangman.event.GameEventNotifier;

/**
 * Represents a model of the game state.
 */
public class GameModel {

	private Difficulty difficulty;
	private String correctAnswer;
	private List<Character> word;
	private List<Character> incorrect;
	private WordList wordList;
	private GallowsModel gallowsModel;
	private GameEventNotifier notifier;
	private Config config;

	public GameModel(Config config) {
		this.config = Objects.requireNonNull(config, "config");

		gallowsModel = new GallowsModel();
		word = new ArrayList<Character>();
		incorrect = new ArrayList<Character>();
		notifier = new GameEventNotifier();

		// Note: Setting difficulty will also initialize the word list.
		setDifficulty(config.getDifficulty());

		newWord();
	}

	/**
	 * Adds a listener to be notified when the game model changes.
	 * 
	 * @param listener The listener to add.
	 */
	public void addListener(GameEventListener listener) {
		notifier.addListener(listener);
	}

	public Difficulty difficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		Difficulty oldDifficulty = this.difficulty;
		this.difficulty = difficulty;
		
		DrawingType drawingType = DrawingType.MAN;

		switch (difficulty) {
		case EASY:
			wordList = config.getEasyWords();
			break;
		case MEDIUM:
			wordList = config.getMediumWords();
			break;
		case HARD:
			wordList = config.getHardWords();
			drawingType = DrawingType.WOMAN;
			break;
		case ALL:
			wordList = config.getAllWords();
			break;
		}

		gallowsModel.setNextDrawingType(drawingType);
		notifier.notifyDifficultyCanged(oldDifficulty, difficulty);
	}

	public GallowsModel gallowsModel() {
		return gallowsModel;
	}

	public String correctAnswer() {
		return correctAnswer;
	}

	public List<Character> wordCharacters() {
		return word;
	}

	public String word() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < word.size(); i++) {
			builder.append(Character.toUpperCase(word.get(i)));
			builder.append(' ');
		}
		return builder.toString();
	}

	public String wordWithoutSpaces() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < word.size(); i++) {
			builder.append(word.get(i));
		}
		return builder.toString();
	}

	public void newWord() {
		String previousWord = correctAnswer();
		correctAnswer = wordList.getRandomWord();
		if (wordList.size() > 1) {
			while (correctAnswer.equals(previousWord)) {
				// Got the same word. Try again.
				correctAnswer = wordList.getRandomWord();
			}
		}

		incorrect.clear();
		word.clear();
		gallowsModel.reset();

		for (int i = 0; i < correctAnswer.length(); i++) {
			word.add('_');
		}

		notifier.notifyNewGame();
	}

	public List<Character> incorrectLetters() {
		return incorrect;
	}

	public String incorrect() {
		// Convert the individual characters to a string.
		return incorrect.stream().map(c -> c.toString()).collect(Collectors.joining());
	}

	public WordList wordList() {
		return wordList;
	}

	public void makeGuess(char letter) {
		letter = Character.toUpperCase(letter);

		if (incorrect.contains(letter) && difficulty != Difficulty.HARD) {
			// Letter already tried. On hard difficulty, make repeated incorrect keys count.
			return;
		}

		boolean correctGuess;
		int i = correctAnswer.indexOf(letter);
		if (i >= 0) {
			correctGuess = true;
			updateWord(letter);
		} else {
			// Incorrect guess.
			correctGuess = false;
			incorrect.add(letter);
			gallowsModel.nextStage();
		}

		notifier.notifyGuessMade(letter);
		if (correctGuess) {
			notifier.notifyCorrectGuessMade(letter);
			if (correctAnswer.equals(wordWithoutSpaces())) {
				gallowsModel.free();
				notifier.notifyGameWon();
			}
		} else {
			notifier.notifyIncorrectGuessMade(letter);
			if (gallowsModel.isDead()) {
				notifier.notifyGameLost();
			}
		}
	}

	private void updateWord(char letter) {
		for (int i = 0; i < word.size(); i++) {
			char c = correctAnswer.charAt(i);
			if (c == letter) {
				word.set(i, c);
			}
		}
	}
}
