package no.helgeby.hangman.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class WordList extends ArrayList<String> {

	private static final long serialVersionUID = -5395383222522175653L;

	private Random random = new Random();

	public WordList(Set<String> initialWords) {
		super(initialWords);
		if (isEmpty()) {
			throw new IllegalArgumentException("Parameter initialWords must not be empty.");
		}
	}

	public String getRandomWord() {
		if (isEmpty()) {
			throw new IllegalStateException("Word list is empty.");
		}
		int index = random.nextInt(size());
		return get(index).toUpperCase();
	}

}
