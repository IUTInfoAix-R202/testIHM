package fr.univ_amu.iut.othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Othellier extends JPanel {

    private static final Point[] directions = {
            new Point(1, 0),
            new Point(1, 1),
            new Point(0, 1),
            new Point(-1, 1),
            new Point(-1, 0),
            new Point(-1, -1),
            new Point(0, -1),
            new Point(1, -1)
    };

    private final ActionListener caseListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Case caseSelectionnee = (Case) actionEvent.getSource();
            if (estPositionJouable(caseSelectionnee)) {
                capturer(caseSelectionnee);
                tourSuivant();
                pere.updateStatus();
            }
        }
    };

    private int taille;
    private Case[][] cases;
    private Joueur joueurCourant = Joueur.NOIR;
    private OthelloIHM pere;

    public Othellier(OthelloIHM pere, int taille) {
        super();
        this.pere = pere;
        this.taille = taille;
        cases = new Case[taille][taille];

        setLayout(new GridLayout(taille, taille));

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cases[i][j] = new Case(i, j);
                cases[i][j].addActionListener(caseListener);
                add(cases[i][j]);
            }
        }
        positionnerPionsDebutPartie();
    }

    private void positionnerPionsDebutPartie() {
        cases[taille / 2 - 1][taille / 2 - 1].setPossesseur(Joueur.BLANC);
        cases[taille / 2][taille / 2].setPossesseur(Joueur.BLANC);
        Joueur.BLANC.incrementerScore();
        Joueur.BLANC.incrementerScore();

        cases[taille / 2 - 1][taille / 2].setPossesseur(Joueur.NOIR);
        cases[taille / 2][taille / 2 - 1].setPossesseur(Joueur.NOIR);
        Joueur.NOIR.incrementerScore();
        Joueur.NOIR.incrementerScore();
    }


    public void nouvellePartie() {
        joueurCourant = Joueur.NOIR;
        Joueur.initialiserScores();
        vider();
        positionnerPionsDebutPartie();
        pere.updateStatus();
    }

    public boolean peutJouer() {
        return !casesJouables().isEmpty();
    }

    private List<Case> casesJouables() {
        List<Case> casesJouables = new ArrayList<Case>();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (cases[i][j].getPossesseur() == Joueur.PERSONNE && !casesCapturable(cases[i][j]).isEmpty()) {
                    casesJouables.add(cases[i][j]);
                }
            }
        }
        return casesJouables;
    }

    private List<Case> casesCapturable(Case caseSelectionnee) {
        List<Case> casesCapturable = new ArrayList<Case>();

        for (Point direction : directions) {
            casesCapturable.addAll(casesCapturable(caseSelectionnee, direction));
        }

        return casesCapturable;
    }

    private List<Case> casesCapturable(Case caseSelectionnee, Point direction) {
        List<Case> casesCapturable = new ArrayList<Case>();

        int indiceLigne = caseSelectionnee.getLigne() + direction.y;
        int indiceColonne = caseSelectionnee.getColonne() + direction.x;

        while (estIndicesValides(indiceLigne, indiceColonne)) {
            if (cases[indiceLigne][indiceColonne].getPossesseur() != joueurCourant.suivant())
                break;

            casesCapturable.add(cases[indiceLigne][indiceColonne]);

            indiceLigne += direction.y;
            indiceColonne += direction.x;
        }

        if (estIndicesValides(indiceLigne, indiceColonne) &&
                cases[indiceLigne][indiceColonne].getPossesseur() == joueurCourant)
            return casesCapturable;
        return new ArrayList<Case>();
    }

    private boolean estIndicesValides(int indiceLigne, int indiceColonne) {
        return estIndiceValide(indiceColonne) && estIndiceValide(indiceLigne);
    }

    private boolean estIndiceValide(int indiceColonne) {
        return indiceColonne < taille && indiceColonne >= 0;
    }

    private boolean estPositionJouable(Case caseSelectionnee) {
        if (caseSelectionnee.getPossesseur() != Joueur.PERSONNE)
            return false;
        return !casesCapturable(caseSelectionnee).isEmpty();
    }

    private void capturer(Case caseCapturee) {
        caseCapturee.setPossesseur(joueurCourant);
        joueurCourant.incrementerScore();

        List<Case> casesCapturees = casesCapturable(caseCapturee);
        for (Case caseCourante : casesCapturees) {
            caseCourante.setPossesseur(joueurCourant);
            joueurCourant.incrementerScore();
            joueurCourant.suivant().decrementerScore();
        }
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    private void tourSuivant() {
        joueurCourant = joueurCourant.suivant();
        if (!peutJouer()) {
            joueurCourant = joueurCourant.suivant();
            if (!peutJouer())
                joueurCourant = Joueur.PERSONNE;
        }
    }

    private void vider() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cases[i][j].setPossesseur(Joueur.PERSONNE);
            }
        }
    }
}
