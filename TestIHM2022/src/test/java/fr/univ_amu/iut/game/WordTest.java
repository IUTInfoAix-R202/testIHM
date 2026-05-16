package fr.univ_amu.iut.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class WordTest {

  @Test
  void un_mot_valide_expose_ses_lettres_en_majuscules() {
    Word wordleWord = new Word("valid");
    assertThat(wordleWord.letters())
        .as("letters() doit renvoyer les caracteres en majuscules")
        .isEqualTo(new char[] {'V', 'A', 'L', 'I', 'D'});
  }

  @Test
  void un_mot_avec_moins_de_5_lettres_leve_une_exception() {
    assertThatThrownBy(() -> new Word("vali"))
        .as("un mot de 4 lettres ne peut pas etre construit")
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Too few letters. Should be 5");
  }

  @Test
  void un_mot_avec_plus_de_5_lettres_leve_une_exception() {
    assertThatThrownBy(() -> new Word("toolong"))
        .as("un mot de 7 lettres ne peut pas etre construit")
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Too many letters. Should be 5");
  }

  @Test
  void un_mot_avec_un_caractere_special_leve_une_exception() {
    assertThatThrownBy(() -> new Word("vali*"))
        .as("le caractere * doit etre rejete")
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("word contains invalid letters");
  }

  @Test
  void un_mot_avec_un_point_leve_une_exception() {
    assertThatThrownBy(() -> new Word("v.lid"))
        .as("le caractere . doit etre rejete")
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("word contains invalid letters");
  }

  @Test
  void deux_mots_differents_ne_sont_pas_egaux() {
    assertThat(new Word("valid"))
        .as("deux Word avec des lettres differentes ne sont pas equals")
        .isNotEqualTo(new Word("happy"));
  }

  @Test
  void deux_mots_identiques_sont_egaux() {
    assertThat(new Word("valid"))
        .as("deux Word avec les memes lettres sont equals")
        .isEqualTo(new Word("valid"));
  }

  @Test
  void un_mot_avec_lettres_dupliquees_expose_chaque_lettre() {
    Word grassWord = new Word("grass");
    assertThat(grassWord.letters())
        .as("letters() doit conserver les lettres dupliquees")
        .isEqualTo(new char[] {'G', 'R', 'A', 'S', 'S'});
  }

  @Test
  void deux_mots_sans_lettre_commune_n_ont_aucune_position_correcte() {
    Word firstWord = new Word("trees");
    Word secondWord = new Word("valid");

    assertThat(firstWord.matchesCorrectPositionsWith(secondWord))
        .as("aucune lettre n'est a la bonne position")
        .isEqualTo(List.of());
    assertThat(firstWord.matchesIncorrectPositionsWith(secondWord))
        .as("aucune lettre n'est presente a une mauvaise position")
        .isEqualTo(List.of());
    assertThat(firstWord.matchesWrongLetterPositionWith(secondWord))
        .as("toutes les lettres sont absentes du mot a deviner")
        .isEqualTo(List.of(0, 1, 2, 3, 4));
  }

  @Test
  void seule_la_premiere_lettre_est_a_la_bonne_position() {
    Word firstWord = new Word("trees");
    Word secondWord = new Word("table");

    assertThat(firstWord.matchesCorrectPositionsWith(secondWord))
        .as("la lettre T est a la position 0 dans les deux mots")
        .isEqualTo(List.of(0));
    assertThat(firstWord.matchesIncorrectPositionsWith(secondWord))
        .as("E (position 2) et E (position 3) sont presents dans 'table' mais ailleurs")
        .isEqualTo(List.of(2, 3));
    assertThat(firstWord.matchesWrongLetterPositionWith(secondWord))
        .as("R et S sont absents de 'table'")
        .isEqualTo(List.of(1, 4));
  }

  @Test
  void deux_mots_identiques_ont_toutes_les_positions_correctes() {
    Word firstWord = new Word("trees");
    Word secondWord = new Word("trees");

    assertThat(firstWord.matchesCorrectPositionsWith(secondWord))
        .as("toutes les lettres sont a la bonne position")
        .isEqualTo(List.of(0, 1, 2, 3, 4));
    assertThat(firstWord.matchesIncorrectPositionsWith(secondWord))
        .as("aucune lettre n'est mal placee")
        .isEqualTo(List.of());
    assertThat(firstWord.matchesWrongLetterPositionWith(secondWord))
        .as("aucune lettre n'est absente")
        .isEqualTo(List.of());
  }

  @Test
  void seul_le_R_est_a_la_bonne_position_pour_trees_vs_drama() {
    Word firstWord = new Word("trees");
    Word secondWord = new Word("drama");

    assertThat(firstWord.matchesCorrectPositionsWith(secondWord))
        .as("le R est a la position 1 dans les deux mots")
        .isEqualTo(List.of(1));
    assertThat(firstWord.matchesIncorrectPositionsWith(secondWord))
        .as("aucune autre lettre presente n'est mal placee")
        .isEqualTo(List.of());
    assertThat(firstWord.matchesWrongLetterPositionWith(secondWord))
        .as("les autres lettres T, E, E, S sont absentes de 'drama'")
        .isEqualTo(List.of(0, 2, 3, 4));
  }

  @Test
  void alarm_et_drama_partagent_quatre_lettres_avec_une_a_la_bonne_position() {
    Word firstWord = new Word("alarm");
    Word secondWord = new Word("drama");

    assertThat(firstWord.matchesCorrectPositionsWith(secondWord))
        .as("le A est a la position 2 dans les deux mots")
        .isEqualTo(List.of(2));
    assertThat(firstWord.matchesIncorrectPositionsWith(secondWord))
        .as("A, R, M sont presents dans 'drama' mais a une mauvaise position")
        .isEqualTo(List.of(0, 3, 4));
    assertThat(firstWord.matchesWrongLetterPositionWith(secondWord))
        .as("le L est absent de 'drama'")
        .isEqualTo(List.of(1));

    assertThat(secondWord.matchesCorrectPositionsWith(firstWord))
        .as("symetrie : le A est a la position 2 dans les deux mots")
        .isEqualTo(List.of(2));
    assertThat(secondWord.matchesIncorrectPositionsWith(firstWord))
        .as("R, M, A sont presents dans 'alarm' mais ailleurs")
        .isEqualTo(List.of(1, 3, 4));
    assertThat(secondWord.matchesWrongLetterPositionWith(firstWord))
        .as("le D est absent de 'alarm'")
        .isEqualTo(List.of(0));
  }
}
