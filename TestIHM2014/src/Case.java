import javax.swing.*;
import java.awt.*;

/**
 * Created by nedjar on 01/06/14.
 */
class Case extends JButton {
    private int ligne;
    private int colonne;
    private Joueur possesseur;

    public Case(int ligne, int colonne) {
        setPreferredSize(new Dimension(50, 50));
        this.colonne = colonne;
        this.ligne = ligne;
        this.possesseur = Joueur.PERSONNE;
    }

    public void setPossesseur(Joueur possesseur) {
        this.possesseur = possesseur;
        setIcon(possesseur.getIcon());
    }

    public Joueur getPossesseur() {
        return possesseur;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }
}
