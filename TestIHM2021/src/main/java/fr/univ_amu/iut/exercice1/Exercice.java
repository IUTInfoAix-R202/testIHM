package fr.univ_amu.iut.exercice1;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Random;

public class Exercice {
    private String enonce;
    private IntegerProperty solution;

    public Exercice() {
        Random random = new Random();
        int choice = random.nextInt(4);

        int operande1;
        int operande2;
        String symboleOperateur;
        if (choice == 0) {
            operande1 = random.nextInt(26);
            operande2 = random.nextInt(26);
            solution = new SimpleIntegerProperty(operande1 + operande2);
            symboleOperateur = "+";
        } else if (choice == 1) {
            operande1 = random.nextInt(16) + 10;
            operande2 = random.nextInt(26);
            solution = new SimpleIntegerProperty(operande1 - operande2);
            symboleOperateur = "-";
        } else if (choice == 2) {
            operande1 = random.nextInt(15) + 1;
            operande2 = random.nextInt(15) + 1;
            solution = new SimpleIntegerProperty(operande1 * operande2);
            symboleOperateur = "*";
        } else {
            operande1 = random.nextInt(151);
            operande2 = random.nextInt(15) + 1;
            solution = new SimpleIntegerProperty(operande1 / operande2);
            symboleOperateur = "/";
        }
        enonce = new String(operande1 + " " + symboleOperateur + " " + operande2 + " = ");
    }

    public String getEnonce() {
        return enonce;
    }

    public int getSolution() {
        return solution.get();
    }

}
