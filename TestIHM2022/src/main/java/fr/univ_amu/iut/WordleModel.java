package fr.univ_amu.iut;

import fr.univ_amu.iut.game.LetterStatus;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.*;
import javafx.beans.value.ObservableBooleanValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WordleModel {
    int currentColumn = 0;

    List<List<LetterModel>> letters = createLetters();

    Map<Character, ObjectProperty<LetterStatus>> alphabet = createAlphabet();

    List<BooleanProperty> wordValidity = createWordValidity();

    BooleanExpression wordsValid = createWordsValid();

    IntegerProperty currentRow = new SimpleIntegerProperty(0);

    BooleanProperty wordGuessed = new SimpleBooleanProperty(false);

    ObservableBooleanValue gameOver =
            Bindings.createBooleanBinding(() -> currentRow.get() > 5 || wordGuessed.get(), currentRow, wordGuessed);

    BooleanProperty darkMode = new SimpleBooleanProperty(false);

    private List<List<LetterModel>> createLetters() {
        List<List<LetterModel>> letters = new ArrayList<>(6);
        for (int line = 0; line < 6; line++) {
            List<LetterModel> word = new ArrayList<>(5);
            for (int column = 0; column < 5; column++) {
                word.add(new LetterModel(column));
            }
            letters.add(word);
        }
        return letters;
    }

    private Map<Character, ObjectProperty<LetterStatus>> createAlphabet() {
        return IntStream.rangeClosed('A', 'Z')
                .mapToObj(e -> (char) e)
                .collect(Collectors.toMap(
                        character -> character,
                        character -> new SimpleObjectProperty<>(LetterStatus.UNLOCKED)));
    }

    private List<BooleanProperty> createWordValidity() {
        List<BooleanProperty> wordValidity = new ArrayList<>(6);
        IntStream.range(0, 6).forEach(i -> wordValidity.add(new SimpleBooleanProperty(true)));
        return wordValidity;
    }

    private BooleanExpression createWordsValid() {
        return IntStream.range(1, 6).mapToObj(index -> (BooleanExpression) wordValidity.get(index)).reduce(new SimpleBooleanProperty(true), BooleanExpression::and);
    }
}
