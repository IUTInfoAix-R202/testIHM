package fr.univ_amu.iut.modele;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/** Tests de la classe {@link Plateau} : zones, contraintes de pose, alignements, victoire. */
class PlateauTest {

  private static Piece blanc(Forme forme) {
    return new Piece(forme, Joueur.BLANC);
  }

  private static Piece noir(Forme forme) {
    return new Piece(forme, Joueur.NOIR);
  }

  @ParameterizedTest
  @CsvSource({
    "0,0,0", "0,1,0", "1,0,0", "1,1,0",
    "0,2,1", "0,3,1", "1,2,1", "1,3,1",
    "2,0,2", "2,1,2", "3,0,2", "3,1,2",
    "2,2,3", "2,3,3", "3,2,3", "3,3,3"
  })
  void zoneDeDecoupeLePlateauEnQuatreZonesDe2x2(int ligne, int colonne, int zoneAttendue) {
    assertThat(Plateau.zoneDe(ligne, colonne)).isEqualTo(zoneAttendue);
  }

  @Test
  void surUnPlateauVideToutesLesPosesSontPossibles() {
    Plateau plateau = new Plateau();
    assertThat(plateau.estVide(0, 0)).isTrue();
    assertThat(plateau.peutPoser(Forme.CUBE, 0, 0)).isTrue();
  }

  @Test
  void onNePeutPasPoserDeuxFoisLaMemeFormeSurUneLigne() {
    Plateau plateau = new Plateau();
    plateau.poser(blanc(Forme.CUBE), 0, 0);
    assertThat(plateau.peutPoser(Forme.CUBE, 0, 3)).isFalse();
  }

  @Test
  void onNePeutPasPoserDeuxFoisLaMemeFormeSurUneColonne() {
    Plateau plateau = new Plateau();
    plateau.poser(blanc(Forme.CUBE), 0, 0);
    assertThat(plateau.peutPoser(Forme.CUBE, 3, 0)).isFalse();
  }

  @Test
  void onNePeutPasPoserDeuxFoisLaMemeFormeDansUneZone() {
    Plateau plateau = new Plateau();
    plateau.poser(blanc(Forme.CUBE), 0, 0);
    assertThat(plateau.peutPoser(Forme.CUBE, 1, 1)).isFalse();
  }

  @Test
  void laContrainteIgnoreLeProprietaireDeLaPiece() {
    Plateau plateau = new Plateau();
    plateau.poser(noir(Forme.CUBE), 0, 0);
    // Meme si c'est une piece NOIR, BLANC ne peut pas poser un CUBE sur la meme ligne.
    assertThat(plateau.peutPoser(Forme.CUBE, 0, 1)).isFalse();
  }

  @Test
  void onPeutPoserUneFormeDifferenteSurLaMemeLigne() {
    Plateau plateau = new Plateau();
    plateau.poser(blanc(Forme.CUBE), 0, 0);
    assertThat(plateau.peutPoser(Forme.SPHERE, 0, 1)).isTrue();
  }

  @Test
  void poserUnCoupInterditLeveUneException() {
    Plateau plateau = new Plateau();
    plateau.poser(blanc(Forme.CUBE), 0, 0);
    assertThatThrownBy(() -> plateau.poser(noir(Forme.CUBE), 0, 1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("interdit");
  }

  @Test
  void unAlignementDeQuatreFormesDifferentesEstComplet() {
    Piece[] alignement = {
      blanc(Forme.CUBE), noir(Forme.SPHERE), blanc(Forme.CYLINDRE), noir(Forme.CONE)
    };
    assertThat(Plateau.estAlignementComplet(alignement)).isTrue();
  }

  @Test
  void unAlignementAvecUneFormeRepeteeNEstPasComplet() {
    Piece[] alignement = {
      blanc(Forme.CUBE), noir(Forme.CUBE), blanc(Forme.CYLINDRE), noir(Forme.CONE)
    };
    assertThat(Plateau.estAlignementComplet(alignement)).isFalse();
  }

  @Test
  void unAlignementIncompletNEstPasComplet() {
    Piece[] alignement = {blanc(Forme.CUBE), noir(Forme.SPHERE), null, noir(Forme.CONE)};
    assertThat(Plateau.estAlignementComplet(alignement)).isFalse();
  }

  @Test
  void completerUneLigneAvecLesQuatreFormesEstUneVictoire() {
    Plateau plateau = new Plateau();
    plateau.poser(blanc(Forme.CUBE), 0, 0);
    plateau.poser(noir(Forme.SPHERE), 0, 1);
    plateau.poser(blanc(Forme.CYLINDRE), 0, 2);
    plateau.poser(noir(Forme.CONE), 0, 3);
    assertThat(plateau.estVictoireApres(0, 3)).isTrue();
  }

  @Test
  void uneLigneIncompleteNEstPasUneVictoire() {
    Plateau plateau = new Plateau();
    plateau.poser(blanc(Forme.CUBE), 0, 0);
    plateau.poser(noir(Forme.SPHERE), 0, 1);
    assertThat(plateau.estVictoireApres(0, 1)).isFalse();
  }
}
