package fr.univ_amu.iut.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameTest {

    @Test
    void testThatEmptyGameHasNoWinner() {
        Dictionary dictionary = new Dictionary(new Word("happy"));
        Word winnerWord = new Word("happy");
        Game game = new Game(dictionary, winnerWord);
        assertThat(game.hasWon()).isFalse();
    }

    @Test
    void testThatEmptyGameHasNoWordsTried() {
        Dictionary dictionary = new Dictionary(new Word("happy"));
        Word winnerWord = new Word("happy");
        Game game = new Game(dictionary, winnerWord);
        assertThat(game.wordsTried()).isEqualTo(List.of());
    }

    @Test
    void testThatTryOneWordAndRecordIt() {
        Dictionary dictionary = new Dictionary(new Word("happy"), new Word("loser"));
        Word winnerWord = new Word("happy");
        Game game = new Game(dictionary, winnerWord);
        game.addtry(new Word("loser"));
        assertThat(game.wordsTried()).isEqualTo(List.of(new Word("loser")));
    }

    @Test
    void testThatTryFourWordsLoses() {
        Dictionary dictionary = new Dictionary(new Word("happy"), new Word("loser"));
        Word winnerWord = new Word("happy");
        Game game = new Game(dictionary, winnerWord);
        game.addtry(new Word("loser"));
        game.addtry(new Word("loser"));
        game.addtry(new Word("loser"));
        game.addtry(new Word("loser"));
        assertThat(game.hasLost()).isFalse();
    }

    @Test
    void testThatTryFiveWordsLoses() {
        Dictionary dictionary = new Dictionary(new Word("happy"), new Word("loser"));
        Word winnerWord = new Word("happy");
        Game game = new Game(dictionary, winnerWord);
        game.addtry(new Word("loser"));
        game.addtry(new Word("loser"));
        game.addtry(new Word("loser"));
        game.addtry(new Word("loser"));
        game.addtry(new Word("loser"));
        assertThat(game.hasLost()).isTrue();
    }

    @Test
    void testThatGuessesWord() {
        Dictionary dictionary = new Dictionary(new Word("happy"));
        Word winnerWord = new Word("happy");
        Game game = new Game(dictionary, winnerWord);
        assertThat(game.hasWon()).isFalse();
        game.addtry(new Word("happy"));
        assertThat(game.hasWon()).isTrue();
    }

    @Test
    void testThatTryToPlayInvalid() {
        Dictionary dictionary = new Dictionary(new Word("happy"));
        Word winnerWord = new Word("happy");
        Game game = new Game(dictionary, winnerWord);
        assertThatThrownBy(() -> game.addtry(new Word("yyyyy"))).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("YYYYY is not an english word");
    }


    @Test
    void testWinnerWordNotInDictionary() {
        Dictionary dictionary = new Dictionary(new Word("happy"));
        Word winnerWord = new Word("heros");
        assertThatThrownBy(() -> new Game(dictionary, winnerWord)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Winner word must be in dictionary");
    }
}