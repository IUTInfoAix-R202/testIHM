package fr.univ_amu.iut.tictactoe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TicTacToeMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic-Tac-Toe");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/univ_amu/iut/tictactoe/TicTacToeView.fxml"));
            BorderPane root = loader.load();
            TicTacToeControleur controller = loader.getController();
            controller.setStageAndSetupListeners(primaryStage);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }
}
