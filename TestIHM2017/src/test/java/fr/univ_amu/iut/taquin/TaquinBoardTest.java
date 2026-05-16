package fr.univ_amu.iut.taquin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests fonctionnels du {@link TaquinBoard} (4x4) en mode JavaFX.
 *
 * <p>Inspires du pattern du bonus 10 du TP3 : {@code Platform.startup} une seule fois, scenarios
 * executes via {@code Platform.runLater} + {@code CountDownLatch}. Les clics sont simules via
 * {@code carreau.fire()}.
 */
class TaquinBoardTest {

  private static final int TAILLE = 4;

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
  void un_taquin_neuf_4x4_contient_16_carreaux() throws Exception {
    TaquinBoard b = surFxThread(() -> new TaquinBoard(TAILLE));
    assertThat(b.getCarreaux())
        .as("un taquin %dx%d doit contenir %d carreaux", TAILLE, TAILLE, TAILLE * TAILLE)
        .hasSize(TAILLE * TAILLE);
  }

  @Test
  void au_demarrage_les_carreaux_sont_numerotes_de_1_a_16_dans_l_ordre() throws Exception {
    TaquinBoard b = surFxThread(() -> new TaquinBoard(TAILLE));
    for (int i = 0; i < TAILLE * TAILLE; i++) {
      assertThat(b.getCarreau(i).getNumero())
          .as("le carreau a l'index %d doit avoir le numero %d", i, i + 1)
          .isEqualTo(i + 1);
      assertThat(b.getCarreau(i).estBienPlace())
          .as("au demarrage, le carreau %d est a sa place", i + 1)
          .isTrue();
    }
  }

  @Test
  void le_dernier_carreau_est_le_carreau_vide_dans_le_coin_bas_droit() throws Exception {
    TaquinBoard b = surFxThread(() -> new TaquinBoard(TAILLE));
    Carreau vide = b.getCarreauVide();
    assertThat(vide.getPosition().getX()).as("le carreau vide est en colonne 3").isEqualTo(TAILLE - 1);
    assertThat(vide.getPosition().getY()).as("le carreau vide est en ligne 3").isEqualTo(TAILLE - 1);
    assertThat(vide.getText()).as("le carreau vide n'a pas de texte affiche").isEmpty();
  }

  @Test
  void cliquer_sur_un_carreau_adjacent_au_vide_le_deplace() throws Exception {
    TaquinBoard b = surFxThread(() -> new TaquinBoard(TAILLE));
    // Au demarrage, le carreau (2,3) est juste a gauche du vide (3,3).
    Carreau aDeplacer =
        b.getCarreaux().stream()
            .filter(c -> c.getPosition().getX() == TAILLE - 2 && c.getPosition().getY() == TAILLE - 1)
            .findFirst()
            .orElseThrow();
    surFxThread(
        () -> {
          aDeplacer.fire();
          return null;
        });
    assertThat(aDeplacer.getPosition())
        .as("apres clic, le carreau se deplace en (3,3) (la position du vide)")
        .isEqualTo(new Position(TAILLE - 1, TAILLE - 1));
    assertThat(b.getCarreauVide().getPosition())
        .as("le vide a pris la place du carreau deplace en (2,3)")
        .isEqualTo(new Position(TAILLE - 2, TAILLE - 1));
    assertThat(b.getNombreDeMouvement())
        .as("le compteur de mouvements est a 1")
        .isEqualTo(1);
  }

  @Test
  void cliquer_sur_un_carreau_non_adjacent_au_vide_est_ignore() throws Exception {
    TaquinBoard b = surFxThread(() -> new TaquinBoard(TAILLE));
    // Le carreau (0,0) est non-adjacent au vide (3,3).
    Carreau nonAdjacent =
        b.getCarreaux().stream()
            .filter(c -> c.getPosition().getX() == 0 && c.getPosition().getY() == 0)
            .findFirst()
            .orElseThrow();
    surFxThread(
        () -> {
          nonAdjacent.fire();
          return null;
        });
    assertThat(nonAdjacent.getPosition())
        .as("un carreau non-adjacent au vide ne doit pas bouger")
        .isEqualTo(new Position(0, 0));
    assertThat(b.getNombreDeMouvement())
        .as("aucun mouvement n'a ete enregistre")
        .isEqualTo(0);
  }

  @Test
  void un_taquin_dans_l_ordre_est_une_partie_terminee_apres_verification() throws Exception {
    TaquinBoard b = surFxThread(() -> new TaquinBoard(TAILLE));
    // L'etat initial est deja "dans l'ordre" mais estPartieTerminee n'est mis a jour
    // qu'apres un coup. On joue un coup puis on annule (clic adjacent + retour).
    Carreau c23 =
        b.getCarreaux().stream()
            .filter(c -> c.getPosition().getX() == TAILLE - 2 && c.getPosition().getY() == TAILLE - 1)
            .findFirst()
            .orElseThrow();
    surFxThread(
        () -> {
          c23.fire(); // (2,3) <-> vide
          c23.fire(); // c23 se redeplace en (2,3), retour a l'ordre
          return null;
        });
    assertThat(b.estPartieTermineeProperty().get())
        .as("apres 2 deplacements qui se compensent, le taquin est dans l'ordre = partie terminee")
        .isTrue();
  }
}
