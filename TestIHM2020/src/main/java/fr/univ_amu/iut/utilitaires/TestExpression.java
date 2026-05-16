package fr.univ_amu.iut.utilitaires;

public class TestExpression {

    public static void main(String[] args) {
        Expression f =
                new Soustraction(
                        new Multiplication(new Variable(), new Variable()),
                        new Constante(1));

        System.out.println("f(x) = " + f);

        double[] v = { -2, -1, 0, 1, 2 };
        for (int i = 0; i < v.length; i++)
            System.out.println("f(" + v[i] + ") = " + f.valeur(v[i]));
    }
}
