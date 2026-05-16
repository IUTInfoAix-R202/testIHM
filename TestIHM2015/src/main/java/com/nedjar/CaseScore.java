package com.nedjar;

import javax.swing.*;

/**
 * Created by nedjar on 04/06/15.
 */
public class CaseScore extends JButton {

    private PionScore score;

    public CaseScore() {
        vider();
        setEnabled(false);
    }

    void setScore(PionScore score) {
        this.score = score;
        setIcon(score.getIcon());
        setDisabledIcon(score.getIcon());
    }

    public void vider() {
        setScore(PionScore.VIDE);
    }
}
