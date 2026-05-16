package fr.univ_amu.iut;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WordleMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Wordle");
        primaryStage.setScene(new Scene(new WordleController().view));
        primaryStage.show();
    }
}
