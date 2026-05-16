package fr.univ_amu.iut;

import javafx.scene.layout.Region;

public class WordleController {
    private final WordleModel model = new WordleModel();
    private final WordleInteractor interactor = new WordleInteractor(model);

    final Region view =
            new ViewBuilder(model, new VirtualKeyboard(interactor::handleLetter, interactor::checkWord, interactor::eraseLetter, model.alphabet)).build();

    public WordleController() {
        view.setOnKeyTyped(event -> {
            switch (event.getCode()) {
                case ENTER -> interactor.checkWord();
                case BACK_SPACE -> interactor.eraseLetter();
                case UNDEFINED -> {
                    switch (event.getCharacter()) {
                        case String s && s.equals("\r") -> interactor.checkWord();
                        case String s && s.equals("\b") -> interactor.eraseLetter();
                        case String s && Character.isAlphabetic(s.charAt(0)) ->
                                interactor.handleLetter(s.toUpperCase().charAt(0));
                        default -> System.out.println("Not valid character :" + event.getCharacter());
                    }
                }
            }
        });
    }
}
