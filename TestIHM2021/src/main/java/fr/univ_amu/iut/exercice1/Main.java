package fr.univ_amu.iut.exercice1;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private BorderPane contenu = new BorderPane();
    private VBox panneauExercices;
    private Button validationResultats;
    private ComboBox nbExercices;
    private int nombreReponsesCorrectes;

    @Override
    public void start(Stage primaryStage) {
        contenu = new BorderPane();

        HBox infoNbExercices = new HBox();
        Label labelNbExercices = new Label("Combien d'exercices ? ");
        nbExercices = new ComboBox();
        nbExercices.getItems().addAll(6, 9, 12, 15);
        infoNbExercices.getChildren().addAll(labelNbExercices, nbExercices);
        infoNbExercices.setSpacing(20);
        infoNbExercices.setAlignment(Pos.CENTER);
        contenu.setTop(infoNbExercices);
        contenu.setPadding(new Insets(10));

        validationResultats = new Button("Voir le résultat");

        panneauExercices = new VBox();

        ajoutListenerPourAfficherLesExercices();
        definirActionBoutonValidationResultat();


        primaryStage.setScene(new Scene(contenu));
        primaryStage.show();
    }

    private void ajoutListenerPourAfficherLesExercices() {
        nbExercices.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                panneauExercices.getChildren().clear();

                for (int i = 0; i < /*(int) nbExercices.getValue()*/ (int) newValue -1; i++) {
                    LigneExercice ligneExercice = new LigneExercice();
                    panneauExercices.getChildren().add(ligneExercice);
                }

                contenu.setCenter(panneauExercices);
                contenu.getScene().getWindow().sizeToScene();

                contenu.setBottom(validationResultats);
                BorderPane.setAlignment(validationResultats, Pos.CENTER);
            }
        });
    }

    private void definirActionBoutonValidationResultat() {
        nombreReponsesCorrectes = 0;
        validationResultats.setOnAction(event -> {
            for (Node e : panneauExercices.getChildren()) {
                LigneExercice le = (LigneExercice) e;
                if (le.isCorrect()) {
                    nombreReponsesCorrectes++;
                }
            }

            afficherMessageResultats();

        });
    }

    private void afficherMessageResultats() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Félicitations!");
        alert.setHeaderText(null);
        String message = "Vous avez résolu " + nombreReponsesCorrectes + " exercice" + (nombreReponsesCorrectes>1?"s !":" !");
        alert.setContentText(message);
        alert.showAndWait();
        nombreReponsesCorrectes = 0;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
