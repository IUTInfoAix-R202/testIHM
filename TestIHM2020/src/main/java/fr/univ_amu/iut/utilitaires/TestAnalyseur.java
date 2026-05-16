package fr.univ_amu.iut.utilitaires;

public class TestAnalyseur {

  public static void main(String[] args) {
    try {
      Analyseur analyseur = new Analyseur(args[0]);
      Expression expression = analyseur.analyser();
      System.out.println("f(x) = " + expression);

      double xMin = Double.parseDouble(args[1]);
      double xMax = Double.parseDouble(args[2]);

      for (double x = xMin; x < xMax; x++) {
        System.out.println("f(" + x + ") = " + expression.valeur(x));
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
