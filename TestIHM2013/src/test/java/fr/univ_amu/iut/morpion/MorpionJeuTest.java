package fr.univ_amu.iut.morpion;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests fonctionnels du modele {@link MorpionJeu} (questions 1 a 7 de l'enonce 2013). */
class MorpionJeuTest {

  private MorpionJeu jeu;

  @BeforeEach
  void init() {
    jeu = new MorpionJeu();
  }

  @Test
  void la_grille_initiale_n_a_aucune_case_remplie() {
    assertThat(jeu.grilleEstRemplie())
        .as("la grille neuve doit etre entierement libre")
        .isFalse();
  }

  @Test
  void l_autre_joueur_de_0_est_1_et_inversement() {
    assertThat(jeu.autreJoueur(0)).as("autreJoueur(0) doit etre 1").isEqualTo(1);
    assertThat(jeu.autreJoueur(1)).as("autreJoueur(1) doit etre 0").isEqualTo(0);
  }

  @Test
  void les_noms_des_joueurs_sont_Joueur_1_et_Joueur_2() {
    assertThat(jeu.nomJoueur(0)).as("nomJoueur(0) doit etre Joueur 1").isEqualTo("Joueur 1");
    assertThat(jeu.nomJoueur(1)).as("nomJoueur(1) doit etre Joueur 2").isEqualTo("Joueur 2");
  }

  @Test
  void la_grille_est_entierement_remplie_apres_9_coups() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        jeu.enregistrerCoupJoueur(i, j, (i + j) % 2);
      }
    }
    assertThat(jeu.grilleEstRemplie())
        .as("apres 9 coups, la grille doit etre entierement remplie")
        .isTrue();
  }

  @Test
  void un_alignement_horizontal_de_3_marques_fait_gagner() {
    jeu.enregistrerCoupJoueur(1, 0, 0);
    jeu.enregistrerCoupJoueur(1, 1, 0);
    jeu.enregistrerCoupJoueur(1, 2, 0);
    assertThat(jeu.aGagne(0))
        .as("3 marques alignees horizontalement sur la ligne du milieu fait gagner le joueur 0")
        .isTrue();
    assertThat(jeu.aGagne(1))
        .as("le joueur 1 n'a aucune marque, il n'a pas gagne")
        .isFalse();
  }

  @Test
  void un_alignement_vertical_de_3_marques_fait_gagner() {
    jeu.enregistrerCoupJoueur(0, 2, 1);
    jeu.enregistrerCoupJoueur(1, 2, 1);
    jeu.enregistrerCoupJoueur(2, 2, 1);
    assertThat(jeu.aGagne(1))
        .as("3 marques alignees verticalement sur la colonne 2 fait gagner le joueur 1")
        .isTrue();
  }

  @Test
  void un_alignement_diagonal_descendant_fait_gagner() {
    jeu.enregistrerCoupJoueur(0, 0, 0);
    jeu.enregistrerCoupJoueur(1, 1, 0);
    jeu.enregistrerCoupJoueur(2, 2, 0);
    assertThat(jeu.aGagne(0))
        .as("la diagonale (0,0)-(1,1)-(2,2) fait gagner le joueur 0")
        .isTrue();
  }

  @Test
  void un_alignement_diagonal_ascendant_fait_gagner() {
    jeu.enregistrerCoupJoueur(0, 2, 1);
    jeu.enregistrerCoupJoueur(1, 1, 1);
    jeu.enregistrerCoupJoueur(2, 0, 1);
    assertThat(jeu.aGagne(1))
        .as("l'anti-diagonale (0,2)-(1,1)-(2,0) fait gagner le joueur 1")
        .isTrue();
  }
}
