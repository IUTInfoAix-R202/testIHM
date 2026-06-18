package fr.univ_amu.iut;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * Smoke test TestFX : verifie que l'application Quantik charge sa vue FXML, lie le label de statut
 * au ViewModel et construit le plateau 4x4 et les deux reserves. Valide aussi la chaine headless
 * (glass.platform=Headless) et les acces natifs (argLine du pom).
 */
@ExtendWith(ApplicationExtension.class)
class QuantikAppTest {

  @Start
  void start(Stage stage) throws Exception {
    stage.setScene(null); // evite la fuite de Scene entre tests (TestFX reutilise le Stage)
    new QuantikMain().start(stage);
  }

  @Test
  void leStatutAfficheLeJoueurCourantAuDemarrage(FxRobot robot) {
    Label statut = robot.lookup("#statutLabel").queryAs(Label.class);
    assertThat(statut).as("un Label #statutLabel doit etre present").isNotNull();
    assertThat(statut.getText()).contains("BLANC");
  }

  @Test
  void lePlateauContientSeizeCases(FxRobot robot) {
    GridPane plateau = robot.lookup("#plateauGrid").queryAs(GridPane.class);
    assertThat(plateau.getChildren()).as("le plateau doit etre une grille 4x4").hasSize(16);
  }

  @Test
  void lesDeuxReservesSontPeuplees(FxRobot robot) {
    VBox poolBlanc = robot.lookup("#poolBlanc").queryAs(VBox.class);
    VBox poolNoir = robot.lookup("#poolNoir").queryAs(VBox.class);
    // 1 titre + 4 vignettes (une par forme) de chaque cote.
    assertThat(poolBlanc.getChildren()).hasSize(5);
    assertThat(poolNoir.getChildren()).hasSize(5);
  }
}
