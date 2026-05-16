package fr.univ_amu.iut.utilitaires;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

/**
 * Tests fonctionnels de l'{@link Analyseur} fourni dans le sujet 2020.
 *
 * <p>L'Analyseur produit une {@link Expression} a partir d'une chaine. Les operations supportees
 * (cf. enonce) sont : +, -, *, / et les fonctions exp, sin, cos, log.
 */
class AnalyseurTest {

  @Test
  void analyser_une_expression_simple_produit_une_expression_evaluable() throws Exception {
    Expression f = new Analyseur("x").analyser();
    assertThat(f.valeur(5.0))
        .as("f(x) = x doit retourner x pour tout x")
        .isEqualTo(5.0);
  }

  @Test
  void l_expression_donnee_dans_l_enonce_calcule_la_valeur_attendue_pour_x_egal_4()
      throws Exception {
    Expression f = new Analyseur("exp(-x * 0.2) * sin(x)").analyser();
    // L'enonce donne 0.0313, mais le calcul reel est -0.3401 :
    //   exp(-0.8) * sin(4) = 0.4493 * (-0.7568) ~ -0.340
    // (Coquille probable dans l'enonce. On teste la valeur reellement calculee.)
    assertThat(f.valeur(4.0))
        .as("f(x) = exp(-x*0.2)*sin(x), f(4) doit etre proche de -0.340")
        .isCloseTo(-0.340, org.assertj.core.data.Offset.offset(0.001));
  }

  @Test
  void les_quatre_operations_arithmetiques_sont_supportees() throws Exception {
    assertThat(new Analyseur("3 + 4").analyser().valeur(0))
        .as("3 + 4 = 7")
        .isEqualTo(7.0);
    assertThat(new Analyseur("10 - 3").analyser().valeur(0))
        .as("10 - 3 = 7")
        .isEqualTo(7.0);
    assertThat(new Analyseur("2 * 5").analyser().valeur(0))
        .as("2 * 5 = 10")
        .isEqualTo(10.0);
    assertThat(new Analyseur("20 / 4").analyser().valeur(0))
        .as("20 / 4 = 5")
        .isEqualTo(5.0);
  }

  @Test
  void analyser_une_expression_avec_un_identifiant_inconnu_leve_ErreurDeSyntaxe() {
    assertThatThrownBy(() -> new Analyseur("si(x)").analyser())
        .as("'si' n'est pas une fonction reconnue, l'analyseur doit lever ErreurDeSyntaxe")
        .isInstanceOf(ErreurDeSyntaxe.class);
  }
}
