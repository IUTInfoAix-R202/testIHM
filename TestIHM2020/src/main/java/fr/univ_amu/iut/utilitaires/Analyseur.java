package fr.univ_amu.iut.utilitaires;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

public class Analyseur {

    private StreamTokenizer lexical;

    public Analyseur(String texte)  {
        lexical = new StreamTokenizer(new StringReader(texte));
        lexical.ordinaryChar('/');
        lexical.ordinaryChar('-');
    }

    public Expression analyser() throws IOException, ErreurDeSyntaxe {
        lexical.nextToken();
        Expression resultat = analyserExpression();
        if (lexical.ttype != StreamTokenizer.TT_EOF)
            throw new ErreurDeSyntaxe("caractère inattendu à la fin du texte");
        return resultat;
    }

    /* La suite ne vous est d'aucune utilité */

    private Expression analyserExpression()
            throws IOException, ErreurDeSyntaxe {

        boolean negatif = false;
        if (lexical.ttype == '-') {
            negatif = true;
            lexical.nextToken();
        }

        Expression resultat = analyserTerme();

        if (negatif)
            resultat = new Soustraction(new Constante(0), resultat);

        while (lexical.ttype == '+' || lexical.ttype == '-') {
            boolean estUneAddition = lexical.ttype == '+';
            lexical.nextToken();
            Expression terme = analyserTerme();
            if (estUneAddition)
                resultat = new Addition(resultat, terme);
            else
                resultat = new Soustraction(resultat, terme);
        }

        return resultat;
    }

    private Expression analyserTerme() throws IOException, ErreurDeSyntaxe {

        Expression resultat = analyserFacteur();

        while (lexical.ttype == '*' || lexical.ttype == '/') {
            boolean estUnProduit = lexical.ttype == '*';
            lexical.nextToken();
            Expression facteur = analyserFacteur();
            if (estUnProduit)
                resultat = new Multiplication(resultat, facteur);
            else
                resultat = new Division(resultat, facteur);
        }

        return resultat;
    }

    private Expression analyserFacteur() throws IOException, ErreurDeSyntaxe {
        Expression resultat = null;

        if (lexical.ttype == StreamTokenizer.TT_NUMBER) {
            resultat = new Constante(lexical.nval);
            lexical.nextToken();

        } else if (lexical.ttype == StreamTokenizer.TT_WORD) {
            String s = lexical.sval.toLowerCase();

            if (s.equals("x")) {
                resultat = new Variable();
                lexical.nextToken();

            } else {
                if (s.equals("sin"))
                    resultat = new Sin();
                else if (s.equals("cos"))
                    resultat = new Cos();
                else if (s.equals("exp"))
                    resultat = new Exp();
                else if (s.equals("log"))
                    resultat = new Log();
                else
                    throw new ErreurDeSyntaxe(s + " : identificateur non reconnu");

                lexical.nextToken();
                if (lexical.ttype != '(')
                    throw new ErreurDeSyntaxe("( attendue");
                lexical.nextToken();
                ((ExpressionUnaire) resultat).setArgument(analyserExpression());
                if (lexical.ttype != ')')
                    throw new ErreurDeSyntaxe(") attendue");
                lexical.nextToken();
            }

        } else if (lexical.ttype == '(') {
            lexical.nextToken();
            resultat = analyserExpression();
            if (lexical.ttype != ')')
                throw new ErreurDeSyntaxe(") attendue");
            lexical.nextToken();

        } else if (lexical.ttype == StreamTokenizer.TT_EOF)
            throw new ErreurDeSyntaxe("fin du texte inattendue");

        else
            throw new ErreurDeSyntaxe(
                    ((char) lexical.ttype) + " : symbole inattendu");

        return resultat;
    }

}