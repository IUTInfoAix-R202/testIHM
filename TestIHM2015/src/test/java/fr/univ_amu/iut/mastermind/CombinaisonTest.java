package fr.univ_amu.iut.mastermind;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/** Tests fonctionnels de {@link Combinaison} - le modele de la combinaison de pions. */
class CombinaisonTest {

  @Test
  void une_combinaison_neuve_a_la_taille_demandee() {
    Combinaison combi = new Combinaison(4);
    assertThat(combi.nombrePions())
        .as("une combinaison construite avec taille=4 doit avoir 4 emplacements")
        .isEqualTo(4);
  }

  @Test
  void setPion_modifie_la_couleur_a_la_position_donnee() {
    Combinaison combi = new Combinaison(4);
    combi.setPion(2, PionJeu.ROUGE);
    assertThat(combi.getPion(2))
        .as("le pion en position 2 doit etre ROUGE apres setPion")
        .isEqualTo(PionJeu.ROUGE);
  }

  @Test
  void contient_retourne_true_si_le_pion_est_present_dans_la_combinaison() {
    Combinaison combi = new Combinaison(4);
    combi.setPion(0, PionJeu.BLEU);
    combi.setPion(1, PionJeu.VERT);
    assertThat(combi.contient(PionJeu.BLEU)).as("BLEU est present en position 0").isTrue();
    assertThat(combi.contient(PionJeu.VERT)).as("VERT est present en position 1").isTrue();
    assertThat(combi.contient(PionJeu.JAUNE)).as("JAUNE n'a jamais ete pose").isFalse();
  }

  @Test
  void generer_une_combinaison_aleatoire_produit_la_taille_attendue() {
    Combinaison combi = Combinaison.genererCombinaisonAléatoire(4);
    assertThat(combi.nombrePions())
        .as("la combinaison aleatoire doit avoir la taille demandee")
        .isEqualTo(4);
  }

  @Test
  void une_combinaison_aleatoire_ne_contient_pas_de_pion_VIDE() {
    Combinaison combi = Combinaison.genererCombinaisonAléatoire(4);
    for (int i = 0; i < 4; i++) {
      assertThat(combi.getPion(i))
          .as("position %d : un pion genere aleatoirement ne doit pas etre VIDE", i)
          .isNotEqualTo(PionJeu.VIDE);
    }
  }
}
