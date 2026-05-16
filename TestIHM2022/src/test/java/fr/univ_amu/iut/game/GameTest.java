package fr.univ_amu.iut.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  void une_partie_qui_commence_n_a_pas_de_gagnant() {
    Dictionary dictionary = new Dictionary(new Word("happy"));
    Game game = new Game(dictionary, new Word("happy"));
    assertThat(game.hasWon())
        .as("le joueur n'a pas encore propose de mot, il ne peut pas avoir gagne")
        .isFalse();
  }

  @Test
  void une_partie_qui_commence_n_a_aucun_mot_essaye() {
    Dictionary dictionary = new Dictionary(new Word("happy"));
    Game game = new Game(dictionary, new Word("happy"));
    assertThat(game.wordsTried())
        .as("aucun mot n'a encore ete essaye au debut")
        .isEqualTo(List.of());
  }

  @Test
  void un_mot_essaye_est_enregistre_dans_la_liste() {
    Dictionary dictionary = new Dictionary(new Word("happy"), new Word("loser"));
    Game game = new Game(dictionary, new Word("happy"));
    game.addtry(new Word("loser"));
    assertThat(game.wordsTried())
        .as("le mot essaye doit etre enregistre dans wordsTried()")
        .isEqualTo(List.of(new Word("loser")));
  }

  @Test
  void apres_quatre_essais_la_partie_n_est_pas_perdue() {
    Dictionary dictionary = new Dictionary(new Word("happy"), new Word("loser"));
    Game game = new Game(dictionary, new Word("happy"));
    for (int i = 0; i < 4; i++) {
      game.addtry(new Word("loser"));
    }
    assertThat(game.hasLost())
        .as("apres 4 essais infructueux, la partie n'est pas encore perdue")
        .isFalse();
  }

  @Test
  void apres_cinq_essais_la_partie_est_perdue() {
    Dictionary dictionary = new Dictionary(new Word("happy"), new Word("loser"));
    Game game = new Game(dictionary, new Word("happy"));
    for (int i = 0; i < 5; i++) {
      game.addtry(new Word("loser"));
    }
    assertThat(game.hasLost()).as("apres 5 essais infructueux, la partie est perdue").isTrue();
  }

  @Test
  void proposer_le_mot_gagnant_fait_gagner_la_partie() {
    Dictionary dictionary = new Dictionary(new Word("happy"));
    Game game = new Game(dictionary, new Word("happy"));
    assertThat(game.hasWon()).as("au debut, la partie n'est pas gagnee").isFalse();
    game.addtry(new Word("happy"));
    assertThat(game.hasWon())
        .as("apres avoir propose le mot gagnant, la partie est gagnee")
        .isTrue();
  }

  @Test
  void proposer_un_mot_hors_dictionnaire_leve_une_exception() {
    Dictionary dictionary = new Dictionary(new Word("happy"));
    Game game = new Game(dictionary, new Word("happy"));
    assertThatThrownBy(() -> game.addtry(new Word("yyyyy")))
        .as("un mot absent du dictionnaire doit lever IllegalArgumentException")
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("YYYYY is not an english word");
  }

  @Test
  void le_mot_gagnant_doit_etre_dans_le_dictionnaire() {
    Dictionary dictionary = new Dictionary(new Word("happy"));
    assertThatThrownBy(() -> new Game(dictionary, new Word("heros")))
        .as("construire un Game avec un mot gagnant hors dictionnaire doit lever IllegalArgumentException")
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Winner word must be in dictionary");
  }
}
