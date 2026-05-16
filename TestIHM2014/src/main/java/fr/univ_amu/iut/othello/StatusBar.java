package fr.univ_amu.iut.othello;

import javax.swing.*;
import java.awt.*;

class StatusBar extends JPanel {
    private static final String MESSAGE_TOUR_NOIR = "Noir joue !";
    private static final String MESSAGE_TOUR_BLANC = "Blanc joue !";

    private static final String SCORE_NOIR = "Noir : ";
    private static final String SCORE_BLANC = "Blanc : ";
    private static final String MESSAGE_TOUR_FIN_PARTIE = "Partie Terminée";

    private Joueur joueurCourant = Joueur.NOIR;

    private final JLabel messageScoreNoir = new JLabel("", JLabel.LEFT);
    private final JLabel messageScoreBlanc = new JLabel("", JLabel.RIGHT);
    private final JLabel messageTourDeJeu = new JLabel("", JLabel.CENTER);

    public StatusBar() {
        super();
        setLayout(new BorderLayout());
        add(messageScoreNoir, BorderLayout.WEST);
        add(messageTourDeJeu, BorderLayout.CENTER);
        add(messageScoreBlanc, BorderLayout.EAST);
        updateStatus();
    }

    public void setJoueurCourant(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;
    }

    public void updateStatus() {
        messageScoreNoir.setText(SCORE_NOIR + Joueur.NOIR.getScore());
        messageScoreBlanc.setText(SCORE_BLANC + Joueur.BLANC.getScore());
        if (joueurCourant == Joueur.NOIR)
            messageTourDeJeu.setText(MESSAGE_TOUR_NOIR);
        else if (joueurCourant == Joueur.BLANC)
            messageTourDeJeu.setText(MESSAGE_TOUR_BLANC);
        else
            messageTourDeJeu.setText(MESSAGE_TOUR_FIN_PARTIE);
    }
}
