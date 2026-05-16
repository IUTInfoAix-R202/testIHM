package fr.univ_amu.iut.game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Word> wordsTried = new ArrayList<>();
    private final Dictionary dictionary;
    private final Word winnerWord;

    public Game(Dictionary dictionary, Word winnerWord) {
        if (! dictionary.includesWord(winnerWord))
            throw new IllegalArgumentException("Winner word must be in dictionary");
        this.dictionary = dictionary;
        this.winnerWord = winnerWord;
    }

    public boolean hasWon() {
        return wordsTried.contains(winnerWord);
    }

    public List<Word> wordsTried() {
        return wordsTried;
    }

    public void addtry(Word word) {
        if (dictionary != null && ! dictionary.includesWord(word))
            throw new IllegalArgumentException(word + " is not an english word");

        wordsTried.add(word);
    }

    public boolean hasLost() {
        return wordsTried.size() >= 5;
    }
}
