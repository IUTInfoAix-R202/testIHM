package fr.univ_amu.iut;

import fr.univ_amu.iut.game.LetterStatus;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class LetterModel {
    int column;
    ObjectProperty<Character> letter = new SimpleObjectProperty<>();
    ObjectProperty<LetterStatus> status = new SimpleObjectProperty<>();

    public LetterModel(int column) {
        this.column = column;
        clear();
    }

    void clear() {
        letter.set(' ');
        status.set(LetterStatus.EMPTY);
    }
}
