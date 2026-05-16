package com.nedjar;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by nedjar on 04/06/15.
 */
public class Combinaison {
    private final int taille;
    private PionJeu[] pions;

    public Combinaison(int taille) {
        this.taille = taille;
        this.pions = new PionJeu[taille];
        for (int i = 0; i < pions.length; i++) {
            pions[i] = PionJeu.VIDE;
        }
    }

    public static Combinaison genererCombinaisonAléatoire(int taille) {
        Combinaison combinaison = new Combinaison(taille);
        Random random = new Random();
        for (int i = 0; i < taille; i++) {
            while (combinaison.pions[i] == PionJeu.VIDE) {
                int indicePion = random.nextInt(taille);
                combinaison.pions[i] = PionJeu.values()[indicePion];
            }
        }
        return combinaison;
    }

    public boolean contient(PionJeu pion) {
        return Arrays.asList(pions).contains(pion);
    }

    public PionJeu getPion(int i) {
        return pions[i];
    }

    public void setPion(int i, PionJeu pion) {
        pions[i] = pion;
    }

    public int nombrePions() {
        return pions.length;
    }
}
