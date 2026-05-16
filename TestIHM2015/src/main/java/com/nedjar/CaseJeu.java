package com.nedjar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nedjar on 04/06/15.
 */
public class CaseJeu extends JButton {
    private PionJeu pion;
    private final ActionListener boutonPionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setPion(pion.suivant());
        }
    };

    public CaseJeu() {
        vider();
        addActionListener(boutonPionListener);
        setEnabled(false);
    }

    public void vider() {
        setPion(PionJeu.VIDE);
    }

    public PionJeu getPion() {
        return pion;
    }

    void setPion(PionJeu pion) {
        this.pion = pion;
        setIcon(pion.getIcon());
        setDisabledIcon(pion.getIcon());
    }

    public void setMasqué(boolean masqué) {
        if (masqué)
            setDisabledIcon(PionJeu.VIDE.getIcon());
        else
            setDisabledIcon(pion.getIcon());
    }
}
