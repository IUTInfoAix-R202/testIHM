package fr.univ_amu.iut.Mastermind;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class Rangée extends HBox {
    private CaseJeu[] pions;

    public Rangée() {
        this.pions = new CaseJeu[MasterMindControleur.NB_PIONS_PAR_RANGEES];
        for (int i = 0; i < pions.length; i++) {
            pions[i] = new CaseJeu();
        }
        getChildren().addAll(pions);
        setAlignment(Pos.CENTER);
    }


    public Combinaison getCombinaison() {
        Combinaison combinaison = new Combinaison(pions.length);
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

    public void setActivée(boolean activée) {
        for (CaseJeu pion : pions) {
            pion.setDisable(!activée);
        }
    }

    public void setMasquée(boolean masquée) {
        for (CaseJeu pion : pions) {
            pion.setMasqué(masquée);
        }
    }

    public void vider() {
        for (CaseJeu pion : pions) {
            pion.vider();
        }
    }
}