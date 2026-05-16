package fr.univ_amu.iut.lightsout;

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
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class LightsOutControleur implements Initializable {
    @FXML
    private Plateau plateau;

    @FXML
    private StatusBar statusBar;


    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusBar.nouvellePartie();
        creerBindings();
    }

    private void creerBindings() {
        plateau.aGagnéProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                afficherDialogFinDePartie();
        });
        statusBar.nombreDeCoupsJouésProperty().bind(plateau.nombreDeCoupsJouésProperty());
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
        alert.setContentText("Êtes vous certain de vouloir quitter l'application ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    private void afficherDialogFinDePartie() {
        String messageFinDePartie = "Vous avez gagné en " + plateau.getNombreDeCoupsJoués() + " coups";
        Alert alert = new Alert(INFORMATION);
        alert.setTitle("Fin de partie");
        alert.setContentText(messageFinDePartie);
        alert.showAndWait();
    }
}
