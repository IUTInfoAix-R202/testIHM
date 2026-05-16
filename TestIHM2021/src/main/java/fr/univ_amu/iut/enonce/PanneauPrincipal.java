package fr.univ_amu.iut.enonce;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.io.IOException;

public class PanneauPrincipal extends GridPane {

    private Slider axSlider, aySlider;

    private TextField bxField, byField, perimeterTextField;

    private Button bxMinus, bxPlus, byMinus, byPlus;

    private Pane drawPane;

    private Line horizontal1, horizontal2, vertical1, vertical2;

    private Rectangle rectangle = new Rectangle();

    private final double drawPaneSize = 300;
    private final double coordinateMaxValues = 20;
    private final double drawRatio = drawPaneSize / coordinateMaxValues;

    public PanneauPrincipal() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/panneau.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addLines();
        setByMinusActionWithHandler();
        setByPlusActionWithLambda();
        bindSommetsRectangle();
        bindPerimeterTextField();
        bindHorizontal1();
        bindHorizontal2();
        bindVertical1();
        bindVertical2();
    }

    private void addLines() {
        // A compléter Partie B Question 2
    }

    public void decrementXB() {
        // A compléter Partie B Question 3
    }

    public void incrementXB() {
        // A compléter Partie B Question 3
    }

    private void setByPlusActionWithLambda() {
        byPlus.setOnAction(
                // A compléter Partie B Question 3
                null);
    }

    private void setByMinusActionWithHandler() {
        byMinus.setOnAction(
                // A compléter Partie B Question 3
                null );
    }

    private void bindSommetsRectangle() {
        // A compléter Partie C Question 1
    }

    private void bindPerimeterTextField() {
        // A compléter Partie C Question 2
    }

    private void bindHorizontal1() {
        // A compléter Partie C Question 3
    }

    private void bindHorizontal2() {
        // A compléter Partie C Question 3
    }

    private void bindVertical1() {
        // A compléter Partie C Question 3
    }

    private void bindVertical2() {
        // A compléter Partie C Question 3
    }

}