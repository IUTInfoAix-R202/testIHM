package fr.univ_amu.iut.modele;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/** Tests de la classe {@link Piece} (record forme + proprietaire). */
class PieceTest {

  @Test
  void unePieceExposeSaFormeEtSonProprietaire() {
    Piece piece = new Piece(Forme.CUBE, Joueur.BLANC);
    assertThat(piece.forme()).isEqualTo(Forme.CUBE);
    assertThat(piece.proprietaire()).isEqualTo(Joueur.BLANC);
  }

  @Test
  void deuxPiecesIdentiquesSontEgales() {
    assertThat(new Piece(Forme.SPHERE, Joueur.NOIR))
        .isEqualTo(new Piece(Forme.SPHERE, Joueur.NOIR));
  }

  @Test
  void deuxPiecesDeProprietairesDifferentsNeSontPasEgales() {
    assertThat(new Piece(Forme.SPHERE, Joueur.NOIR))
        .isNotEqualTo(new Piece(Forme.SPHERE, Joueur.BLANC));
  }
}
