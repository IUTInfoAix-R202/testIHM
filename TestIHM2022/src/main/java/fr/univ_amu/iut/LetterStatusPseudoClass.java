package fr.univ_amu.iut;

import fr.univ_amu.iut.game.LetterStatus;
import javafx.beans.property.ObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;

public class LetterStatusPseudoClass {
    private static final PseudoClass emptyPseudoClass = PseudoClass.getPseudoClass("empty");
    private static final PseudoClass unlockedPseudoClass = PseudoClass.getPseudoClass("unlocked");
    private static final PseudoClass wrongPseudoClass = PseudoClass.getPseudoClass("wrong");
    private static final PseudoClass presentPseudoClass = PseudoClass.getPseudoClass("present");
    private static final PseudoClass correctPseudoClass = PseudoClass.getPseudoClass("correct");

    public static void updatePseudoClass(Node node, LetterStatus letterStatus) {
        node.pseudoClassStateChanged(emptyPseudoClass, false);
        node.pseudoClassStateChanged(unlockedPseudoClass, false);
        node.pseudoClassStateChanged(wrongPseudoClass, false);
        node.pseudoClassStateChanged(presentPseudoClass, false);
        node.pseudoClassStateChanged(correctPseudoClass, false);
        switch (letterStatus) {
            case EMPTY -> node.pseudoClassStateChanged(emptyPseudoClass, true);
            case UNLOCKED -> node.pseudoClassStateChanged(unlockedPseudoClass, true);
            case WRONG -> node.pseudoClassStateChanged(wrongPseudoClass, true);
            case PRESENT -> node.pseudoClassStateChanged(presentPseudoClass, true);
            case CORRECT -> node.pseudoClassStateChanged(correctPseudoClass, true);
        }
    }

    public static void addPseudoClass(Node node, ObjectProperty<LetterStatus> property) {
        property.addListener(observable -> updatePseudoClass(node, property.get()));
    }
}
