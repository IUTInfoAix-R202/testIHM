import javax.swing.*;

/**
 * Created by nedjar on 01/06/14.
 */
public enum Joueur {
    NOIR("resources/noir.png"),
    BLANC("resources/blanc.png"),
    PERSONNE("resources/vide.png");

    private final Icon icon;
    private int score;

    private Joueur(String fileName) {
        icon = new ImageIcon(fileName);
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void decrementerScore() {
        score--;
    }

    public void incrementerScore() {
        score++;
    }

    public Icon getIcon() {
        return icon;
    }

    public Joueur suivant() {
        if (this == BLANC)
            return NOIR;
        if (this == NOIR)
            return BLANC;
        return PERSONNE;
    }

    private void initialiserScore() {
        score = 0;
    }

    public static void initialiserScores() {
        BLANC.initialiserScore();
        NOIR.initialiserScore();
    }
}
