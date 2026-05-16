package fr.univ_amu.iut.Mastermind;

import javafx.scene.image.Image;


public enum PionJeu {

    VIDE("assets/vide.png"),
    ROUGE("assets/rouge.png"),
    BLEU("assets/bleu.png"),
    FUCHSIA("assets/fuchsia.png"),
    JAUNE("assets/jaune.png"),
    MARRON("assets/marron.png"),
    ORANGE("assets/orange.png"),
    VERT("assets/vert.png"),
    VIOLET("assets/violet.png");

    private Image image;

    PionJeu(String nomFichier) {
        ClassLoader classLoader = getClass().getClassLoader();
        this.image = new Image(classLoader.getResource(nomFichier).toExternalForm());
    }

    public Image getImage() {
        return image;
    }

    public PionJeu suivant() {
        return PionJeu.values()[(ordinal() + 1) % PionJeu.values().length];
    }
}
