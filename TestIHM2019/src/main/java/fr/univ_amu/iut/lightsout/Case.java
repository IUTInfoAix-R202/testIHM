package fr.univ_amu.iut.lightsout;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;


public class Case extends Button {

    private static final int TAILLE_CASE = 75;
    private Position position;
    private BooleanProperty estAllumé;

    public Case(int x, int y) {
        position = new Position(x, y);

        estAllumé = new SimpleBooleanProperty();
        allumer();

        final int tailleCase = TAILLE_CASE;
        setMinSize(tailleCase, tailleCase);
        setPrefSize(tailleCase, tailleCase);
    }

    public Position getPosition() {
        return position;
    }

    public boolean estAllumé() {
        return estAllumé.get();
    }

    public BooleanProperty estAlluméProperty() {
        return estAllumé;
    }

    public void allumer() {
        estAllumé.set(true);
        setStyle("-fx-background-color: #000000, " +
                "linear-gradient(#7ffc1a, #2f7b1f), " +
                "linear-gradient(#42fa17, #267e15), " +
                "linear-gradient(#39fc1b, #227728);\n" +
                "-fx-background-insets: 0,1,2,3;\n" +
                "-fx-background-radius: 3,2,2,2;\n" +
                "-fx-padding: 12 30 12 30;\n" +
                "-fx-text-fill: white;\n");
    }

    public void eteindre() {
        estAllumé.set(false);
        setStyle("-fx-background-color: black; -fx-border-color: grey");

    }

    public void permuter() {
        if (estAllumé.get())
            eteindre();
        else
            allumer();
    }
}
