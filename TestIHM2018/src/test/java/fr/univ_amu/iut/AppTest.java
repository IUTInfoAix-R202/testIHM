package fr.univ_amu.iut;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * Smoke test du lanceur du test IHM 2018 (Mastermind).
 *
 * <p>Les clics sont realises via {@code button.fire()} dans {@code robot.interact(...)} plutot que
 * {@code robot.clickOn(...)} pour eviter la dependance au robot OS (echoue sur Wayland et certains
 * environnements headless type xvfb).
 */
@ExtendWith(ApplicationExtension.class)
class AppTest {

  @Start
  void start(Stage stage) throws Exception {
    stage.setScene(null); // evite la fuite de Scene entre tests (TestFX reutilise le Stage)
    new App().start(stage);
  }

  @Test
  void le_bouton_click_affiche_le_texte_initial(FxRobot robot) {
    Button bouton = robot.lookup("#buttonClick").queryAs(Button.class);
    assertThat(bouton).as("le bouton #buttonClick doit etre present dans la scene").isNotNull();
    assertThat(bouton.getText())
        .as("le bouton doit afficher \"Click !\" au demarrage")
        .isEqualTo("Click !");
  }

  @Test
  void le_compteur_passe_a_1_apres_un_clic(FxRobot robot) {
    Button bouton = robot.lookup("#buttonClick").queryAs(Button.class);
    robot.interact(bouton::fire);
    assertThat(bouton.getText())
        .as("le bouton doit afficher \"1\" apres un fire()")
        .isEqualTo("1");
  }

  @Test
  void le_compteur_passe_a_2_apres_deux_clics(FxRobot robot) {
    Button bouton = robot.lookup("#buttonClick").queryAs(Button.class);
    robot.interact(
        () -> {
          bouton.fire();
          bouton.fire();
        });
    assertThat(bouton.getText())
        .as("le bouton doit afficher \"2\" apres deux fire()")
        .isEqualTo("2");
  }
}
