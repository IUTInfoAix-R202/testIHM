package fr.univ_amu.iut;

import fr.univ_amu.iut.game.LetterStatus;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class VirtualKeyboard extends VBox {
    private final Consumer<Character> keystrokeConsumer;
    private final Runnable enterHandler;
    private final Runnable backspaceHandler;
    private final Map<Character, ObjectProperty<LetterStatus>> alphabet;

    public VirtualKeyboard(Consumer<Character> keystrokeConsumer,
                           Runnable enterHandler,
                           Runnable backspaceHandler,
                           Map<Character, ObjectProperty<LetterStatus>> alphabet) {
        super(8.0);

        this.keystrokeConsumer = keystrokeConsumer;
        this.enterHandler = enterHandler;
        this.backspaceHandler = backspaceHandler;
        this.alphabet = alphabet;

        List<Character> row1Keys = "QWERTYUIOP".chars().mapToObj(e -> (char) e).toList();
        List<Character> row2Keys = "ASDFGHJKL".chars().mapToObj(e -> (char) e).toList();
        List<Character> row3Keys = "ZXCVBNM".chars().mapToObj(e -> (char) e).toList();

        getChildren().addAll(
                createRow(row1Keys, 0.0, false, false),
                createRow(row2Keys, 20.0, false, false),
                createRow(row3Keys, 0.0, true, true));
        setPadding(new Insets(10.0, 40.0, 10.0, 40.0));

        setAlignment(Pos.CENTER);
    }

    private HBox createRow(List<Character> letters, double leftPadding, boolean includeEnter, boolean includeBackspace) {
        HBox row = new HBox(6.0);
        if (includeEnter) {
            row.getChildren().addAll(createEnterKey());
        }

        row.getChildren().addAll(letters.stream().map(this::createLetterButton).toList());

        if (includeBackspace) {
            row.getChildren().addAll(createBackspaceKey());
        }
        row.setPadding(new Insets(0.0, 0.0, 0.0, leftPadding));
        return row;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMinSize(44.0, 58.0);
        button.getStyleClass().add("key-button");
        return button;
    }

    private Button createLetterButton(char letter) {
        Button button = createButton("" + letter);
        button.setOnMouseClicked(event -> keystrokeConsumer.accept(letter));
        if (alphabet.get(letter) != null) {
            LetterStatusPseudoClass.addPseudoClass(button, alphabet.get(letter));
        }
        return button;
    }

    private Button createBackspaceKey() {
        Button button = createButton("");
        button.setGraphic(new FontIcon("typ-backspace-outline"));
        button.setMinWidth(64);
        button.setOnAction(event -> backspaceHandler.run());
        return button;
    }

    private Button createEnterKey() {
        String enter = "Enter";
        Button button = createButton(enter);
        button.setOnAction(event -> enterHandler.run());
        return button;
    }
}
