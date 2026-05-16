package fr.univ_amu.iut.utilitaires;

public interface Expression {
    double valeur(double x);
}

class Constante implements Expression {

    public Constante(double v) {
        valeur = v;
    }

    public double valeur(double x) {
        return valeur;
    }

    public String toString() {
        return "" + valeur;
    }

    private double valeur;
}

class Variable implements Expression {

    public Variable() {
    }

    public double valeur(double x) {
        return x;
    }

    public String toString() {
        return "x";
    }
}

abstract class ExpressionBinaire implements Expression {
    protected Expression gauche, droite;

    public ExpressionBinaire(Expression g, Expression d) {
        gauche = g;
        droite = d;
    }
}

abstract class ExpressionUnaire implements Expression {
    protected Expression argument = null;

    public void setArgument(Expression arg) {
        argument = arg;
    }
}

/*
    Expressions Binaires
 */

class Addition extends ExpressionBinaire {

    public Addition(Expression g, Expression d) {
        super(g, d);
    }

    public double valeur(double x) {
        return gauche.valeur(x) + droite.valeur(x);
    }

    public String toString() {
        return gauche + " + " + droite;
    }
}

class Soustraction extends ExpressionBinaire {

    public Soustraction(Expression g, Expression d) {
        super(g, d);
    }

    public double valeur(double x) {
        return gauche.valeur(x) - droite.valeur(x);
    }

    public String toString() {
        return gauche + " - (" + droite + ")";
    }
}

class Multiplication extends ExpressionBinaire {

    public Multiplication(Expression g, Expression d) {
        super(g, d);
    }

    public double valeur(double x) {
        return gauche.valeur(x) * droite.valeur(x);
    }

    public String toString() {
        return "(" + gauche + ") * (" + droite + ")";
    }
}

class Division extends ExpressionBinaire {

    public Division(Expression g, Expression d) {
        super(g, d);
    }

    public double valeur(double x) {
        return gauche.valeur(x) / droite.valeur(x);
    }

    public String toString() {
        return "(" + gauche + ") / (" + droite + ")";
    }
}

/*
    Expressions Unaires
 */

class Cos extends ExpressionUnaire {

    public double valeur(double x) {
        return Math.cos(argument.valeur(x));
    }

    public String toString() {
        return "cos(" + argument + ")";
    }
}

class Sin extends ExpressionUnaire {

    public double valeur(double x) {
        return Math.sin(argument.valeur(x));
    }

    public String toString() {
        return "sin(" + argument + ")";
    }
}

class Exp extends ExpressionUnaire {

    public double valeur(double x) {
        return Math.exp(argument.valeur(x));
    }

    public String toString() {
        return "exp(" + argument + ")";
    }
}

class Log extends ExpressionUnaire {

    public double valeur(double x) {
        return Math.log(argument.valeur(x));
    }

    public String toString() {
        return "log(" + argument + ")";
    }
}
