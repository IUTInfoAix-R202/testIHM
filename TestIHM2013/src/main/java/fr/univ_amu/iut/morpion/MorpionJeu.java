package fr.univ_amu.iut.morpion;

public class MorpionJeu {

    private int[][] grille;

    public MorpionJeu() {
        grille = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grille[i][j] = -1;
            }
        }
    }

    public int autreJoueur(int joueur) {
        return 1 - joueur;
    }

    public String nomJoueur(int joueur) {
        return "Joueur " + (joueur + 1);
    }

    public void enregistrerCoupJoueur(int i, int j, int joueur) {
        grille[i][j] = joueur;
    }

    public boolean grilleEstRemplie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grille[i][j] == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean aGagne(int joueur) {
        for (int i = 0; i < 3; i++) {
            if (grille[i][0] == joueur && grille[i][1] == joueur && grille[i][2] == joueur) {
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            if (grille[0][j] == joueur && grille[1][j] == joueur && grille[2][j] == joueur) {
                return true;
            }
        }
        if (grille[0][0] == joueur && grille[1][1] == joueur && grille[2][2] == joueur) {
            return true;
        }
        if (grille[0][2] == joueur && grille[1][1] == joueur && grille[2][0] == joueur) {
            return true;
        }
        return false;
    }
}
