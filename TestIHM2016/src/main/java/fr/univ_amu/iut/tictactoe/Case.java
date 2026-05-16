package fr.univ_amu.iut.tictactoe;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


class Case extends Button {
    private static final int TAILLE_CASE = 110;
    private ObjectProperty<Joueur> possesseurProperty;

    public Case() {
        possesseurProperty = new SimpleObjectProperty<>(Joueur.VIDE);
        setMinSize(TAILLE_CASE, TAILLE_CASE);
        setPrefSize(TAILLE_CASE, TAILLE_CASE);
        vider();
    }

    public void prendre(Joueur joueur) {
        possesseurProperty.set(joueur);
        setGraphic(new ImageView(joueur.getImage()));
    }

    public ObjectProperty<Joueur> possesseurProperty() {
        return possesseurProperty;
    }


    public void vider() {
        prendre(Joueur.VIDE);
    }

    public boolean estVide() {
        return possesseurProperty.get().equals(Joueur.VIDE);
    }
}
