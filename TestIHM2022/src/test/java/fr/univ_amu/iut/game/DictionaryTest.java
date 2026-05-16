package fr.univ_amu.iut.game;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DictionaryTest {

    @Test
    void testThatEmptyDictionaryHasNoWords() {
        Dictionary dictionary = new Dictionary();
        assertThat(dictionary.wordsCount()).isEqualTo(0);
    }

    @Test
    void testThatSingleDictionaryReturns1AsCount() {
        Dictionary dictionary = new Dictionary(new Word("happy"));
        assertThat(dictionary.wordsCount()).isEqualTo(1);
    }

    @Test
    void testThatDictionaryDoesNotIncludeWord() {
        Dictionary dictionary = new Dictionary(new Word("happy"));
        assertThat(dictionary.includesWord(new Word("sadly"))).isFalse();
    }

    @Test
    void testThatDictionaryIncludesWord() {
        Dictionary dictionary = new Dictionary(new Word("happy"));
        assertThat(dictionary.includesWord(new Word("happy"))).isTrue();
    }

}