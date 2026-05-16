package fr.univ_amu.iut.Mastermind;

import javafx.scene.image.Image;


public enum PionScore {
    VIDE("assets/pasdemarque.png"),
    BIENPLACE("assets/bienplace.png"),
    MALPLACE("assets/malplace.png");

    private Image image;

    PionScore(String nomFichier) {
        this.image = new Image(PionScore.class.getResourceAsStream("/" + nomFichier));
    }

    public Image getImage() {
        return image;
    }
}
