package no.helgeby.hangman.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * Loads a configuration file with the word lists and starting difficulty.
 */
public class Config extends Properties {

	/**
	 * The string used to split a property value into a set.
	 */
	public static final String LIST_DELIMITER = ",";

	private static final long serialVersionUID = 3351111178524024705L;

	private Set<String> easyWords;
	private Set<String> mediumWords;
	private Set<String> hardWords;
	private Set<String> allWords;
	private Difficulty difficulty;

	public Config(Path path) throws IOException {
		BufferedReader reader = Files.newBufferedReader(path);
		load(reader);
		parse();
	}

	// Note: Using copy constructors to preserve original lists.

	public WordList getEasyWords() {
		return new WordList(easyWords);
	}

	public WordList getMediumWords() {
		WordList list = new WordList(this.easyWords);
		list.addAll(this.mediumWords);
		return list;
	}

	public WordList getHardWords() {
		WordList list = new WordList(mediumWords);
		list.addAll(hardWords);
		return list;
	}

	public WordList getAllWords() {
		WordList list = new WordList(easyWords);
		list.addAll(mediumWords);
		list.addAll(hardWords);
		return list;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	private void parse() {
		easyWords = asSet(require("easyWords"));
		mediumWords = asSet(require("mediumWords"));
		hardWords = asSet(require("hardWords"));

		allWords = new HashSet<>();
		allWords.addAll(easyWords);
		allWords.addAll(mediumWords);
		allWords.addAll(hardWords);

		difficulty = Difficulty.of(require("difficulty"));
	}

	/**
	 * Gets a property or throws an exception if it's null or blank.
	 * 
	 * @param key The property name.
	 * @return The property value.
	 * @throws IllegalArgumentException if the property is null or blank.
	 */
	private String require(String key) {
		String value = getProperty(key);
		if (StringUtils.isBlank(value)) {
			throw new IllegalArgumentException("Required property '" + key + "' is null or blank.");
		}
		return value;
	}

	/**
	 * Converts a String into a Set. It is splitting the string by the
	 * {@value #LIST_DELIMITER}.
	 * <p>
	 * The values are trimmed after splitting.
	 * 
	 * @param value Property value.
	 * @return A set of strings.
	 */
	private static Set<String> asSet(String value) {
		String[] values = value.split(LIST_DELIMITER);
		HashSet<String> set = new HashSet<>();
		for (String item : values) {
			item = item.trim();
			if (StringUtils.isBlank(item)) {
				// Ignore blank lines in the property.
				continue;
			}
			set.add(item);
		}
		return set;
	}

}
