package fr.univ_amu.iut.exercice1;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;

public class LigneExercice extends HBox {

    @FXML
    private Label enonce;
    @FXML
    private TextField reponse;

    private BooleanProperty correct;

    Exercice exercice;

    public LigneExercice() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ligneExercice.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        exercice = new Exercice();
        enonce.setText(exercice.getEnonce());
        correct = new SimpleBooleanProperty();
        IntegerProperty intReponse = new SimpleIntegerProperty();
        reponse.textProperty().bindBidirectional(intReponse, new NumberStringConverter());
        correct.bind(Bindings.equal(intReponse, exercice.getSolution()));
    }

    public boolean isCorrect() {
        return correct.get();
    }

}