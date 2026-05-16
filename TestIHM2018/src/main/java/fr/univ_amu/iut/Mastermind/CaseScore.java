package fr.univ_amu.iut.Mastermind;


import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


public class CaseScore extends Button {
    private PionScore score;
    private ImageView imageView;

    public CaseScore() {
        this.score = PionScore.VIDE;
        this.imageView = new ImageView(this.score.getImage());
        setGraphic(imageView);
        setDisable(true);
    }

    void setScore(PionScore score) {
        this.score = score;
        this.imageView.setImage(score.getImage());
    }

    public void vider() {
        setScore(PionScore.VIDE);
    }
}
