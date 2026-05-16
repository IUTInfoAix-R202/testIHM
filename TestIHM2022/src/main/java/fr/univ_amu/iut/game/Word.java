package fr.univ_amu.iut.game;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Word {
    String letters;

    public Word(String word) {
        this.letters = word.toUpperCase();
        isValidWord(this.letters);
    }

    public Word(List<Character> characters) {
        this(characters.stream().map(String::valueOf).collect(Collectors.joining()));
    }

    private void isValidWord(String word) {
        hasACorrectLength(word);
        hasOnlyValidLetters(word);
    }

    private void hasOnlyValidLetters(String word) {
        if (! word.matches("[a-zA-Z]*"))
            throw new IllegalArgumentException("word contains invalid letters");
    }

    private void hasACorrectLength(String word) {
        if (word.length() > 5)
            throw new IllegalArgumentException("Too many letters. Should be 5");

        if (word.length() < 5)
            throw new IllegalArgumentException("Too few letters. Should be 5");
    }

    public char[] letters() {
        return letters.toCharArray();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        return Objects.equals(letters, word.letters);
    }

    @Override
    public int hashCode() {
        return letters != null ? letters.hashCode() : 0;
    }

    @Override
    public String toString() {
        return letters;
    }

    public List<Integer> matchesCorrectPositionsWith(Word word) {
        return IntStream.range(0, 5)
                .filter(i -> this.letters.charAt(i) == word.letters.charAt(i)).boxed().collect(Collectors.toList());
    }

    public List<Integer> matchesIncorrectPositionsWith(Word word) {
        List<Integer> matchesIncorrectPosition =
                IntStream.range(0, 5)
                        .filter(i -> word.letters.indexOf(this.letters.charAt(i)) != - 1).boxed().collect(Collectors.toList());
        matchesIncorrectPosition.removeAll(matchesCorrectPositionsWith(word));
        return matchesIncorrectPosition;
    }

    public List<Integer> matchesWrongLetterPositionWith(Word word) {
        List<Integer> matchesWrongLetterPosition = IntStream.range(0, 5).boxed().collect(Collectors.toList());
        matchesWrongLetterPosition.removeAll(matchesCorrectPositionsWith(word));
        matchesWrongLetterPosition.removeAll(matchesIncorrectPositionsWith(word));
        return matchesWrongLetterPosition;
    }
}
