package fr.univ_amu.iut.tictactoe;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;

public class Plateau extends GridPane {
    private final int TAILLE = 3;
    private Case[][] cases;

    private IntegerProperty nombreDeCoupsJoues;
    private BooleanProperty estPartieTerminee;
    private BooleanProperty estMatchNul;
    private BooleanProperty aGagne;

    private ObjectProperty<Joueur> joueurCourant;

    private EventHandler<ActionEvent> caseListener = actionEvent -> {
        Case caseChoisie = (Case) actionEvent.getSource();
        if (caseChoisie.estVide()) {
            caseChoisie.prendre(joueurCourant.get());
            incrementerNombreDeCoupsJoues();
            if ( ! estPartieTerminee.get() )
                joueurSuivant();
        }
    };

    public Plateau() {
        this.joueurCourant = new SimpleObjectProperty<>(Joueur.VIDE);
        this.nombreDeCoupsJoues = new SimpleIntegerProperty(0);
        this.estPartieTerminee = new SimpleBooleanProperty(false);
        this.estMatchNul = new SimpleBooleanProperty(false);
        this.aGagne = new SimpleBooleanProperty(false);

        setHgap(3);
        setVgap(3);

        creerCases();
        nouvellePartie();
        creerBindings();
    }

    private void creerCases() {
        cases = new Case[TAILLE][TAILLE];
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                cases[i][j] = new Case();
                cases[i][j].setOnAction(caseListener);
                add(cases[i][j], i, j);
            }
        }
    }

    public void nouvellePartie() {
        viderPlateau();
        joueurCourant.set(Joueur.CROIX);
        nombreDeCoupsJoues.set(0);
    }


    private void viderPlateau() {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                cases[i][j].vider();
            }
        }
    }

    private void creerBindings() {
        estPartieTerminee.bind(aGagne.or(estMatchNul));
        estMatchNul.bind(nombreDeCoupsJoues.isEqualTo(TAILLE * TAILLE));
        aGagne.bind(auMoinsUneLigneEstGagnante().or(auMoinsUneColonneEstGagnante()).or(auMoinsUneDiagonaleEstGagnante()));
        disableProperty().bind(estPartieTerminee);
    }

    private BooleanProperty estTripletIdentiqueEtNonVide(int i1, int j1, int i2, int j2, int i3, int j3) {
        BooleanProperty diagonalesGagnantes = new SimpleBooleanProperty(false);
        diagonalesGagnantes.bind(cases[i1][j1].possesseurProperty().isEqualTo(cases[i2][j2].possesseurProperty())
                .and(cases[i1][j1].possesseurProperty().isEqualTo(cases[i3][j3].possesseurProperty()))
                .and(cases[i1][j1].possesseurProperty().isNotEqualTo(Joueur.VIDE)));
        return diagonalesGagnantes;
    }

    private BooleanProperty estColonneGagnante(int i) {
        return estTripletIdentiqueEtNonVide(0, i, 1, i, 2, i);
    }

    private BooleanProperty estLigneGagnante(int i) {
        return estTripletIdentiqueEtNonVide(i, 0, i, 1, i, 2);
    }

    private BooleanProperty estDiagonaleMontanteGagnante() {
        return estTripletIdentiqueEtNonVide(0, 0, 1, 1, 2, 2);
    }

    private BooleanProperty estDiagonaleDescendanteGagnante() {
        return estTripletIdentiqueEtNonVide(2, 0, 1, 1, 0, 2);
    }

    private BooleanProperty[] sontDiagonalesGagnantes() {
        BooleanProperty[] diagonalesGagnantes = new BooleanProperty[2];
        diagonalesGagnantes[0] = estDiagonaleMontanteGagnante();
        diagonalesGagnantes[1] = estDiagonaleDescendanteGagnante();
        return diagonalesGagnantes;
    }

    private BooleanProperty[] sontColonneGagnantes() {
        BooleanProperty[] colonnesGagnantes = new BooleanProperty[TAILLE];
        for (int i = 0; i < TAILLE; i++)
            colonnesGagnantes[i] = estColonneGagnante(i);
        return colonnesGagnantes;
    }

    private BooleanProperty[] sontLignesGagnantes() {
        BooleanProperty[] lignesGagnantes = new BooleanProperty[TAILLE];
        for (int i = 0; i < TAILLE; i++)
            lignesGagnantes[i] = estLigneGagnante(i);
        return lignesGagnantes;
    }

    private BooleanBinding auMoinsUneDiagonaleEstGagnante() {
        BooleanProperty[] diagonalesGagnantes = sontDiagonalesGagnantes();
        return diagonalesGagnantes[0].or(diagonalesGagnantes[1]);
    }

    private BooleanBinding auMoinsUneColonneEstGagnante() {
        BooleanProperty[] colonnesGagnantes = sontColonneGagnantes();
        return colonnesGagnantes[0].or(colonnesGagnantes[1]).or(colonnesGagnantes[2]);
    }

    private BooleanBinding auMoinsUneLigneEstGagnante() {
        BooleanProperty[] lignesGagnantes = sontLignesGagnantes();
        return lignesGagnantes[0].or(lignesGagnantes[1]).or(lignesGagnantes[2]);
    }

    private void joueurSuivant() {
        joueurCourant.set(joueurCourant.get().getSuivant());
    }

    private void incrementerNombreDeCoupsJoues() {
        nombreDeCoupsJoues.set(nombreDeCoupsJoues.get() + 1);
    }

    public BooleanProperty aGagneProperty() {
        return aGagne;
    }

    public IntegerProperty nombreDeCoupsJouesProperty() {
        return nombreDeCoupsJoues;
    }

    public BooleanProperty estPartieTermineeProperty() {
        return estPartieTerminee;
    }

    public ObjectProperty<Joueur> joueurCourantProperty() {
        return joueurCourant;
    }

    public BooleanProperty estMatchNulProperty() {
        return estMatchNul;
    }

    /** Accesseur public ajoute pour les tests. */
    public Case getCase(int ligne, int colonne) {
        return cases[ligne][colonne];
    }

    public int getTaille() {
        return TAILLE;
    }
}
