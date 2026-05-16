package com.nedjar;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nedjar on 04/06/15.
 */
public class Rangée extends JPanel {
    private final int taille;
    private CaseJeu[] pions;

    public Rangée(int taille) {
        this.taille = taille;
        this.pions = new CaseJeu[taille];
        this.setLayout(new GridLayout(1, taille, 0, 0));
        for (int i = 0; i < pions.length; i++) {
            pions[i] = new CaseJeu();
            add(pions[i]);
        }
    }

    public Combinaison getCombinaison() {
        Combinaison combinaison = new Combinaison(taille);
        for (int i = 0; i < pions.length; i++) {
            combinaison.setPion(i, pions[i].getPion());
        }
        return combinaison;
    }

    public void setCombinaison(Combinaison combinaison) {
        for (int i = 0; i < pions.length; i++) {
            pions[i].setPion(combinaison.getPion(i));
        }
    }

    public void setEnabled(boolean enabled) {
        for (CaseJeu pion : pions) {
            pion.setEnabled(enabled);
        }
    }

    public void setMasqué(boolean masqué) {
        for (CaseJeu pion : pions) {
            pion.setMasqué(masqué);
        }
    }

    public void vider() {
        for (CaseJeu pion : pions) {
            pion.vider();
        }
    }
}