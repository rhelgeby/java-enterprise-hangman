package no.helgeby.hangman.model;

import java.util.ArrayList;
import java.util.Random;

public class WordList {

	private Random random = new Random();
	private ArrayList<String> words;

	public WordList() {
		words = new ArrayList<>();
		addEasyJavaWords();
	}

	// TODO: Add support from reading from file. Use a different class for this.

	public static WordList easy() {
		WordList list = new WordList();
		list.addEasyJavaWords();
		return list;
	}

	public static WordList medium() {
		WordList list = new WordList();
		list.addEasyJavaWords();
		list.addMediumJavaWords();
		return list;
	}

	public static WordList hard() {
		WordList list = new WordList();
		list.addMediumJavaWords();
		list.addDifficultJavaWords();
		return list;
	}

	public static WordList all() {
		WordList list = new WordList();
		list.addEasyJavaWords();
		list.addMediumJavaWords();
		list.addDifficultJavaWords();
		return list;
	}

	public String getRandomWord() {
		int index = random.nextInt(words.size());
		return words.get(index).toUpperCase();
	}

	public int size() {
		return words.size();
	}

	// It's arguable what is difficult and easy. In this list it organized by how
	// basic the word is.

	private void addEasyJavaWords() {
		words.add("boolean");
		words.add("byte");
		words.add("char");
		words.add("class");
		words.add("do");
		words.add("double");
		words.add("else");
		words.add("enum");
		words.add("false");
		words.add("float");
		words.add("for");
		words.add("if");
		words.add("int");
		words.add("long");
		words.add("new");
		words.add("null");
		words.add("private");
		words.add("public");
		words.add("return");
		words.add("short");
		words.add("true");
		words.add("while");
	}

	private void addMediumJavaWords() {
		words.add("abstract");
		words.add("break");
		words.add("catch");
		words.add("class");
		words.add("continue");
		words.add("default");
		words.add("extends");
		words.add("final");
		words.add("implements");
		words.add("import");
		words.add("interface");
		words.add("switch");
		words.add("throw");
		words.add("throws");
		words.add("try");
		words.add("void");
	}

	private void addDifficultJavaWords() {
		words.add("finally");
		words.add("instanceof");
		words.add("native");
		words.add("package");
		words.add("protected");
		words.add("static");
		words.add("super");
		words.add("synchronized");
		words.add("this");
		words.add("transient");
		words.add("volatile");
	}

}
