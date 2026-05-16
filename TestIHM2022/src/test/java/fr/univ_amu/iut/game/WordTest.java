package fr.univ_amu.iut.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WordTest {

    @Test
    void testThatValidWordLettersAreValid() {
        Word wordleWord = new Word("valid");
        assertThat(wordleWord.letters()).isEqualTo(new char[]{'V', 'A', 'L', 'I', 'D'});
    }

    @Test
    void testThatFewWordLettersShouldRaiseException() {
        assertThatThrownBy(() -> new Word("vali")).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Too few letters. Should be 5");
    }

    @Test
    void testThatTooManyWordLettersShouldRaiseException() {
        assertThatThrownBy(() -> new Word("toolong")).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Too many letters. Should be 5");
    }

    @Test
    void testThatInvalidLettersShouldRaiseException() {
        assertThatThrownBy(() -> new Word("vali*")).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("word contains invalid letters");
    }

    @Test
    void testThatPointShouldRaiseException() {
        assertThatThrownBy(() -> new Word("v.lid")).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("word contains invalid letters");
    }

    @Test
    public void testThatTwoWordsAreNotTheSame() {
        Word firstWord = new Word("valid");
        Word secondWord = new Word("happy");
        assertThat(firstWord).isNotEqualTo(secondWord);
    }

    @Test
    public void testThatTwoWordsAreTheSame() {
        Word firstWord = new Word("valid");
        Word secondWord = new Word("valid");
        assertThat(firstWord).isEqualTo(secondWord);
    }

    @Test
    public void testThatLettersForGrassWord() {
        Word grassWord = new Word("grass");
        assertThat(grassWord.letters()).isEqualTo(new char[]{'G', 'R', 'A', 'S', 'S'});
    }

    @Test
    public void testThatTwoWordHasNoMatch() {
        Word firstWord = new Word("trees");
        Word secondWord = new Word("valid");

        assertThat(firstWord.matchesCorrectPositionsWith(secondWord)).isEqualTo(List.of());
        assertThat(firstWord.matchesIncorrectPositionsWith(secondWord)).isEqualTo(List.of());
        assertThat(firstWord.matchesWrongLetterPositionWith(secondWord)).isEqualTo(List.of(0, 1, 2, 3, 4));
    }

    @Test
    public void testThatMatchesFirstLetter() {
        Word firstWord = new Word("trees");
        Word secondWord = new Word("table");

        assertThat(firstWord.matchesCorrectPositionsWith(secondWord)).isEqualTo(List.of(0));
        assertThat(firstWord.matchesIncorrectPositionsWith(secondWord)).isEqualTo(List.of(2, 3));
        assertThat(firstWord.matchesWrongLetterPositionWith(secondWord)).isEqualTo(List.of(1, 4));
    }

    @Test
    public void testThatMatchesAllLetters() {
        Word firstWord = new Word("trees");
        Word secondWord = new Word("trees");

        assertThat(firstWord.matchesCorrectPositionsWith(secondWord)).isEqualTo(List.of(0, 1, 2, 3, 4));
        assertThat(firstWord.matchesIncorrectPositionsWith(secondWord)).isEqualTo(List.of());
        assertThat(firstWord.matchesWrongLetterPositionWith(secondWord)).isEqualTo(List.of());
    }

    @Test
    public void testThatMatchesIncorrectPositions() {
        Word firstWord = new Word("trees");
        Word secondWord = new Word("drama");

        assertThat(firstWord.matchesCorrectPositionsWith(secondWord)).isEqualTo(List.of(1));
        assertThat(firstWord.matchesIncorrectPositionsWith(secondWord)).isEqualTo(List.of());
        assertThat(firstWord.matchesWrongLetterPositionWith(secondWord)).isEqualTo(List.of(0, 2, 3, 4));

    }

    @Test
    public void testThatMatchesIncorrectPositionsWithMatch() {
        Word firstWord = new Word("alarm");
        Word secondWord = new Word("drama");

        assertThat(firstWord.matchesCorrectPositionsWith(secondWord)).isEqualTo(List.of(2));
        assertThat(firstWord.matchesIncorrectPositionsWith(secondWord)).isEqualTo(List.of(0, 3, 4));
        assertThat(firstWord.matchesWrongLetterPositionWith(secondWord)).isEqualTo(List.of(1));

        assertThat(secondWord.matchesCorrectPositionsWith(firstWord)).isEqualTo(List.of(2));
        assertThat(secondWord.matchesIncorrectPositionsWith(firstWord)).isEqualTo(List.of(1, 3, 4));
        assertThat(secondWord.matchesWrongLetterPositionWith(firstWord)).isEqualTo(List.of(0));
    }

}
