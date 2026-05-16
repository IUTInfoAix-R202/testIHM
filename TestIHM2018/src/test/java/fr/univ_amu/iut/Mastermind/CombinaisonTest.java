package fr.univ_amu.iut.Mastermind;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/** Tests fonctionnels du POJO {@link Combinaison} (Mastermind JavaFX 2018). */
class CombinaisonTest {

  @Test
  void une_combinaison_neuve_a_la_taille_demandee() {
    Combinaison combi = new Combinaison(4);
    assertThat(combi.getNombrePions())
        .as("une combinaison de taille 4 doit avoir 4 emplacements")
        .isEqualTo(4);
  }

  @Test
  void setPion_modifie_le_pion_a_la_position_donnee() {
    Combinaison combi = new Combinaison(4);
    combi.setPion(1, PionJeu.BLEU);
    assertThat(combi.getPion(1))
        .as("le pion en position 1 doit etre BLEU apres setPion")
        .isEqualTo(PionJeu.BLEU);
  }

  @Test
  void contient_distingue_les_pions_presents_des_absents() {
    Combinaison combi = new Combinaison(4);
    combi.setPion(0, PionJeu.ROUGE);
    combi.setPion(1, PionJeu.VERT);
    assertThat(combi.contient(PionJeu.ROUGE)).as("ROUGE est present").isTrue();
    assertThat(combi.contient(PionJeu.JAUNE)).as("JAUNE absent").isFalse();
  }

  @Test
  void generer_une_combinaison_aleatoire_n_introduit_pas_de_pion_VIDE() {
    Combinaison combi = Combinaison.générerCombinaisonAléatoire();
    for (int i = 0; i < combi.getNombrePions(); i++) {
      assertThat(combi.getPion(i))
          .as("position %d : pion aleatoire doit etre non-VIDE", i)
          .isNotEqualTo(PionJeu.VIDE);
    }
  }
}
