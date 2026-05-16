package fr.univ_amu.iut.mastermind;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests fonctionnels du {@link Plateau} - le modele/vue Swing du Mastermind.
 *
 * <p>{@code Plateau extends JPanel} : les tests fonctionnent en mode AWT headless (cf. argLine
 * surefire {@code -Djava.awt.headless=true} dans le pom).
 */
class PlateauTest {

  private Plateau plateau;

  @BeforeEach
  void init() {
    plateau = new Plateau(8, 4);
  }

  @Test
  void au_demarrage_aucun_coup_n_a_ete_joue() {
    assertThat(plateau.nombreDeCoupsJoués())
        .as("le compteur de coups est a 0 au demarrage")
        .isEqualTo(0);
  }

  @Test
  void au_demarrage_la_partie_n_est_ni_gagnee_ni_perdue_ni_terminee() {
    assertThat(plateau.aGagné()).as("partie pas gagnee au demarrage").isFalse();
    assertThat(plateau.aPerdu()).as("partie pas perdue au demarrage").isFalse();
    assertThat(plateau.estFinDePartie()).as("partie pas terminee au demarrage").isFalse();
  }

  @Test
  void valider_la_rangee_avec_la_combinaison_secrete_fait_gagner() {
    Combinaison secrete = new Combinaison(4);
    secrete.setPion(0, PionJeu.ROUGE);
    secrete.setPion(1, PionJeu.BLEU);
    secrete.setPion(2, PionJeu.VERT);
    secrete.setPion(3, PionJeu.JAUNE);
    Combinaison proposition = new Combinaison(4);
    proposition.setPion(0, PionJeu.ROUGE);
    proposition.setPion(1, PionJeu.BLEU);
    proposition.setPion(2, PionJeu.VERT);
    proposition.setPion(3, PionJeu.JAUNE);
    plateau.setCombinaisonCourante(proposition);

    plateau.validerRangéeCourante(secrete);

    assertThat(plateau.aGagné())
        .as("proposer exactement la combinaison secrete fait gagner")
        .isTrue();
    assertThat(plateau.estFinDePartie())
        .as("la partie est terminee une fois gagnee")
        .isTrue();
  }

  @Test
  void valider_8_rangees_sans_trouver_la_combinaison_fait_perdre() {
    Combinaison secrete = new Combinaison(4);
    secrete.setPion(0, PionJeu.ROUGE);
    secrete.setPion(1, PionJeu.BLEU);
    secrete.setPion(2, PionJeu.VERT);
    secrete.setPion(3, PionJeu.JAUNE);

    for (int essai = 0; essai < 8; essai++) {
      Combinaison proposition = plateau.combinaisonCourante();
      proposition.setPion(0, PionJeu.MARRON);
      proposition.setPion(1, PionJeu.MARRON);
      proposition.setPion(2, PionJeu.MARRON);
      proposition.setPion(3, PionJeu.MARRON);
      plateau.validerRangéeCourante(secrete);
    }

    assertThat(plateau.aPerdu())
        .as("apres 8 essais infructueux, la partie est perdue")
        .isTrue();
    assertThat(plateau.estFinDePartie()).as("la partie est terminee").isTrue();
  }

  @Test
  void valider_une_rangee_incremente_le_compteur_de_coups() {
    Combinaison secrete = new Combinaison(4);
    secrete.setPion(0, PionJeu.ROUGE);
    secrete.setPion(1, PionJeu.BLEU);
    secrete.setPion(2, PionJeu.VERT);
    secrete.setPion(3, PionJeu.JAUNE);
    Combinaison proposition = new Combinaison(4);
    proposition.setPion(0, PionJeu.MARRON);
    proposition.setPion(1, PionJeu.MARRON);
    proposition.setPion(2, PionJeu.MARRON);
    proposition.setPion(3, PionJeu.MARRON);
    plateau.setCombinaisonCourante(proposition);

    plateau.validerRangéeCourante(secrete);

    assertThat(plateau.nombreDeCoupsJoués())
        .as("le compteur de coups doit etre a 1 apres une validation")
        .isEqualTo(1);
  }

  /**
   * Note historique : la methode {@code nouvellePartie()} d'origine ne reinitialise PAS
   * {@code rangéeCourante} ni le flag {@code aGagné} - elle se contente de vider les rangees
   * et de reactiver la rangee 0. Le test ne controle donc que le comportement reellement
   * implemente (vidage de la 1ere rangee).
   */
  @Test
  void nouvelle_partie_vide_les_rangees_meme_si_le_compteur_n_est_pas_reset() {
    Combinaison secrete = new Combinaison(4);
    secrete.setPion(0, PionJeu.ROUGE);
    secrete.setPion(1, PionJeu.BLEU);
    secrete.setPion(2, PionJeu.VERT);
    secrete.setPion(3, PionJeu.JAUNE);
    Combinaison proposition = new Combinaison(4);
    proposition.setPion(0, PionJeu.MARRON);
    proposition.setPion(1, PionJeu.MARRON);
    proposition.setPion(2, PionJeu.MARRON);
    proposition.setPion(3, PionJeu.MARRON);
    plateau.setCombinaisonCourante(proposition);
    plateau.validerRangéeCourante(secrete);

    plateau.nouvellePartie();

    Combinaison rangee0 = plateau.combinaisonCourante();
    boolean au_moins_un_pion_non_vide = false;
    for (int i = 0; i < rangee0.nombrePions(); i++) {
      if (rangee0.getPion(i) != PionJeu.VIDE) {
        au_moins_un_pion_non_vide = true;
        break;
      }
    }
    assertThat(au_moins_un_pion_non_vide)
        .as("nouvellePartie() doit vider toutes les rangees (cf. boucle rangées[i].vider())")
        .isFalse();
  }
}
