package fr.univ_amu.iut.lightsout;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Tests fonctionnels du {@link Plateau} Lights Out 5x5 (JavaFX). */
class PlateauTest {

  private static final int TAILLE = 5;

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
  void un_plateau_neuf_a_toutes_ses_cases_allumees() throws Exception {
    Plateau p = surFxThread(() -> new Plateau(TAILLE));
    for (int i = 0; i < TAILLE; i++) {
      for (int j = 0; j < TAILLE; j++) {
        assertThat(p.getCase(i, j).estAllumé())
            .as("la case (%d,%d) doit etre allumee au demarrage", i, j)
            .isTrue();
      }
    }
  }

  @Test
  void au_demarrage_aucun_coup_n_est_joue_et_la_partie_n_est_pas_gagnee() throws Exception {
    Plateau p = surFxThread(() -> new Plateau(TAILLE));
    assertThat(p.getNombreDeCoupsJoués()).as("compteur de coups a 0").isEqualTo(0);
    assertThat(p.aGagnéProperty().get())
        .as("partie pas gagnee (toutes les cases sont allumees)")
        .isFalse();
  }

  @Test
  void cliquer_sur_une_case_centrale_eteint_la_case_et_ses_quatre_voisines() throws Exception {
    Plateau p = surFxThread(() -> new Plateau(TAILLE));
    surFxThread(
        () -> {
          p.getCase(2, 2).fire();
          return null;
        });
    assertThat(p.getCase(2, 2).estAllumé()).as("(2,2) eteinte").isFalse();
    assertThat(p.getCase(1, 2).estAllumé()).as("voisin gauche (1,2) eteint").isFalse();
    assertThat(p.getCase(3, 2).estAllumé()).as("voisin droit (3,2) eteint").isFalse();
    assertThat(p.getCase(2, 1).estAllumé()).as("voisin haut (2,1) eteint").isFalse();
    assertThat(p.getCase(2, 3).estAllumé()).as("voisin bas (2,3) eteint").isFalse();
    assertThat(p.getNombreDeCoupsJoués())
        .as("le compteur de coups doit etre a 1 apres un clic")
        .isEqualTo(1);
  }

  @Test
  void cliquer_sur_un_coin_eteint_seulement_la_case_et_ses_deux_voisines() throws Exception {
    Plateau p = surFxThread(() -> new Plateau(TAILLE));
    surFxThread(
        () -> {
          p.getCase(0, 0).fire(); // coin haut-gauche
          return null;
        });
    assertThat(p.getCase(0, 0).estAllumé()).as("(0,0) coin eteint").isFalse();
    assertThat(p.getCase(1, 0).estAllumé()).as("voisin (1,0) eteint").isFalse();
    assertThat(p.getCase(0, 1).estAllumé()).as("voisin (0,1) eteint").isFalse();
    // Les autres restent allumees - on en verifie quelques-unes.
    assertThat(p.getCase(2, 0).estAllumé()).as("(2,0) toujours allumee").isTrue();
    assertThat(p.getCase(0, 2).estAllumé()).as("(0,2) toujours allumee").isTrue();
  }

  @Test
  void cliquer_deux_fois_la_meme_case_centrale_remet_les_cinq_cases_dans_l_etat_initial()
      throws Exception {
    Plateau p = surFxThread(() -> new Plateau(TAILLE));
    surFxThread(
        () -> {
          p.getCase(2, 2).fire();
          p.getCase(2, 2).fire();
          return null;
        });
    assertThat(p.getCase(2, 2).estAllumé()).as("(2,2) rallumee").isTrue();
    assertThat(p.getCase(1, 2).estAllumé()).as("(1,2) rallumee").isTrue();
    assertThat(p.getCase(3, 2).estAllumé()).as("(3,2) rallumee").isTrue();
    assertThat(p.getCase(2, 1).estAllumé()).as("(2,1) rallumee").isTrue();
    assertThat(p.getCase(2, 3).estAllumé()).as("(2,3) rallumee").isTrue();
    assertThat(p.getNombreDeCoupsJoués())
        .as("compteur a 2 (2 clics meme si etat identique)")
        .isEqualTo(2);
    assertThat(p.aGagnéProperty().get())
        .as("partie pas gagnee (toutes rallumees)")
        .isFalse();
  }

  @Test
  void nouvelle_partie_rallume_toutes_les_cases_et_remet_le_compteur_a_zero() throws Exception {
    Plateau p = surFxThread(() -> new Plateau(TAILLE));
    surFxThread(
        () -> {
          p.getCase(2, 2).fire();
          p.getCase(0, 0).fire();
          p.nouvellePartie();
          return null;
        });
    for (int i = 0; i < TAILLE; i++) {
      for (int j = 0; j < TAILLE; j++) {
        assertThat(p.getCase(i, j).estAllumé())
            .as("apres nouvelle partie, (%d,%d) doit etre rallumee", i, j)
            .isTrue();
      }
    }
    assertThat(p.getNombreDeCoupsJoués())
        .as("compteur de coups remis a 0")
        .isEqualTo(0);
  }
}
