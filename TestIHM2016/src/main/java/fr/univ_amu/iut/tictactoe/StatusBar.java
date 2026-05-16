package fr.univ_amu.iut.tictactoe;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static javafx.beans.binding.Bindings.*;

public class StatusBar extends BorderPane {

    private Label labelNombreDeCoupsJoues = new Label();
    private Label labelTemps = new Label();
    private Label labelInfo = new Label();

    private IntegerProperty nombreDeCoupsJoues;
    private BooleanProperty estPartieTerminee;
    private BooleanProperty estMatchNul;
    private BooleanProperty aGagne;

    private ObjectProperty<Joueur> joueurCourant;

    private LocalTime time;
    private Timeline timer;
    private StringProperty clock;
    private DateTimeFormatter fmt;

    public StatusBar() {
        nombreDeCoupsJoues = new SimpleIntegerProperty();
        estPartieTerminee = new SimpleBooleanProperty();
        joueurCourant = new SimpleObjectProperty<>(Joueur.VIDE);
        estMatchNul = new SimpleBooleanProperty(false);
        aGagne = new SimpleBooleanProperty(false);

        time = LocalTime.now();
        clock = new SimpleStringProperty("00:00:00");
        fmt = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(ZoneId.systemDefault());

        this.setLeft(labelNombreDeCoupsJoues);
        this.setRight(labelTemps);
        this.setBottom(labelInfo);

        BorderPane.setAlignment(labelInfo, Pos.CENTER);

        creerAnimation();
        creerBindings();
    }

    private void creerAnimation() {
        timer = new Timeline(new KeyFrame(Duration.ZERO, e -> clock.set(LocalTime.now().minusNanos(time.toNanoOfDay()).format(fmt))),
                new KeyFrame(Duration.seconds(1)));
        timer.setCycleCount(Animation.INDEFINITE);
    }

    private void creerBindings() {
        labelTemps.textProperty().bind(concat("Durée : ", clock));
        labelNombreDeCoupsJoues.textProperty().bind(concat("Nombre de coups : ", nombreDeCoupsJoues));

        StringBinding messageJoueurCourant = when(joueurCourant.isNotEqualTo(Joueur.VIDE))
                .then(format("Joueur %s, à vous !", joueurCourant.asString()))
                .otherwise("");

        StringBinding messageMatchNul = when(estMatchNul)
                .then("Terminé : Match nul !").otherwise(messageJoueurCourant);

        StringBinding messageInfoGagne = when(aGagne)
                .then(format("Terminé : joueur %s a gagné", joueurCourant.asString()))
                .otherwise(messageMatchNul);

        labelInfo.textProperty().bind(messageInfoGagne);

        joueurCourant.addListener((observable, oldValue, newValue) -> labelInfo.setTextFill(newValue.getCouleur()));

        estPartieTerminee.addListener((observable, oldValue, newValue) -> {
            if (newValue)
                labelInfo.setTextFill(Joueur.VIDE.getCouleur());
        });
    }

  public void nouvellePartie() {
        time = LocalTime.now();
        timer.playFromStart();
    }

  public IntegerProperty nombreDeCoupsJouesProperty() {
        return nombreDeCoupsJoues;
    }

  public BooleanProperty estPartieTermineeProperty() {
        return estPartieTerminee;
    }

  public ObjectProperty<Joueur> joueurCourantProperty() {
        return joueurCourant;
    }

  public BooleanProperty estMatchNulProperty() {
        return estMatchNul;
    }

  public BooleanProperty aGagneProperty() {
        return aGagne;
    }
}
