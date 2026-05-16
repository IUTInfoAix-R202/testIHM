package fr.univ_amu.iut.lightsout;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static javafx.beans.binding.Bindings.when;

public class StatusBar extends BorderPane implements Initializable {
    @FXML
    private Label labelNombreDeCoupsJoués;

    @FXML
    private Label labelTemps;

    @FXML
    private Label labelpartieTerminee;

    private IntegerProperty nombreDeCoupsJoués;
    private BooleanProperty estPartieTerminee;

    private LocalTime time;
    private Timeline timer;
    private StringProperty clock;
    private DateTimeFormatter fmt;


    public StatusBar() {
        nombreDeCoupsJoués = new SimpleIntegerProperty();
        estPartieTerminee = new SimpleBooleanProperty();
        time = LocalTime.now();
        clock = new SimpleStringProperty("00:00:00");
        fmt = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/univ_amu/iut/lightsout/StatusBarView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        creerAnimation();
        creerBindings();
    }

    private void creerAnimation() {
        timer = new Timeline(new KeyFrame(Duration.ZERO, e -> clock.set(LocalTime.now().minusNanos(time.toNanoOfDay()).format(fmt))),
                new KeyFrame(Duration.seconds(1)));
        timer.setCycleCount(Animation.INDEFINITE);
    }

    private void creerBindings() {
        labelTemps.textProperty().bind(Bindings.concat("Durée : ", clock));
        labelNombreDeCoupsJoués.textProperty().bind(Bindings.concat("Nombres de coups : ", nombreDeCoupsJoués));
        labelpartieTerminee.textProperty().bind(when(estPartieTerminee).then("Partie terminée !").otherwise(""));
    }

    void nouvellePartie() {
        time = LocalTime.now();
        timer.playFromStart();
    }

    public IntegerProperty nombreDeCoupsJouésProperty() {
        return nombreDeCoupsJoués;
    }

    public BooleanProperty estPartieTermineeProperty() {
        return estPartieTerminee;
    }
}
