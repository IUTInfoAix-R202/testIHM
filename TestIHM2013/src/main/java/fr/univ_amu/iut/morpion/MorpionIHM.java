package fr.univ_amu.iut.morpion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MorpionIHM extends JFrame {

    private ImageIcon[] icones;
    private MorpionJeu jeu;
    private JLabel barreEtat;
    private int joueurCourant;

    private void chargerIcones() {
        icones = new ImageIcon[2];
        icones[0] = new ImageIcon(MorpionIHM.class.getResource("/croix.jpg"));
        icones[1] = new ImageIcon(MorpionIHM.class.getResource("/rond.jpg"));
    }

    private class AuditeurJeu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            BoutonJeu bouton = (BoutonJeu) evt.getSource();
            bouton.setIcon(icones[joueurCourant]);
            bouton.setEnabled(false);
            jeu.enregistrerCoupJoueur(bouton.getLigne(), bouton.getColonne(), joueurCourant);
            if (jeu.aGagne(joueurCourant)) {
                barreEtat.setText("Terminé : " + jeu.nomJoueur(joueurCourant) + " a gagné !!");
                return;
            }
            if (jeu.grilleEstRemplie()) {
                barreEtat.setText("Match nul");
                return;
            }
            joueurCourant = jeu.autreJoueur(joueurCourant);
            barreEtat.setText(jeu.nomJoueur(joueurCourant) + ", à vous");
        }
    }

    private JPanel creerGrille(ActionListener auditeur) {
        JPanel panneau = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BoutonJeu bouton = new BoutonJeu(i, j);
                bouton.setPreferredSize(new Dimension(80, 80));
                bouton.addActionListener(auditeur);
                panneau.add(bouton);
            }
        }
        return panneau;
    }

    public MorpionIHM() {
        super("Jeu de Morpion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chargerIcones();
        jeu = new MorpionJeu();
        AuditeurJeu auditeur = new AuditeurJeu();

        setLayout(new BorderLayout());
        add(creerGrille(auditeur), BorderLayout.CENTER);
        barreEtat = new JLabel();
        add(barreEtat, BorderLayout.SOUTH);

        pack();
        joueurCourant = 0;
        barreEtat.setText(jeu.nomJoueur(joueurCourant) + ", à vous");
    }

    public static void main(String[] args) {
        new MorpionIHM().setVisible(true);
    }
}
