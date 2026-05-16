package fr.univ_amu.iut.lightsout;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LightsOutMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lights Out");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/univ_amu/iut/lightsout/LightsOutView.fxml"));
            BorderPane root = loader.load();
            LightsOutControleur controller = loader.getController();
            controller.setStageAndSetupListeners(primaryStage);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }
}
