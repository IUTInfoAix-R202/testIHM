package fr.univ_amu.iut.Mastermind;

import java.util.Arrays;
import java.util.Random;

public class Combinaison {
    private PionJeu[] pions;

    public Combinaison(int nombrePions) {
        this.pions = new PionJeu[nombrePions];
        for (int i = 0; i < pions.length; i++) {
            pions[i] = PionJeu.VIDE;
        }
    }

    public static Combinaison générerCombinaisonAléatoire() {
        int nombrePions = MasterMindControleur.NB_PIONS_PAR_RANGEES;
        Combinaison combinaison = new Combinaison(nombrePions);
        Random random = new Random();
        for (int i = 0; i < nombrePions; i++) {
            while (combinaison.pions[i] == PionJeu.VIDE) {
                int indicePion = random.nextInt(nombrePions);
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

    public int getNombrePions() {
        return pions.length;
    }
}
