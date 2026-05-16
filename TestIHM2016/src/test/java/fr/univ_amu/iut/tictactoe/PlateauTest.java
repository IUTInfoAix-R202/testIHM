package fr.univ_amu.iut.tictactoe;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests fonctionnels du modele / vue {@link Plateau} (JavaFX).
 *
 * <p>Inspires du pattern du bonus 10 du TP3 : on demarre la plateforme JavaFX une seule fois,
 * puis on execute chaque scenario via {@link Platform#runLater(Runnable)} avec un latch
 * d'attente. Les clics sont simules via {@code case.fire()} - equivalent JavaFX du {@code
 * doClick()} Swing - pour eviter la dependance au robot OS.
 */
class PlateauTest {

  @BeforeAll
  static void demarrerJavaFx() {
    try {
      Platform.startup(() -> {});
    } catch (IllegalStateException dejaDemarree) {
      // OK : un autre test a deja demarre la plateforme dans le meme process.
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
  void un_plateau_neuf_a_toutes_les_cases_vides_et_aucun_coup_joue() throws Exception {
    Plateau p = surFxThread(Plateau::new);
    for (int i = 0; i < p.getTaille(); i++) {
      for (int j = 0; j < p.getTaille(); j++) {
        assertThat(p.getCase(i, j).estVide())
            .as("la case (%d,%d) doit etre vide au demarrage", i, j)
            .isTrue();
      }
    }
    assertThat(p.nombreDeCoupsJouesProperty().get())
        .as("aucun coup n'a ete joue au demarrage")
        .isEqualTo(0);
    assertThat(p.estPartieTermineeProperty().get())
        .as("la partie n'est pas terminee au demarrage")
        .isFalse();
    assertThat(p.joueurCourantProperty().get())
        .as("CROIX commence")
        .isEqualTo(Joueur.CROIX);
  }

  @Test
  void cliquer_sur_une_case_pose_le_pion_du_joueur_courant_et_passe_la_main() throws Exception {
    Plateau p = surFxThread(Plateau::new);
    surFxThread(
        () -> {
          p.getCase(1, 1).fire();
          return null;
        });
    assertThat(p.getCase(1, 1).possesseurProperty().get())
        .as("la case (1,1) doit appartenir a CROIX apres le 1er clic")
        .isEqualTo(Joueur.CROIX);
    assertThat(p.nombreDeCoupsJouesProperty().get())
        .as("le compteur de coups doit etre incremente a 1")
        .isEqualTo(1);
    assertThat(p.joueurCourantProperty().get())
        .as("apres CROIX, la main passe a ROND")
        .isEqualTo(Joueur.ROND);
  }

  @Test
  void cliquer_sur_une_case_deja_prise_est_ignore() throws Exception {
    Plateau p = surFxThread(Plateau::new);
    surFxThread(
        () -> {
          p.getCase(0, 0).fire();
          p.getCase(0, 0).fire(); // ROND essaie de jouer sur la case deja prise par CROIX
          return null;
        });
    assertThat(p.getCase(0, 0).possesseurProperty().get())
        .as("la case (0,0) reste a CROIX")
        .isEqualTo(Joueur.CROIX);
    assertThat(p.nombreDeCoupsJouesProperty().get())
        .as("le 2eme clic est ignore : compteur a 1")
        .isEqualTo(1);
    assertThat(p.joueurCourantProperty().get())
        .as("ROND a la main, en attente de son coup")
        .isEqualTo(Joueur.ROND);
  }

  @Test
  void trois_pions_alignes_sur_une_ligne_font_gagner() throws Exception {
    Plateau p = surFxThread(Plateau::new);
    surFxThread(
        () -> {
          p.getCase(0, 0).fire(); // CROIX
          p.getCase(1, 0).fire(); // ROND
          p.getCase(0, 1).fire(); // CROIX
          p.getCase(1, 1).fire(); // ROND
          p.getCase(0, 2).fire(); // CROIX gagne sur ligne 0
          return null;
        });
    assertThat(p.aGagneProperty().get())
        .as("CROIX a aligne (0,0)(0,1)(0,2) - aGagne doit etre true")
        .isTrue();
    assertThat(p.estPartieTermineeProperty().get())
        .as("la partie est terminee une fois gagnee")
        .isTrue();
  }

  @Test
  void trois_pions_alignes_sur_une_diagonale_font_gagner() throws Exception {
    Plateau p = surFxThread(Plateau::new);
    surFxThread(
        () -> {
          p.getCase(0, 0).fire(); // CROIX
          p.getCase(0, 1).fire(); // ROND
          p.getCase(1, 1).fire(); // CROIX
          p.getCase(0, 2).fire(); // ROND
          p.getCase(2, 2).fire(); // CROIX gagne diagonale (0,0)-(1,1)-(2,2)
          return null;
        });
    assertThat(p.aGagneProperty().get())
        .as("CROIX a aligne la diagonale - aGagne doit etre true")
        .isTrue();
  }

  @Test
  void neuf_cases_remplies_sans_alignement_est_un_match_nul() throws Exception {
    Plateau p = surFxThread(Plateau::new);
    // Sequence de 9 coups menant a un match nul (configuration X-O-X / X-O-O / O-X-X).
    surFxThread(
        () -> {
          p.getCase(0, 0).fire(); // CROIX
          p.getCase(0, 1).fire(); // ROND
          p.getCase(0, 2).fire(); // CROIX
          p.getCase(1, 1).fire(); // ROND
          p.getCase(1, 0).fire(); // CROIX
          p.getCase(1, 2).fire(); // ROND
          p.getCase(2, 1).fire(); // CROIX
          p.getCase(2, 0).fire(); // ROND
          p.getCase(2, 2).fire(); // CROIX
          return null;
        });
    assertThat(p.nombreDeCoupsJouesProperty().get())
        .as("9 coups joues")
        .isEqualTo(9);
    assertThat(p.estMatchNulProperty().get())
        .as("aucun alignement => match nul")
        .isTrue();
    assertThat(p.aGagneProperty().get()).as("pas de gagnant").isFalse();
    assertThat(p.estPartieTermineeProperty().get()).as("partie terminee").isTrue();
  }

  @Test
  void nouvelle_partie_reinitialise_le_plateau_et_le_compteur() throws Exception {
    Plateau p = surFxThread(Plateau::new);
    surFxThread(
        () -> {
          p.getCase(0, 0).fire();
          p.getCase(1, 1).fire();
          p.nouvellePartie();
          return null;
        });
    assertThat(p.getCase(0, 0).estVide()).as("(0,0) videe apres nouvelle partie").isTrue();
    assertThat(p.getCase(1, 1).estVide()).as("(1,1) videe").isTrue();
    assertThat(p.nombreDeCoupsJouesProperty().get())
        .as("compteur de coups remis a 0")
        .isEqualTo(0);
    assertThat(p.joueurCourantProperty().get())
        .as("CROIX rejoue en premier")
        .isEqualTo(Joueur.CROIX);
  }
}
