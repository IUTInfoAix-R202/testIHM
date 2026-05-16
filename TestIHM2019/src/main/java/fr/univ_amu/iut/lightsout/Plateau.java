package fr.univ_amu.iut.lightsout;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;

public class Plateau extends GridPane {
    private static final Point2D[] directions = {
            new Point2D(1, 0),
            new Point2D(0, 1),
            new Point2D(0, -1),
            new Point2D(-1, 0)
    };

    private int taille;
    private IntegerProperty nombreDeCoupsJoués;
    private IntegerProperty nombreDeCasesEteintes;
    private BooleanProperty aGagné;
    private Case[][] cases;
    private EventHandler<ActionEvent> caseListener = actionEvent -> {
        Case caseChoisi = (Case) actionEvent.getSource();
        nombreDeCoupsJoués.set(nombreDeCoupsJoués.get() + 1);
        caseChoisi.permuter();
        permuterVoisin(caseChoisi);
    };

    public Plateau() {
        this(5);
    }

    public Plateau(int taille) {
        this.taille = taille;
        this.nombreDeCoupsJoués = new SimpleIntegerProperty(0);
        this.nombreDeCasesEteintes = new SimpleIntegerProperty(0);
        this.aGagné = new SimpleBooleanProperty(false);

        setHgap(3);
        setVgap(3);

        remplir();
        creerBindings();
        nouvellePartie();
    }

    private void remplir() {
        cases = new Case[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cases[i][j] = new Case(i, j);
                cases[i][j].setOnAction(caseListener);
                add(cases[i][j], i, j);
            }
        }
    }

    private void toutAllumer() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cases[i][j].allumer();
            }
        }
    }

    public void nouvellePartie() {
        nombreDeCoupsJoués.set(0);
        toutAllumer();
    }

    private void creerBindings() {
        aGagné.bind(nombreDeCasesEteintes.isEqualTo(taille * taille));
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cases[i][j].estAlluméProperty().addListener((observableValue, oldValue, newValue) -> {
                    if (newValue)
                        nombreDeCasesEteintes.set(nombreDeCasesEteintes.get() - 1);
                    else
                        nombreDeCasesEteintes.set(nombreDeCasesEteintes.get() + 1);
                });
            }
        }
    }

    private void permuterVoisin(Case caseChoisi) {
        for (Point2D direction : directions) {
            int indiceLigne = (int) (caseChoisi.getPosition().getY() + direction.getY());
            int indiceColonne = (int) (caseChoisi.getPosition().getX() + direction.getX());
            if (estIndicesValides(indiceLigne, indiceColonne))
                cases[indiceColonne][indiceLigne].permuter();
        }
    }

    private boolean estIndicesValides(int indiceLigne, int indiceColonne) {
        return estIndiceValide(indiceColonne) && estIndiceValide(indiceLigne);
    }

    private boolean estIndiceValide(int indice) {
        return indice < taille && indice >= 0;
    }

    public int getNombreDeCoupsJoués() {
        return nombreDeCoupsJoués.get();
    }

    public IntegerProperty nombreDeCoupsJouésProperty() {
        return nombreDeCoupsJoués;
    }

    public BooleanProperty aGagnéProperty() {
        return aGagné;
    }

    public IntegerProperty nombreDeCasesEteintesProperty() {
        return nombreDeCasesEteintes;
    }
}
