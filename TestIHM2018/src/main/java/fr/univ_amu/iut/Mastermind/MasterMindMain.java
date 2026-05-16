package fr.univ_amu.iut.Mastermind;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MasterMindMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("MasterMind");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/univ_amu/iut/Mastermind/MasterMindView.fxml"));
            BorderPane root = loader.load();
            MasterMindControleur controller = loader.getController();
            controller.setStageAndSetupListeners(primaryStage);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }
}
