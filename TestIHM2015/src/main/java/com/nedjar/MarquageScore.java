package com.nedjar;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nedjar on 04/06/15.
 */
public class MarquageScore extends JPanel {
    private final int nombrePionsParRangée;
    private CaseScore[] scores;

    public MarquageScore(int nombrePionsParRangée) {
        this.nombrePionsParRangée = nombrePionsParRangée;
        this.scores = new CaseScore[nombrePionsParRangée];
        this.setLayout(new GridLayout(2, 2));
        for (int i = 0; i < nombrePionsParRangée; i++) {
            scores[i] = new CaseScore();
            add(scores[i]);
        }
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
