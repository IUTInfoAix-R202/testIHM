package fr.univ_amu.iut.exercice2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests fonctionnels du modele {@link Rectangle} (exercice 2 du test 2021).
 *
 * <p>Le rectangle est defini par deux coins A=(xA, yA) et B=(xB, yB), et son perimetre est calcule
 * via un binding JavaFX qui gere les cas ou A est avant ou apres B sur chaque axe (valeur
 * absolue). Pas besoin de demarrer la plateforme JavaFX : les Properties et Bindings sont
 * autonomes.
 */
class RectangleTest {

  @Test
  void le_perimetre_d_un_rectangle_neuf_est_nul() {
    Rectangle r = new Rectangle();
    assertThat(r.perimeterProperty().get())
        .as("a la construction, xA=yA=xB=yB=0 donc perimetre = 2*(0+0) = 0")
        .isEqualTo(0);
  }

  @Test
  void le_perimetre_d_un_rectangle_5_par_2_est_14() {
    Rectangle r = new Rectangle();
    r.xAProperty().set(0);
    r.yAProperty().set(0);
    r.xBProperty().set(5);
    r.yBProperty().set(2);
    assertThat(r.perimeterProperty().get())
        .as("rectangle (0,0)-(5,2) : largeur=5, hauteur=2, perimetre = 2*(5+2) = 14")
        .isEqualTo(14);
  }

  @Test
  void le_perimetre_est_le_meme_quand_A_et_B_sont_inverses() {
    Rectangle r = new Rectangle();
    r.xAProperty().set(5);
    r.yAProperty().set(2);
    r.xBProperty().set(0);
    r.yBProperty().set(0);
    assertThat(r.perimeterProperty().get())
        .as("rectangle (5,2)-(0,0) : meme perimetre que (0,0)-(5,2) grace a la valeur absolue")
        .isEqualTo(14);
  }

  @Test
  void le_perimetre_d_un_rectangle_diagonal_negatif_reste_positif() {
    Rectangle r = new Rectangle();
    r.xAProperty().set(3);
    r.yAProperty().set(7);
    r.xBProperty().set(-1);
    r.yBProperty().set(-2);
    // largeur = |3 - (-1)| = 4, hauteur = |7 - (-2)| = 9, perimetre = 2*(4+9) = 26
    assertThat(r.perimeterProperty().get())
        .as("rectangle (3,7)-(-1,-2) : perimetre = 2*(4+9) = 26")
        .isEqualTo(26);
  }

  @Test
  void modifier_xB_recalcule_le_perimetre_immediatement() {
    Rectangle r = new Rectangle();
    r.xAProperty().set(0);
    r.yAProperty().set(0);
    r.xBProperty().set(4);
    r.yBProperty().set(3);
    assertThat(r.perimeterProperty().get())
        .as("rectangle (0,0)-(4,3) : perimetre initial 2*(4+3)=14")
        .isEqualTo(14);

    r.xBProperty().set(10);

    assertThat(r.perimeterProperty().get())
        .as("modifier xB a 10 : perimetre = 2*(10+3) = 26 (binding live)")
        .isEqualTo(26);
  }

  @Test
  void modifier_yA_recalcule_le_perimetre_immediatement() {
    Rectangle r = new Rectangle();
    r.xBProperty().set(2);
    r.yBProperty().set(3);
    assertThat(r.perimeterProperty().get())
        .as("rectangle (0,0)-(2,3) : perimetre 2*(2+3)=10")
        .isEqualTo(10);

    r.yAProperty().set(8);

    assertThat(r.perimeterProperty().get())
        .as("modifier yA a 8 : hauteur=|0-8|=... attendez yA=8, yB=3 => hauteur=5, perimetre = 2*(2+5)=14")
        .isEqualTo(14);
  }
}
