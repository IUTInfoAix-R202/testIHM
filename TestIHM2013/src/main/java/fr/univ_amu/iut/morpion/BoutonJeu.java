package fr.univ_amu.iut.morpion;

import javax.swing.JButton;

public class BoutonJeu extends JButton {

    private int ligne;
    private int colonne;

    public BoutonJeu(int ligne, int colonne) {
        super();
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }
}
