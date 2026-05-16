package fr.univ_amu.iut.othello;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests fonctionnels du modele / vue {@link Othellier} (4x4) en mode AWT headless.
 *
 * <p>Inspires des tests du bonus 10 du TP3 (qui traite un Othello JavaFX 8x8). Adaptes ici a la
 * version 2014 ecrite en Swing : pas besoin de demarrer JavaFX, on instancie directement {@link
 * Othellier} avec un {@link OthelloIHM} pere {@code null} (un null-check sur {@code pere} a ete
 * ajoute dans le listener pour permettre cet usage).
 *
 * <p>Les clics sont simules via {@link javax.swing.JButton#doClick()} - equivalent Swing du {@code
 * fire()} JavaFX utilise dans le TP3.
 */
class OthellierTest {

  private static final int TAILLE = 4;

  private Othellier othellier;

  @BeforeEach
  void init() {
    // L'enum Joueur a des scores statiques mutables : remettre a zero avant chaque test.
    Joueur.initialiserScores();
    othellier = new Othellier(null, TAILLE);
  }

  // -- 1. Etat initial ------------------------------------------------------

  @Test
  void un_othellier_neuf_positionne_les_quatre_pions_au_centre() {
    int m = TAILLE / 2;
    assertThat(othellier.getCase(m - 1, m - 1).getPossesseur())
        .as("la case centre haut-gauche doit etre BLANC")
        .isEqualTo(Joueur.BLANC);
    assertThat(othellier.getCase(m, m).getPossesseur())
        .as("la case centre bas-droite doit etre BLANC")
        .isEqualTo(Joueur.BLANC);
    assertThat(othellier.getCase(m - 1, m).getPossesseur())
        .as("la case centre haut-droite doit etre NOIR")
        .isEqualTo(Joueur.NOIR);
    assertThat(othellier.getCase(m, m - 1).getPossesseur())
        .as("la case centre bas-gauche doit etre NOIR")
        .isEqualTo(Joueur.NOIR);
  }

  @Test
  void le_joueur_noir_joue_en_premier() {
    assertThat(othellier.getJoueurCourant())
        .as("NOIR doit jouer en premier")
        .isEqualTo(Joueur.NOIR);
  }

  @Test
  void les_scores_initiaux_sont_de_2_pour_chaque_joueur() {
    assertThat(Joueur.NOIR.getScore()).as("NOIR a 2 pions au demarrage").isEqualTo(2);
    assertThat(Joueur.BLANC.getScore()).as("BLANC a 2 pions au demarrage").isEqualTo(2);
  }

  // -- 2. Coups jouables et clics valides ----------------------------------

  @Test
  void le_joueur_noir_dispose_de_quatre_coups_legaux_au_demarrage() {
    List<Case> jouables = othellier.casesJouables();
    assertThat(jouables)
        .as("en 4x4 au demarrage, NOIR doit avoir exactement 4 coups legaux")
        .hasSize(4);
    List<String> coords = jouables.stream().map(c -> c.getLigne() + "," + c.getColonne()).toList();
    assertThat(coords)
        .as("les 4 coups legaux de NOIR au demarrage sont (0,1) (1,0) (2,3) (3,2)")
        .containsExactlyInAnyOrder("0,1", "1,0", "2,3", "3,2");
  }

  @Test
  void jouer_en_0_1_au_demarrage_retourne_le_pion_blanc_de_1_1() {
    othellier.getCase(0, 1).doClick();
    assertThat(othellier.getCase(0, 1).getPossesseur())
        .as("le pion noir est pose en (0,1)")
        .isEqualTo(Joueur.NOIR);
    assertThat(othellier.getCase(1, 1).getPossesseur())
        .as("le pion blanc encadre en (1,1) doit etre retourne en NOIR")
        .isEqualTo(Joueur.NOIR);
    assertThat(Joueur.NOIR.getScore())
        .as("NOIR : 2 initial + 1 pose + 1 capture = 4")
        .isEqualTo(4);
    assertThat(Joueur.BLANC.getScore()).as("BLANC : 2 initial - 1 capture = 1").isEqualTo(1);
    assertThat(othellier.getJoueurCourant())
        .as("apres un coup valide, la main passe a BLANC")
        .isEqualTo(Joueur.BLANC);
  }

  // -- 3. Coups invalides --------------------------------------------------

