package fr.univ_amu.iut.tictactoe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

public class TicTacToeControleur implements Initializable {
    @FXML
    private Plateau plateau;

    @FXML
    private StatusBar statusBar;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        plateau.nouvellePartie();
        statusBar.nouvellePartie();
        creerBindings();
    }

    private void creerBindings() {
        statusBar.nombreDeCoupsJouesProperty().bind(plateau.nombreDeCoupsJouesProperty());
        statusBar.estPartieTermineeProperty().bind(plateau.estPartieTermineeProperty());
        statusBar.joueurCourantProperty().bind(plateau.joueurCourantProperty());
        statusBar.estMatchNulProperty().bind(plateau.estMatchNulProperty());
        statusBar.aGagneProperty().bind(plateau.aGagneProperty());
    }

    void setStageAndSetupListeners(Stage stage) {
        stage.setOnCloseRequest(event -> {
            this.actionMenuJeuQuitter();
            event.consume();
        });
    }

    @FXML
    void actionMenuJeuNouveau() {
        plateau.nouvellePartie();
        statusBar.nouvellePartie();
    }

    @FXML
    void actionMenuJeuQuitter() {
        Alert alert = new Alert(CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Voulez-vous vraiment quitter l'application ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
}
