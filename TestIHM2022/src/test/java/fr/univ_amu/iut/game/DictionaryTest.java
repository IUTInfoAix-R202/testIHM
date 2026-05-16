package fr.univ_amu.iut.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DictionaryTest {

  @Test
  void un_dictionnaire_vide_ne_contient_aucun_mot() {
    Dictionary dictionary = new Dictionary();
    assertThat(dictionary.wordsCount())
        .as("le dictionnaire vide doit retourner 0 mots")
        .isEqualTo(0);
  }

  @Test
  void un_dictionnaire_avec_un_mot_a_pour_taille_1() {
    Dictionary dictionary = new Dictionary(new Word("happy"));
    assertThat(dictionary.wordsCount())
        .as("le dictionnaire a un mot doit retourner 1 mot")
        .isEqualTo(1);
  }

  @Test
  void le_dictionnaire_ne_contient_pas_un_mot_absent() {
    Dictionary dictionary = new Dictionary(new Word("happy"));
    assertThat(dictionary.includesWord(new Word("sadly")))
        .as("le dictionnaire ne doit pas contenir un mot qui n'a jamais ete ajoute")
        .isFalse();
  }

  @Test
  void le_dictionnaire_contient_un_mot_ajoute() {
    Dictionary dictionary = new Dictionary(new Word("happy"));
    assertThat(dictionary.includesWord(new Word("happy")))
        .as("le dictionnaire doit contenir le mot ajoute a la construction")
        .isTrue();
  }
}
