package fr.univ_amu.iut;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univ_amu.iut.modele.Etat;
import fr.univ_amu.iut.modele.Forme;
import fr.univ_amu.iut.modele.Joueur;
import org.junit.jupiter.api.Test;

/**
 * Tests du {@link QuantikViewModel}.
 *
 * <p>Les proprietes JavaFX ne demandent pas le moteur graphique : ces tests sont de simples tests
 * unitaires JUnit.
 */
class QuantikViewModelTest {

  private void jouer(QuantikViewModel vm, Forme forme, int ligne, int colonne) {
    vm.selectionner(forme);
    vm.jouerEn(ligne, colonne);
  }

  @Test
  void auDemarrageCEstAuBlancDeJouer() {
    QuantikViewModel vm = new QuantikViewModel();
    assertThat(vm.joueurCourant()).isEqualTo(Joueur.BLANC);
    assertThat(vm.etat()).isEqualTo(Etat.EN_COURS);
    assertThat(vm.statutProperty().get()).contains("BLANC");
  }

  @Test
  void jouerUnCoupLegalPoseLaPieceEtPasseLaMain() {
    QuantikViewModel vm = new QuantikViewModel();
    vm.selectionner(Forme.CUBE);
    assertThat(vm.peutJouerEn(0, 0)).isTrue();
    assertThat(vm.jouerEn(0, 0)).isTrue();
    assertThat(vm.pieceEn(0, 0).forme()).isEqualTo(Forme.CUBE);
    assertThat(vm.pieceEn(0, 0).proprietaire()).isEqualTo(Joueur.BLANC);
    assertThat(vm.joueurCourant()).isEqualTo(Joueur.NOIR);
    assertThat(vm.compte(Joueur.BLANC, Forme.CUBE)).isEqualTo(1);
  }

  @Test
  void jouerSansSelectionEchoue() {
    QuantikViewModel vm = new QuantikViewModel();
    assertThat(vm.jouerEn(0, 0)).isFalse();
  }

  @Test
  void jouerUnCoupInterditEchoueSansModifierLePlateau() {
    QuantikViewModel vm = new QuantikViewModel();
    jouer(vm, Forme.CUBE, 0, 0); // BLANC pose un CUBE
    vm.selectionner(Forme.CUBE); // NOIR veut un CUBE
    assertThat(vm.peutJouerEn(0, 1)).isFalse(); // meme ligne que le CUBE de BLANC
    assertThat(vm.jouerEn(0, 1)).isFalse();
    assertThat(vm.pieceEn(0, 1)).isNull();
  }

  @Test
  void casesValidesPourUneFormeSurPlateauVideDonneSeizeCases() {
    QuantikViewModel vm = new QuantikViewModel();
    assertThat(vm.casesValidesPour(Forme.CUBE)).hasSize(16);
  }

  @Test
  void nouvellePartieRemetTout() {
    QuantikViewModel vm = new QuantikViewModel();
    jouer(vm, Forme.CUBE, 0, 0);
    vm.nouvellePartie();
    assertThat(vm.pieceEn(0, 0)).isNull();
    assertThat(vm.joueurCourant()).isEqualTo(Joueur.BLANC);
    assertThat(vm.formeSelectionnee()).isNull();
  }

  @Test
  void completerUneLigneTermineLaPartieAvecLeBonMessage() {
    QuantikViewModel vm = new QuantikViewModel();
    jouer(vm, Forme.CUBE, 0, 0); // BLANC
    jouer(vm, Forme.SPHERE, 0, 1); // NOIR
    jouer(vm, Forme.CYLINDRE, 0, 2); // BLANC
    jouer(vm, Forme.CUBE, 3, 3); // NOIR (coup neutre)
    jouer(vm, Forme.CONE, 0, 3); // BLANC complete la ligne 0
    assertThat(vm.estTerminee()).isTrue();
    assertThat(vm.etat()).isEqualTo(Etat.VICTOIRE_BLANC);
    assertThat(vm.messageFin()).contains("BLANC");
  }
}
