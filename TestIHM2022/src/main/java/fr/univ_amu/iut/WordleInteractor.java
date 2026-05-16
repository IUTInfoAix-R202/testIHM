package fr.univ_amu.iut;

import fr.univ_amu.iut.game.LetterStatus;

import java.util.Collection;
import java.util.List;

public class WordleInteractor {
    WordleData data = new WordleData();
    WordleModel model;

    public WordleInteractor(WordleModel model) {
        this.model = model;
    }

    void handleLetter(Character newLetter) {
        if ((model.currentColumn < 5) && ! model.gameOver.get()) {
            model.letters.get(model.currentRow.get()).get(model.currentColumn).letter.set(newLetter);
            model.letters.get(model.currentRow.get()).get(model.currentColumn).status.set(LetterStatus.UNLOCKED);
            model.currentColumn++;
        }
    }

    void checkWord() {
        if (model.currentColumn > 4) {
            model.wordValidity.get(model.currentRow.get()).set(true);
            var guess = model.letters.get(model.currentRow.get());
            if (data.isWordValid(guess.stream().map(letterModel -> letterModel.letter.get()).toList())) {
                performCheck(guess);
                model.currentRow.set(model.currentRow.get() + 1);
                model.currentColumn = 0;
                setAlphabet();
            } else {
                model.wordValidity.get(model.currentRow.get()).set(false);
            }
        }
    }

    void eraseLetter() {
        if (model.currentColumn > 0) {
            model.currentColumn -= 1;
            model.letters.get(model.currentRow.get()).get(model.currentColumn).clear();
        }
    }

    private void performCheck(List<LetterModel> guess) {
        List<Character> guessedCharacters = guess.stream().map(letterModel -> letterModel.letter.get()).toList();
        data.getCorrectPosition(guessedCharacters).stream().map(guess::get).forEach(letterModel -> letterModel.status.set(LetterStatus.CORRECT));
        data.getIncorrectPosition(guessedCharacters).stream().map(guess::get).forEach(letterModel -> letterModel.status.set(LetterStatus.PRESENT));
        data.getWrongLetterPosition(guessedCharacters).stream().map(guess::get).forEach(letterModel -> letterModel.status.set(LetterStatus.WRONG));
        model.wordGuessed.set(data.getCorrectPosition(guessedCharacters).size() == 5);
    }

    private void setAlphabet() {
        model.alphabet.forEach((character, letterStatusObjectProperty) -> model.letters.stream()
                .flatMap(Collection::stream)
                .filter(letterModel -> letterModel.letter.get() == character)
                .forEach(letterModel ->
                        letterStatusObjectProperty.set(letterModel.status.get())
                ));
    }
}
