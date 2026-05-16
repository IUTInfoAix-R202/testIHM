package fr.univ_amu.iut.Mastermind;

import javafx.scene.image.Image;


public enum PionScore {
    VIDE("assets/pasdemarque.png"),
    BIENPLACE("assets/bienplace.png"),
    MALPLACE("assets/malplace.png");

    private Image image;

    PionScore(String nomFichier) {
        ClassLoader classLoader = getClass().getClassLoader();
        this.image = new Image(classLoader.getResource(nomFichier).toExternalForm());
    }

    public Image getImage() {
        return image;
    }
}
