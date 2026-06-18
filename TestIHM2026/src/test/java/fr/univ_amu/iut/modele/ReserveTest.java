package fr.univ_amu.iut.modele;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

/** Tests de la classe {@link Reserve} (huit pieces : deux de chaque forme). */
class ReserveTest {

  @Test
  void uneReserveNeuveContientDeuxPiecesDeChaqueForme() {
    Reserve reserve = new Reserve(Joueur.BLANC);
    for (Forme forme : Forme.values()) {
      assertThat(reserve.compte(forme)).isEqualTo(2);
    }
  }

  @Test
  void prendreDiminueLeCompteEtRenvoieUnePieceDuBonProprietaire() {
    Reserve reserve = new Reserve(Joueur.NOIR);
    Piece prise = reserve.prendre(Forme.CONE);
    assertThat(prise).isEqualTo(new Piece(Forme.CONE, Joueur.NOIR));
    assertThat(reserve.compte(Forme.CONE)).isEqualTo(1);
  }

  @Test
  void prendreUneFormeEpuiseeLeveUneException() {
    Reserve reserve = new Reserve(Joueur.BLANC);
    reserve.prendre(Forme.CUBE);
    reserve.prendre(Forme.CUBE);
    assertThatThrownBy(() -> reserve.prendre(Forme.CUBE))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Plus de piece");
  }

  @Test
  void uneReserveEstVideQuandToutesLesPiecesSontPrises() {
    Reserve reserve = new Reserve(Joueur.BLANC);
    for (Forme forme : Forme.values()) {
      reserve.prendre(forme);
      reserve.prendre(forme);
    }
    assertThat(reserve.estVide()).isTrue();
  }
}
