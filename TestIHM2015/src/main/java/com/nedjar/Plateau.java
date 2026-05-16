package com.nedjar;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nedjar on 04/06/15.
 */
public class Plateau extends JPanel {
    private final int nombrePionsParRangée;
    private final int nombreRangées;

    private Rangée[] rangées;
    private MarquageScore[] scores;
    private int rangéeCourante;
    private boolean aGagné = false;

    public Plateau(int nombreRangées, int nombrePionsParRangée) {

        this.rangéeCourante = 0;
        this.nombreRangées = nombreRangées;
        this.nombrePionsParRangée = nombrePionsParRangée;
        this.rangées = new Rangée[nombreRangées];
        this.scores = new MarquageScore[nombreRangées];

        setLayout(new GridLayout(nombreRangées, 2));
        for (int i = nombreRangées - 1; i >= 0; i--) {
            rangées[i] = new Rangée(nombrePionsParRangée);
            scores[i] = new MarquageScore(nombrePionsParRangée);

            add(rangées[i]);
            JPanel panel = new JPanel();
            panel.add(scores[i]);
            add(panel);
        }

        rangées[0].setEnabled(true);
    }

    public int nombreDeCoupsJoués() {
        return rangéeCourante;
    }

    public void viderRangéeCourante() {
        if (!estFinDePartie())
            rangées[rangéeCourante].vider();
    }

    public void nouvellePartie() {
        for (int i = 0; i < nombreRangées; i++) {
            rangées[i].vider();
            scores[i].vider();
            rangées[i].setEnabled(false);
        }
        rangées[0].setEnabled(true);
    }

    public void validerRangéeCourante(Combinaison combinaisonSecrète) {
        int nombrePionsBiensPlacés = calculerNombrePionsBiensPlacés(combinaisonSecrète);
        int nombrePionsMalsPlacés = calculerNombrePionsMalsPlacés(combinaisonSecrète);
        rangées[rangéeCourante].setEnabled(false);
        scores[rangéeCourante].setScore(nombrePionsBiensPlacés, nombrePionsMalsPlacés);
        rangéeCourante++;
        if (nombrePionsBiensPlacés == nombrePionsParRangée) {
            aGagné = true;
        } else if (rangéeCourante < nombreRangées) {
            rangées[rangéeCourante].setEnabled(true);
        }
    }

    private int calculerNombrePionsMalsPlacés(Combinaison combinaisonSecrète) {
        int nombrePionsMalsPlacés = 0;
        for (int i = 0; i < combinaisonSecrète.nombrePions(); i++)
            if (combinaisonSecrète.getPion(i) != combinaisonCourante().getPion(i)
                    && combinaisonSecrète.contient(combinaisonCourante().getPion(i)))
                nombrePionsMalsPlacés++;
        return nombrePionsMalsPlacés;
    }

    private int calculerNombrePionsBiensPlacés(Combinaison combinaisonSecrète) {
        int nombrePionsBiensPlacés = 0;
        for (int i = 0; i < combinaisonSecrète.nombrePions(); i++)
            if (combinaisonSecrète.getPion(i) == combinaisonCourante().getPion(i))
                nombrePionsBiensPlacés++;
        return nombrePionsBiensPlacés;
    }

    public boolean estFinDePartie() {
        return aPerdu() || aGagné();
    }

    public boolean aPerdu() {
        return rangéeCourante == nombreRangées;
    }

    public boolean aGagné() {
        return aGagné;
    }

    public Combinaison combinaisonCourante() {
        return rangées[rangéeCourante].getCombinaison();
    }
}
