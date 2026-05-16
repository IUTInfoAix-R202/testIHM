package fr.univ_amu.iut.othello;

import javax.swing.*;

public enum Joueur {
    NOIR("/noir.png"),
    BLANC("/blanc.png"),
    PERSONNE("/vide.png");

    private final Icon icon;
    private int score;

    private Joueur(String resourcePath) {
        icon = new ImageIcon(Joueur.class.getResource(resourcePath));
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void decrementerScore() {
        score--;
    }

    public void incrementerScore() {
        score++;
    }

    public Icon getIcon() {
        return icon;
    }

    public Joueur suivant() {
        if (this == BLANC)
            return NOIR;
        if (this == NOIR)
            return BLANC;
        return PERSONNE;
    }

    private void initialiserScore() {
        score = 0;
    }

    public static void initialiserScores() {
        BLANC.initialiserScore();
        NOIR.initialiserScore();
    }
}
