package fr.univ_amu.iut.game;

import java.util.Arrays;

public class Dictionary {
    private final Word[] words;

    public Dictionary(Word... words) {
        this.words = words;
    }

    public int wordsCount() {
        return words.length;
    }

    public boolean includesWord(Word word) {
        return Arrays.asList(words).contains(word);
    }
}
