package fr.univ_amu.iut.modele;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

/** Tests de la classe {@link Partie} : tour de jeu, coups possibles, victoire et blocage. */
class PartieTest {

  @Test
  void uneNouvellePartieEstEnCoursEtCEstAuBlancDeJouer() {
    Partie partie = new Partie();
    assertThat(partie.etat()).isEqualTo(Etat.EN_COURS);
    assertThat(partie.joueurCourant()).isEqualTo(Joueur.BLANC);
  }

  @Test
  void surUnPlateauVideIlYaSoixanteQuatreCoupsPossibles() {
    Partie partie = new Partie();
    // 4 formes disponibles x 16 cases libres = 64 coups.
    assertThat(partie.coupsPossibles(Joueur.BLANC)).hasSize(64);
  }

  @Test
  void jouerUnCoupNonDecisifPasseLaMainAAdversaire() {
    Partie partie = new Partie();
    partie.jouer(Forme.CUBE, 0, 0);
    assertThat(partie.joueurCourant()).isEqualTo(Joueur.NOIR);
  }

  @Test
  void jouerUnCoupInterditLeveUneException() {
    Partie partie = new Partie();
    partie.jouer(Forme.CUBE, 0, 0); // BLANC
    assertThatThrownBy(() -> partie.jouer(Forme.CUBE, 0, 1)) // NOIR, meme forme, meme ligne
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void completerUneLigneFaitGagnerLeJoueurQuiPoseLaDernierePiece() {
    Partie partie = new Partie();
    partie.jouer(Forme.CUBE, 0, 0); // BLANC
    partie.jouer(Forme.SPHERE, 0, 1); // NOIR
    partie.jouer(Forme.CYLINDRE, 0, 2); // BLANC
    partie.jouer(Forme.CUBE, 3, 3); // NOIR (coup neutre, loin de la ligne 0 et de la colonne 3)
    partie.jouer(Forme.CONE, 0, 3); // BLANC complete la ligne 0
    assertThat(partie.etat()).isEqualTo(Etat.VICTOIRE_BLANC);
  }

  @Test
  void onNePeutPlusJouerUneFoisLaPartieTerminee() {
    Partie partie = new Partie();
    partie.jouer(Forme.CUBE, 0, 0);
    partie.jouer(Forme.SPHERE, 0, 1);
    partie.jouer(Forme.CYLINDRE, 0, 2);
    partie.jouer(Forme.CUBE, 3, 3);
    partie.jouer(Forme.CONE, 0, 3); // VICTOIRE_BLANC
    assertThatThrownBy(() -> partie.jouer(Forme.CUBE, 1, 1))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void unePartieJoueeGloutonnementSeTermineToujoursParUneVictoire() {
    // Strategie deterministe : toujours jouer le premier coup possible.
    // Couvre la boucle de jeu, y compris la fin par alignement ou par blocage.
    Partie partie = new Partie();
    int coups = 0;
    while (partie.etat() == Etat.EN_COURS && coups < 16) {
      List<Coup> possibles = partie.coupsPossibles(partie.joueurCourant());
      // Invariant : tant que la partie est en cours, le joueur courant a au moins un coup.
      assertThat(possibles).isNotEmpty();
      Coup choix = possibles.get(0);
      partie.jouer(choix.forme(), choix.ligne(), choix.colonne());
      coups++;
    }
    assertThat(partie.etat()).isIn(Etat.VICTOIRE_BLANC, Etat.VICTOIRE_NOIR);
  }
}
