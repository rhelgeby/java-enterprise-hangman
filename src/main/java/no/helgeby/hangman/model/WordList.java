package no.helgeby.hangman.model;

import java.util.ArrayList;
import java.util.Random;

public class WordList {

	private Random random = new Random();
	private ArrayList<String> words;

	public WordList() {
		words = new ArrayList<>();
		addJavaWords();
	}

	// TODO: Add support from reading from file. Use a different class for this.

	public String getRandomWord() {
		int index = random.nextInt(words.size());
		return words.get(index);
	}

	public int size() {
		return words.size();
	}

	private void addJavaWords() {
		words.add("abstract");
		words.add("boolean");
		words.add("break");
		words.add("byte");
		words.add("case");
		words.add("catch");
		words.add("char");
		words.add("class");
		words.add("continue");
		words.add("default");
		words.add("do");
		words.add("double");
		words.add("else");
		words.add("enum");
		words.add("extends");
		words.add("false");
		words.add("final");
		words.add("finally");
		words.add("float");
		words.add("for");
		words.add("if");
		words.add("implements");
		words.add("import");
		words.add("instanceof");
		words.add("int");
		words.add("interface");
		words.add("long");
		words.add("native");
		words.add("new");
		words.add("null");
		words.add("package");
		words.add("private");
		words.add("protected");
		words.add("public");
		words.add("return");
		words.add("short");
		words.add("static");
		words.add("super");
		words.add("switch");
		words.add("synchronized");
		words.add("this");
		words.add("throw");
		words.add("throws");
		words.add("transient");
		words.add("true");
		words.add("try");
		words.add("void");
		words.add("volatile");
		words.add("while");
	}
}
