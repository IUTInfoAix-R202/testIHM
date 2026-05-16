package fr.univ_amu.iut;

import fr.univ_amu.iut.game.LetterStatus;
import javafx.beans.binding.Bindings;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Builder;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;
import java.util.stream.IntStream;

public class ViewBuilder implements Builder<Region> {
    private final WordleModel model;
    private final Region keyboard;

    public ViewBuilder(WordleModel model, Region keyboard) {
        this.model = model;
        this.keyboard = keyboard;
    }

    @Override
    public Region build() {
        VBox vBox = new VBox(40.0, createTitle(), createTilePane(), badWordMessage(), keyboard);
        vBox.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/wordle.css")).toExternalForm());
        vBox.getStyleClass().add("main-screen");
        vBox.setAlignment(Pos.TOP_CENTER);
        var darkModePseudoClass = PseudoClass.getPseudoClass("dark-mode");
        model.darkMode.addListener((observable) -> vBox.pseudoClassStateChanged(darkModePseudoClass, model.darkMode.get()));
        return vBox;
    }

    private Node badWordMessage() {
        Label label = new Label("Not in word list");
        label.getStyleClass().add("bad-word");
        label.setOpacity(0.0);
        System.out.println("starting " + model.wordsValid.get());
        model.wordsValid.addListener((observable) -> {
            System.out.println("listener: " + model.wordsValid.get());
            if (! model.wordsValid.get()) {
                WordleAnimation.showBadWordLabel(label);
            }
        });
        return label;
    }

    private Node createTilePane() {
        VBox vBox = new VBox(7.);
        vBox.getChildren().addAll(IntStream.range(0, 6).mapToObj(this::createRow).toList());
        return vBox;
    }

    private Node createRow(int row) {
        HBox hBox = new HBox(5.0);
        hBox.setAlignment(Pos.CENTER);

        hBox.getChildren().addAll(IntStream.range(0, 5).mapToObj(column -> createLetterBox(model.letters.get(row).get(column))).toList());

        model.wordValidity.get(row).addListener(observable -> {
            if (! model.wordValidity.get(row).get()) {
                WordleAnimation.wiggleRow(hBox);
            }
        });

        return hBox;
    }

    private Node createLetterBox(LetterModel letterModel) {
        StackPane stackPane = new StackPane();
        stackPane.getStyleClass().add("tile-box");

        Label label = new Label();
        label.textProperty().bind(Bindings.createStringBinding(() -> letterModel.letter.get().toString(), letterModel.letter));
        label.getStyleClass().add("tile-letter");

        stackPane.getChildren().add(label);

        letterModel.status.addListener((observable) -> {
            if (letterModel.status.get().ordinal() > LetterStatus.UNLOCKED.ordinal()) {
                WordleAnimation.flipTile(stackPane, letterModel.column, letterModel.status.get());
            } else {
                if (letterModel.status.get() == LetterStatus.UNLOCKED) {
                    WordleAnimation.flashTile(stackPane);
                }
                LetterStatusPseudoClass.updatePseudoClass(stackPane, letterModel.status.get());
            }
        });
        return stackPane;
    }

    private Node createTitle() {
        StackPane stackPane = new StackPane();

        Label label = new Label("Wordle");
        label.getStyleClass().add("title");

        FontIcon fontIcon = new FontIcon("typ-cog-outline");
        fontIcon.setIconSize(26);
        fontIcon.setIconColor(Color.GRAY);
        StackPane.setAlignment(fontIcon, Pos.CENTER_RIGHT);
        fontIcon.setOnMouseClicked(mouseEvent -> model.darkMode.set(! model.darkMode.get()));

        Separator separator = new Separator();
        StackPane.setAlignment(separator, Pos.BOTTOM_CENTER);

        stackPane.getChildren().addAll(label, fontIcon, separator);

        return stackPane;
    }
}
