package fr.univ_amu.iut.taquin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/** Tests de la classe POJO {@link Position} (pas besoin de demarrer JavaFX). */
class PositionTest {

  @Test
  void une_position_expose_ses_coordonnees_x_et_y() {
    Position p = new Position(2, 3);
    assertThat(p.getX()).as("getX() doit retourner 2").isEqualTo(2);
    assertThat(p.getY()).as("getY() doit retourner 3").isEqualTo(3);
  }

  @Test
  void deux_positions_avec_les_memes_coordonnees_sont_egales() {
    assertThat(new Position(1, 4))
        .as("equals doit comparer les coordonnees")
        .isEqualTo(new Position(1, 4));
  }

  @Test
  void deux_positions_avec_des_coordonnees_differentes_ne_sont_pas_egales() {
    assertThat(new Position(1, 4))
        .as("(1,4) et (4,1) sont differentes (ordre des coordonnees)")
        .isNotEqualTo(new Position(4, 1));
  }
}
