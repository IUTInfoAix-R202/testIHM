import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nedjar on 01/06/14.
 */
class OthelloIHM extends JFrame {
    private static final int TAILLE = 4;

    private StatusBar statusBar = new StatusBar();
    private Othellier othellier = new Othellier(this, TAILLE);

    private OthelloIHM() {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Othello");
        setLayout(new BorderLayout());

        setJMenuBar(barreDeMenus());

        add(othellier, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public void updateStatus() {
        if (othellier.getJoueurCourant() == Joueur.PERSONNE) {
            afficheDialogFinDePartie();
        }

        statusBar.setJoueurCourant(othellier.getJoueurCourant());
        statusBar.updateStatus();
    }

    private void afficheDialogFinDePartie() {
        String messageFinDePartie;

        if (Joueur.BLANC.getScore() > Joueur.NOIR.getScore())
            messageFinDePartie = "Blanc a gagné !!!";
        else if (Joueur.BLANC.getScore() < Joueur.NOIR.getScore())
            messageFinDePartie = "Noir a gagné !!!";
        else
            messageFinDePartie = "Égalité !!!";

        JOptionPane.showMessageDialog(this, messageFinDePartie);
    }

    private JMenuBar barreDeMenus() {
        JMenuBar barre = new JMenuBar();
        barre.add(creerMenuJeu());
        return barre;
    }

    private JMenu creerMenuJeu() {
        JMenu menuJeu = new JMenu("Jeu");
        menuJeu.add(creerMenuJeuNouveau());
        menuJeu.add(creerMenuJeuQuitter());
        return menuJeu;
    }

    private JMenuItem creerMenuJeuNouveau() {
        JMenuItem menu = new JMenuItem("Nouveau");
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionMenuJeuNouveau();
            }
        });
        return menu;
    }

    private JMenuItem creerMenuJeuQuitter() {
        JMenuItem menu = new JMenuItem("Quitter");
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionMenuJeuQuitter();
            }
        });
        return menu;
    }

    private void actionMenuJeuQuitter() {
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment quitter ?"))
            System.exit(0);
    }

    private void actionMenuJeuNouveau() {
        othellier.nouvellePartie();
    }

    public static void main(String[] args) {
        new OthelloIHM();
    }
}
