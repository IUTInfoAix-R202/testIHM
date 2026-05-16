package com.nedjar;

import javax.swing.*;

/**
 * Created by nedjar on 04/06/15.
 */
public enum PionScore {
    VIDE("pasdemarque.png"),
    BIENPLACE("bienplace.png"),
    MALPLACE("malplace.png");

    private String nomFichier;
    private ImageIcon icon;

    PionScore(String nomFichier) {
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
}
