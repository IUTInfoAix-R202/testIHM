package fr.univ_amu.iut;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Programme principal de l'application Quantik (sujet R2.02).
 *
 * <p>Charge la vue decrite par {@code quantikView.fxml}, l'habille avec la feuille de style {@code
 * quantik.css} et affiche la fenetre.
 */
public class QuantikMain extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/quantikView.fxml"));
    Parent racine = loader.load();
    Scene scene = new Scene(racine, 720, 560);
    scene.getStylesheets().add(getClass().getResource("/css/quantik.css").toExternalForm());
    primaryStage.setTitle("Quantik");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
