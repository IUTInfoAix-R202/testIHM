package fr.univ_amu.iut.ihm;

import static org.assertj.core.api.Assertions.assertThat;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 * Smoke + tests fonctionnels du {@link TraceurDeFonction} (implementation reconstituee a partir
 * du sujet 2020).
 *
 * <p>Les clics sont realises via {@code button.fire()} dans {@code robot.interact(...)} pour
 * eviter la dependance au robot OS (Wayland / xvfb peu fiables).
 */
@ExtendWith(ApplicationExtension.class)
class TraceurDeFonctionTest {

  @Start
  void start(Stage stage) {
    stage.setScene(null);
    new TraceurDeFonction().start(stage);
  }

  @Test
  void le_textfield_d_expression_contient_la_valeur_par_defaut_de_l_enonce(FxRobot robot) {
    TextField texteAAnalyser = robot.lookup("#texteAAnalyser").queryAs(TextField.class);
    assertThat(texteAAnalyser.getText())
        .as("le textfield doit etre prerempli avec 'exp(-x * 0.2) * sin(x)' (cf. enonce)")
        .isEqualTo("exp(-x * 0.2) * sin(x)");
  }

  @Test
  void cliquer_sur_Analyser_affiche_le_resultat_avec_le_prefixe_attendu(FxRobot robot) {
    Button demandeAnalyser = robot.lookup("#demandeAnalyser").queryAs(Button.class);
    Label resultatAnalyse = robot.lookup("#resultatAnalyse").queryAs(Label.class);
    robot.interact(demandeAnalyser::fire);
    assertThat(resultatAnalyse.getText())
        .as("le label doit afficher 'Expression analysée : f(x) = ' suivi du toString()")
        .startsWith("Expression analysée : f(x) = ");
  }

  @Test
  void analyser_une_expression_invalide_affiche_un_message_d_erreur(FxRobot robot) {
    TextField texteAAnalyser = robot.lookup("#texteAAnalyser").queryAs(TextField.class);
    Button demandeAnalyser = robot.lookup("#demandeAnalyser").queryAs(Button.class);
    Label resultatAnalyse = robot.lookup("#resultatAnalyse").queryAs(Label.class);
    robot.interact(
        () -> {
          texteAAnalyser.setText("si(x)");
          demandeAnalyser.fire();
        });
    assertThat(resultatAnalyse.getText())
        .as("'si' est un identificateur non reconnu, le message doit le signaler")
        .contains("si")
        .contains("non reconnu");
  }

  @Test
  void cliquer_sur_Tracer_dessine_des_segments_dans_la_zone_de_trace(FxRobot robot) {
    Button demandeTracer = robot.lookup("#demandeTracer").queryAs(Button.class);
    Group segmentsATracer = robot.lookup("#segmentsATracer").queryAs(Group.class);
    robot.interact(demandeTracer::fire);
    assertThat(segmentsATracer.getChildren())
        .as("apres Tracer, 1000 segments doivent etre dessines (1001 points - 1)")
        .hasSize(1000);
  }

  @Test
  void cliquer_sur_Effacer_apres_Tracer_vide_la_zone(FxRobot robot) {
    Button demandeTracer = robot.lookup("#demandeTracer").queryAs(Button.class);
    Button demandeEffacer = robot.lookup("#demandeEffacer").queryAs(Button.class);
    Group segmentsATracer = robot.lookup("#segmentsATracer").queryAs(Group.class);
    Group quadrillage = robot.lookup("#quadrillage").queryAs(Group.class);
    robot.interact(
        () -> {
          demandeTracer.fire();
          demandeEffacer.fire();
        });
    assertThat(segmentsATracer.getChildren())
        .as("apres Effacer, les segments de la courbe doivent etre supprimes")
        .isEmpty();
    assertThat(quadrillage.getChildren())
        .as("apres Effacer, le quadrillage doit aussi etre supprime")
        .isEmpty();
  }

  @Test
  void la_zone_de_trace_existe_dans_la_scene(FxRobot robot) {
    Pane zone = robot.lookup("#zoneTraceCourbe").queryAs(Pane.class);
    assertThat(zone).as("la zone de trace doit etre identifiee par #zoneTraceCourbe").isNotNull();
  }
}
