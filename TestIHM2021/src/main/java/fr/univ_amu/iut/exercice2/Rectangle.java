package fr.univ_amu.iut.exercice2;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import static javafx.beans.binding.Bindings.*;

public class Rectangle {

    // Partie A Question 1 (2 points)
    private IntegerProperty xA;
    private IntegerProperty yA;

    private IntegerProperty xB;
    private IntegerProperty yB;

    private IntegerProperty perimeter;

    public IntegerProperty xAProperty() {
        return xA;
    }

    public IntegerProperty yAProperty() {
        return yA;
    }

    public IntegerProperty xBProperty() {
        return xB;
    }

    public IntegerProperty yBProperty() {
        return yB;
    }

    public IntegerProperty perimeterProperty() {
        return perimeter;
    }

    public Rectangle() {
        xA = new SimpleIntegerProperty(0);
        yA = new SimpleIntegerProperty(0);
        xB = new SimpleIntegerProperty(0);
        yB = new SimpleIntegerProperty(0);
        perimeter = new SimpleIntegerProperty(0);

        createBinding();
    }
    // Fin Partie A Question 1

    // Partie A Question 2 (3 points)
    private void createBinding() {
        NumberBinding width = when(lessThan(xA, xB)).then(subtract(xB, xA)).otherwise(subtract(xA, xB));
        NumberBinding height = when(lessThan(yA, yB)).then(subtract(yB, yA)).otherwise(subtract(yA, yB));
        perimeter.bind(multiply(add(width, height), 2.0));
    }
    // Fin Partie A Question 2
}