  @Test
  void cliquer_sur_une_case_non_jouable_est_ignore() {
    othellier.getCase(0, 0).doClick();
    assertThat(othellier.getCase(0, 0).getPossesseur())
        .as("la case (0,0) ne capture rien et reste vide")
        .isEqualTo(Joueur.PERSONNE);
    assertThat(othellier.getJoueurCourant())
        .as("la main reste a NOIR si son coup etait illegal")
        .isEqualTo(Joueur.NOIR);
  }

  @Test
  void cliquer_sur_une_case_occupee_est_ignore() {
    othellier.getCase(1, 1).doClick();
    assertThat(othellier.getCase(1, 1).getPossesseur())
        .as("la case (1,1) etait BLANC et le reste")
        .isEqualTo(Joueur.BLANC);
    assertThat(othellier.getJoueurCourant())
        .as("la main reste a NOIR : un clic sur une case occupee est ignore")
        .isEqualTo(Joueur.NOIR);
  }

  // -- 4. Captures dans differentes directions ----------------------------

  @Test
  void une_capture_en_diagonale_retourne_le_pion_adverse() {
    // NOIR (0,1) capture BLANC en (1,1) verticalement.
    othellier.getCase(0, 1).doClick();
    // BLANC (0,0) capture NOIR en (1,1) via diagonale vers (2,2)=BLANC.
    othellier.getCase(0, 0).doClick();
    assertThat(othellier.getCase(0, 0).getPossesseur())
        .as("(0,0) appartient maintenant a BLANC")
        .isEqualTo(Joueur.BLANC);
    assertThat(othellier.getCase(1, 1).getPossesseur())
        .as("(1,1) doit re-basculer en BLANC via la diagonale (0,0)->(2,2)")
        .isEqualTo(Joueur.BLANC);
    assertThat(othellier.getJoueurCourant())
        .as("apres le coup BLANC, la main retourne a NOIR")
        .isEqualTo(Joueur.NOIR);
  }

  @Test
  void une_partie_complete_jusqu_au_blocage_reste_coherente() {
    // Sequence de coups arbitraire jusqu'au moment ou un joueur ne peut plus jouer.
    othellier.getCase(0, 1).doClick(); // NOIR
    othellier.getCase(0, 0).doClick(); // BLANC
    // Apres ces 2 coups : 6 pions sur le plateau (4 initiaux retournes + 2 poses).
    long noirs = compterPions(Joueur.NOIR);
    long blancs = compterPions(Joueur.BLANC);
    assertThat(noirs + blancs)
        .as("apres 2 coups (1 pose chacun), il y a 6 pions sur le plateau 4x4")
        .isEqualTo(6);
    assertThat(noirs)
        .as("nombre de pions NOIR doit correspondre au score")
        .isEqualTo(Joueur.NOIR.getScore());
    assertThat(blancs)
        .as("nombre de pions BLANC doit correspondre au score")
        .isEqualTo(Joueur.BLANC.getScore());
  }

  // -- 5. Nouvelle partie --------------------------------------------------

  @Test
  void nouvelle_partie_reinitialise_le_plateau_les_scores_et_le_joueur_courant() {
    othellier.getCase(0, 1).doClick();
    othellier.nouvellePartie();

    int m = TAILLE / 2;
    assertThat(othellier.getCase(0, 1).getPossesseur())
        .as("(0,1) doit redevenir vide apres nouvelle partie")
        .isEqualTo(Joueur.PERSONNE);
    assertThat(othellier.getCase(m - 1, m - 1).getPossesseur())
        .as("les 4 pions centraux sont repositionnes")
        .isEqualTo(Joueur.BLANC);
    assertThat(othellier.getJoueurCourant())
        .as("NOIR doit rejouer en premier")
        .isEqualTo(Joueur.NOIR);
    assertThat(Joueur.NOIR.getScore()).as("scores remis a 2").isEqualTo(2);
    assertThat(Joueur.BLANC.getScore()).as("scores remis a 2").isEqualTo(2);
  }

  // -- helper --

  private long compterPions(Joueur joueur) {
    long total = 0;
    for (int i = 0; i < TAILLE; i++) {
      for (int j = 0; j < TAILLE; j++) {
        if (othellier.getCase(i, j).getPossesseur() == joueur) {
          total++;
        }
      }
    }
    return total;
  }
}
