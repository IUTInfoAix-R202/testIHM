package fr.univ_amu.iut.mastermind;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Smoke test minimal de l'archive 2015 - verifie juste que la classe principale se charge.
 */
class MasterMindIHMTest {

  @Test
  void la_classe_principale_se_charge() {
    assertThat(MasterMindIHM.class.getName())
        .as("la classe principale doit etre presente dans le module 2015")
        .endsWith("MasterMindIHM");
  }
}
