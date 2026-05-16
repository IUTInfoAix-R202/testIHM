package fr.univ_amu.iut.Mastermind;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class MarquageScore extends GridPane {
    private final int nombrePionsParRangée;
    private final int taille;
    private CaseScore[] scores;

    public MarquageScore() {
        this.nombrePionsParRangée = MasterMindControleur.NB_PIONS_PAR_RANGEES;
        this.scores = new CaseScore[nombrePionsParRangée];
        this.taille = 2;
        for (int i = 0; i < nombrePionsParRangée; i++) {
            scores[i] = new CaseScore();
            add(scores[i], i % taille, i / taille);
        }
        setAlignment(Pos.CENTER_RIGHT);
    }


    public void setScore(int nombrePionsBiensPlacés, int nombrePionsMalsPlacés) {
        if (nombrePionsBiensPlacés + nombrePionsMalsPlacés > nombrePionsParRangée)
            throw new RuntimeException("Trop de points marqués");

        for (int i = 0; i < nombrePionsBiensPlacés; i++) {
            scores[i].setScore(PionScore.BIENPLACE);
        }

        for (int i = nombrePionsBiensPlacés; i < nombrePionsBiensPlacés + nombrePionsMalsPlacés; i++) {
            scores[i].setScore(PionScore.MALPLACE);
        }
    }

    public void vider() {
        for (CaseScore score : scores) {
            score.vider();
        }
    }
}
