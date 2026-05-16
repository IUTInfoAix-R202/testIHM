package fr.univ_amu.iut.exercice2;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;

public class PanneauPrincipal extends GridPane {

    @FXML // Partie B Question 1 et fx:id correspondant dans panneau.fxml
    private Slider axSlider, aySlider;

    @FXML // Partie B Question 1 et fx:id correspondant dans panneau.fxml
    private TextField bxField, byField, perimeterTextField;

    @FXML // Partie B Question 1 et fx:id correspondant dans panneau.fxml
    private Button bxMinus, bxPlus, byMinus, byPlus;

    @FXML // Partie B Question 1 et fx:id correspondant dans panneau.fxml
    private Pane drawPane;

    private Line width1, width2, height1, height2;

    private Rectangle rectanglePerimeter = new Rectangle();

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
        bindWidth1();
        bindWidth2();
        bindHeight1();
        bindHeight2();
    }

    private void addLines() { // Partie B Question 2
        width1 = new Line();
        width2 = new Line();
        height1 = new Line();
        height2 = new Line();
        drawPane.getChildren().addAll(width1, width2, height1, height2);
    }

    @FXML // Partie B Question 1 et onAction correspondant dans panneau.fxml
    public void decrementBx() {
        int bxValue = Integer.parseInt(bxField.getText());
        if (bxValue > 0) bxValue--;
        bxField.setText(String.valueOf(bxValue));
    }

    @FXML // Partie B Question 1 et onAction correspondant dans panneau.fxml
    public void incrementBx() {
        int bxValue = Integer.parseInt(bxField.getText());
        if (bxValue < coordinateMaxValues) bxValue++;
        bxField.setText(String.valueOf(bxValue));
    }

    private void setByPlusActionWithLambda() { // Partie B Question 3
        byPlus.setOnAction(actionEvent -> {
            int byValue = Integer.parseInt(byField.getText());
            if (byValue < coordinateMaxValues) byValue++;
            byField.setText(String.valueOf(byValue));
        });
    }

    private void setByMinusActionWithHandler() { // Partie B Question 3
        byMinus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int byValue = Integer.parseInt(byField.getText());
                if (byValue > 0) byValue--;
                byField.setText(String.valueOf(byValue));
            }
        });
    }

    private void bindSommetsRectangle() { // Partie C Question 1
        rectanglePerimeter.xAProperty().bind(axSlider.valueProperty());
        rectanglePerimeter.yAProperty().bind(aySlider.valueProperty());
        Bindings.bindBidirectional(bxField.textProperty(), rectanglePerimeter.xBProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(byField.textProperty(), rectanglePerimeter.yBProperty(), new NumberStringConverter());
        bxField.setText("5");
        byField.setText("2");
    }

    private void bindPerimeterTextField() { // Partie C Question 2
        perimeterTextField.textProperty().bind(rectanglePerimeter.perimeterProperty().asString());
    }

    private void bindWidth1() { // Partie C Question 3
        width1.startXProperty().bind(rectanglePerimeter.xAProperty().multiply(drawRatio));
        width1.startYProperty().bind(rectanglePerimeter.yAProperty().multiply(drawRatio));
        width1.endXProperty().bind(rectanglePerimeter.xBProperty().multiply(drawRatio));
        width1.endYProperty().bind(rectanglePerimeter.yAProperty().multiply(drawRatio));
    }

    private void bindWidth2() { // Partie C Question 3
        width2.startXProperty().bind(rectanglePerimeter.xAProperty().multiply(drawRatio));
        width2.startYProperty().bind(rectanglePerimeter.yBProperty().multiply(drawRatio));
        width2.endXProperty().bind(rectanglePerimeter.xBProperty().multiply(drawRatio));
        width2.endYProperty().bind(rectanglePerimeter.yBProperty().multiply(drawRatio));
    }

    private void bindHeight1() { // Partie C Question 3
        height1.startXProperty().bind(rectanglePerimeter.xAProperty().multiply(drawRatio));
        height1.startYProperty().bind(rectanglePerimeter.yAProperty().multiply(drawRatio));
        height1.endXProperty().bind(rectanglePerimeter.xAProperty().multiply(drawRatio));
        height1.endYProperty().bind(rectanglePerimeter.yBProperty().multiply(drawRatio));
    }

    private void bindHeight2() { // Partie C Question 3
        height2.startXProperty().bind(rectanglePerimeter.xBProperty().multiply(drawRatio));
        height2.startYProperty().bind(rectanglePerimeter.yAProperty().multiply(drawRatio));
        height2.endXProperty().bind(rectanglePerimeter.xBProperty().multiply(drawRatio));
        height2.endYProperty().bind(rectanglePerimeter.yBProperty().multiply(drawRatio));
    }

}
