package fr.univ_amu.iut.Mastermind;


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

public class MasterMindControleur implements Initializable {
    static final int NB_RANGEES = 8;
    static final int NB_PIONS_PAR_RANGEES = 4;

    @FXML
    private Plateau plateau;

    @FXML
    private StatusBar statusBar;


    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        creerBindings();
    }

    private void creerBindings() {
        plateau.estPartieTerminéeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                afficherDialogFinDePartie();
        });
    }


    void setStageAndSetupListeners(Stage stage) {
        stage.setOnCloseRequest(event -> {
            this.actionMenuJeuQuitter();
            event.consume();
        });
    }

    @FXML
    void actionBoutonEffacer() {
        plateau.viderRangéeCourante();
    }

    @FXML
    void actionBoutonValider() {
        if (plateau.combinaisonCourante().contient(PionJeu.VIDE)) {
            return;
        }
        plateau.validerRangéeCourante();
    }

    @FXML
    void actionMenuJeuNouveau() {
        plateau.nouvellePartie();
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
        String messageFinDePartie;
        if (plateau.aGagné())
            messageFinDePartie = "Vous avez trouvez la bonne combinaison en " + plateau.getNombreDeCoupsJoués() + " coups";
        else
            messageFinDePartie = "Vous avez perdu !";

        Alert alert = new Alert(INFORMATION);
        alert.setTitle("Fin de partie");
        alert.setContentText(messageFinDePartie);
        alert.showAndWait();
    }
}
