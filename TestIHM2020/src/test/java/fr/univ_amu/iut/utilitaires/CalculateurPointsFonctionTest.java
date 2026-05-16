package fr.univ_amu.iut.utilitaires;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/** Tests fonctionnels de {@link CalculateurPointsFonction}. */
class CalculateurPointsFonctionTest {

  @Test
  void le_calculateur_produit_1001_points_entre_xMin_et_xMax() throws Exception {
    Expression f = new Analyseur("x").analyser();
    CalculateurPointsFonction calc = new CalculateurPointsFonction(f, 0.0, 10.0);
    assertThat(calc.getListePoints())
        .as("le calculateur doit produire exactement 1001 points (1000 pas)")
        .hasSize(1001);
  }

  @Test
  void le_premier_et_le_dernier_point_couvrent_l_intervalle_xMin_xMax() throws Exception {
    Expression f = new Analyseur("x").analyser();
    CalculateurPointsFonction calc = new CalculateurPointsFonction(f, -5.0, 15.0);
    Basic2DPoint premier = calc.getListePoints().get(0);
    Basic2DPoint dernier = calc.getListePoints().get(1000);
    assertThat(premier.getX())
        .as("le premier point a pour abscisse xMin")
        .isCloseTo(-5.0, org.assertj.core.data.Offset.offset(1e-9));
    assertThat(dernier.getX())
        .as("le dernier point a pour abscisse xMax (a un epsilon flottant pres)")
        .isCloseTo(15.0, org.assertj.core.data.Offset.offset(1e-9));
  }

  @Test
  void yMin_et_yMax_sont_corrects_pour_la_fonction_identite() throws Exception {
    Expression f = new Analyseur("x").analyser();
    CalculateurPointsFonction calc = new CalculateurPointsFonction(f, -2.0, 8.0);
    assertThat(calc.getYMin())
        .as("pour f(x) = x, yMin sur [-2, 8] vaut -2")
        .isCloseTo(-2.0, org.assertj.core.data.Offset.offset(1e-9));
    assertThat(calc.getYMax())
        .as("pour f(x) = x, yMax sur [-2, 8] vaut 8 (a un epsilon flottant pres)")
        .isCloseTo(8.0, org.assertj.core.data.Offset.offset(1e-9));
  }
}
