package fr.univ_amu.iut.othello;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests de l'enum {@link Joueur} : alternance et gestion des scores. */
class JoueurTest {

  @BeforeEach
  void init() {
    Joueur.initialiserScores();
  }

  @Test
  void le_suivant_du_joueur_NOIR_est_BLANC() {
    assertThat(Joueur.NOIR.suivant())
        .as("suivant(NOIR) doit etre BLANC")
        .isEqualTo(Joueur.BLANC);
  }

  @Test
  void le_suivant_du_joueur_BLANC_est_NOIR() {
    assertThat(Joueur.BLANC.suivant())
        .as("suivant(BLANC) doit etre NOIR")
        .isEqualTo(Joueur.NOIR);
  }

  @Test
  void le_suivant_du_joueur_PERSONNE_reste_PERSONNE() {
    assertThat(Joueur.PERSONNE.suivant())
        .as("suivant(PERSONNE) est PERSONNE - cas terminal de fin de partie")
        .isEqualTo(Joueur.PERSONNE);
  }

  @Test
  void initialiser_les_scores_remet_BLANC_et_NOIR_a_zero() {
    Joueur.NOIR.incrementerScore();
    Joueur.BLANC.incrementerScore();
    Joueur.BLANC.incrementerScore();
    Joueur.initialiserScores();
    assertThat(Joueur.NOIR.getScore()).as("NOIR remis a 0").isEqualTo(0);
    assertThat(Joueur.BLANC.getScore()).as("BLANC remis a 0").isEqualTo(0);
  }

  @Test
  void incrementer_et_decrementer_les_scores_modifie_l_etat() {
    Joueur.NOIR.incrementerScore();
    Joueur.NOIR.incrementerScore();
    Joueur.NOIR.decrementerScore();
    assertThat(Joueur.NOIR.getScore())
        .as("apres 2 incrementer + 1 decrementer, score = 1")
        .isEqualTo(1);
  }
}
