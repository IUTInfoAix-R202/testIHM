package com.nedjar;

import javax.swing.*;

/**
 * Created by nedjar on 04/06/15.
 */
public enum PionJeu {
    VIDE("vide.png"),
    ROUGE("rouge.png"),
    BLEU("bleu.png"),
    FUCHSIA("fuchsia.png"),
    JAUNE("jaune.png"),
    MARRON("marron.png"),
    ORANGE("orange.png"),
    VERT("vert.png"),
    VIOLET("violet.png");

    private String nomFichier;
    private Icon icon;

    PionJeu(String nomFichier) {
        if (nomFichier != null)
            this.nomFichier = nomFichier;
        else
            this.nomFichier = "";

        ClassLoader classLoader = getClass().getClassLoader();
        this.icon = new ImageIcon(classLoader.getResource(nomFichier));
    }

    public Icon getIcon() {
        return icon;
    }

    public PionJeu suivant() {
        return PionJeu.values()[(ordinal() + 1) % PionJeu.values().length];
    }
}
