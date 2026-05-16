package fr.univ_amu.iut.Mastermind;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class CaseJeu extends Button {
    private PionJeu pion;
    private ImageView imageView;

    private final EventHandler<ActionEvent> boutonPionListener = event -> {
        setPion(pion.suivant());
    };

    public CaseJeu() {
        this.pion = PionJeu.VIDE;
        this.imageView = new ImageView(this.pion.getImage());
        setGraphic(imageView);
        setOnAction(boutonPionListener);
        setDisable(true);
    }

    public void vider() {
        setPion(PionJeu.VIDE);
    }

    public PionJeu getPion() {
        return pion;
    }

    void setPion(PionJeu pion) {
        this.pion = pion;
        this.imageView.setImage(pion.getImage());

    }

    public void setMasqué(boolean masqué) {
        if (masqué)
            this.imageView.setImage(PionJeu.VIDE.getImage());
        else
            this.imageView.setImage(pion.getImage());
    }
}
