package fr.univ_amu.iut.Mastermind;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Tests fonctionnels du {@link Plateau} Mastermind (JavaFX). */
class PlateauTest {

  @BeforeAll
  static void demarrerJavaFx() {
    try {
      Platform.startup(() -> {});
    } catch (IllegalStateException dejaDemarree) {
      // OK
    }
  }

  private <T> T surFxThread(java.util.function.Supplier<T> action) throws Exception {
    AtomicReference<T> resultat = new AtomicReference<>();
    AtomicReference<Throwable> erreur = new AtomicReference<>();
    CountDownLatch latch = new CountDownLatch(1);
    Platform.runLater(
        () -> {
          try {
            resultat.set(action.get());
          } catch (Throwable t) {
            erreur.set(t);
          } finally {
            latch.countDown();
          }
        });
    latch.await();
    if (erreur.get() != null) {
      throw new RuntimeException(erreur.get());
    }
    return resultat.get();
  }

  @Test
  void au_demarrage_aucun_coup_n_a_ete_joue() throws Exception {
    Plateau p = surFxThread(Plateau::new);
    assertThat(p.getNombreDeCoupsJoués()).as("compteur de coups a 0").isEqualTo(0);
    assertThat(p.estPartieTerminéeProperty().get()).as("partie pas terminee").isFalse();
    assertThat(p.aGagné()).as("pas gagnee").isFalse();
  }

  @Test
  void la_combinaison_secrete_a_4_pions_non_VIDE() throws Exception {
    Plateau p = surFxThread(Plateau::new);
    Combinaison secrete = p.getCombinaisonSecrete();
    assertThat(secrete.getNombrePions())
        .as("la combinaison secrete a 4 pions")
        .isEqualTo(4);
    for (int i = 0; i < 4; i++) {
      assertThat(secrete.getPion(i))
          .as("position %d : pion non-VIDE", i)
          .isNotEqualTo(PionJeu.VIDE);
    }
  }

  @Test
  void proposer_exactement_la_combinaison_secrete_fait_gagner() throws Exception {
    Plateau p = surFxThread(Plateau::new);
    Combinaison secrete = p.getCombinaisonSecrete();
    Combinaison proposition = new Combinaison(secrete.getNombrePions());
    for (int i = 0; i < secrete.getNombrePions(); i++) {
      proposition.setPion(i, secrete.getPion(i));
    }
    surFxThread(
        () -> {
          p.setCombinaisonCourante(proposition);
          p.validerRangéeCourante();
          return null;
        });
    assertThat(p.aGagné()).as("proposer la combinaison secrete fait gagner").isTrue();
    assertThat(p.estPartieTerminéeProperty().get())
        .as("la partie est terminee une fois gagnee")
        .isTrue();
  }

  @Test
  void valider_8_rangees_sans_trouver_fait_perdre() throws Exception {
    Plateau p = surFxThread(Plateau::new);
    Combinaison secrete = p.getCombinaisonSecrete();
    // Choisir un pion qui n'est PAS dans la combinaison secrete pour garantir le non-match.
    PionJeu pionAbsent = null;
    for (PionJeu candidat : PionJeu.values()) {
      if (candidat != PionJeu.VIDE && !secrete.contient(candidat)) {
        pionAbsent = candidat;
        break;
      }
    }
    final PionJeu pion = pionAbsent;
    if (pion == null) {
      return; // edge case impossible vu qu'il y a 8 pions et 4 dans la secrete
    }
    Combinaison mauvaise = new Combinaison(secrete.getNombrePions());
    for (int i = 0; i < secrete.getNombrePions(); i++) {
      mauvaise.setPion(i, pion);
    }
    surFxThread(
        () -> {
          for (int essai = 0; essai < 8; essai++) {
            p.setCombinaisonCourante(mauvaise);
            p.validerRangéeCourante();
          }
          return null;
        });
    assertThat(p.aPerdu()).as("apres 8 essais infructueux, la partie est perdue").isTrue();
    assertThat(p.estPartieTerminéeProperty().get()).as("partie terminee").isTrue();
  }

  @Test
  void valider_une_rangee_incremente_le_compteur_de_coups() throws Exception {
    Plateau p = surFxThread(Plateau::new);
    Combinaison c = new Combinaison(4);
    c.setPion(0, PionJeu.ROUGE);
    c.setPion(1, PionJeu.ROUGE);
    c.setPion(2, PionJeu.ROUGE);
    c.setPion(3, PionJeu.ROUGE);
    surFxThread(
        () -> {
          p.setCombinaisonCourante(c);
          p.validerRangéeCourante();
          return null;
        });
    assertThat(p.getNombreDeCoupsJoués())
        .as("le compteur de coups doit etre a 1 apres une validation")
        .isEqualTo(1);
  }
}
