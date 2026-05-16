package fr.univ_amu.iut.Mastermind;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class Plateau extends GridPane {
    private final int nombrePionsParRangée;
    private final int nombreRangées;

    private Rangée[] rangées;
    private MarquageScore[] scores;
    private Rangée rangéeSecrète;
    private Combinaison combinaisonSecrète;


    private IntegerProperty nombreDeCoupsJoués;

    private BooleanProperty aGagné;
    private BooleanProperty aPerdu;
    private BooleanProperty estPartieTerminée;

    public Plateau() {
        this.nombreRangées = MasterMindControleur.NB_RANGEES;
        this.nombrePionsParRangée = MasterMindControleur.NB_PIONS_PAR_RANGEES;

        this.rangées = new Rangée[nombreRangées];
        this.scores = new MarquageScore[nombreRangées];
        this.rangéeSecrète = creerRangéeSecrète();
        add(rangéeSecrète, 0, 0, 2, 1);
        setHalignment(rangéeSecrète, HPos.CENTER);
        setHgap(20);
        setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.NEVER);
        column1.setHalignment(HPos.LEFT);
        getColumnConstraints().add(column1);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(Priority.ALWAYS);
        column2.setHalignment(HPos.RIGHT);
        getColumnConstraints().add(column2);

        this.nombreDeCoupsJoués = new SimpleIntegerProperty(0);
        this.aGagné = new SimpleBooleanProperty(false);
        this.aPerdu = new SimpleBooleanProperty(false);
        this.estPartieTerminée = new SimpleBooleanProperty(false);

        creerBindings();
        remplirRangéesEtMarquages();
        nouvellePartie();
    }

    private Rangée creerRangéeSecrète() {
        Rangée rangéeSecrète = new Rangée();
        générerNouvelleRangéeSecrète(rangéeSecrète);
        return rangéeSecrète;
    }

    private void générerNouvelleRangéeSecrète(Rangée rangéeSecrète) {
        combinaisonSecrète = Combinaison.générerCombinaisonAléatoire();
        rangéeSecrète.setCombinaison(combinaisonSecrète);
        rangéeSecrète.setMasquée(true);
    }

    private void creerBindings() {
        aPerdu.bind(nombreDeCoupsJoués.isEqualTo(nombreRangées));
        estPartieTerminée.bind(aGagné.or(aPerdu));

        estPartieTerminée.addListener((observable, oldValue, newValue) -> {
            if (newValue)
                rangéeSecrète.setMasquée(false);
        });
    }

    private void remplirRangéesEtMarquages() {
        for (int i = nombreRangées - 1; i >= 0; i--) {
            rangées[i] = new Rangée();
            scores[i] = new MarquageScore();
            add(rangées[i], 0, i + 1);
            add(scores[i], 1, i + 1);
        }
    }

    public IntegerProperty nombreDeCoupsJouésProperty() {
        return nombreDeCoupsJoués;
    }

    public BooleanProperty estPartieTerminéeProperty() {
        return estPartieTerminée;
    }

    public int getNombreDeCoupsJoués() {
        return nombreDeCoupsJoués.get();
    }

    public void viderRangéeCourante() {
        if (!estPartieTerminée.get())
            rangées[getNombreDeCoupsJoués()].vider();
    }

    public void nouvellePartie() {
        for (int i = 0; i < nombreRangées; i++) {
            rangées[i].vider();
            scores[i].vider();
            rangées[i].setActivée(false);
        }
        rangées[0].setActivée(true);
        générerNouvelleRangéeSecrète(rangéeSecrète);
    }

    public Combinaison combinaisonCourante() {
        return rangées[getNombreDeCoupsJoués()].getCombinaison();
    }

    public void validerRangéeCourante() {
        int nombrePionsBiensPlacés = calculerNombrePionsBiensPlacés(combinaisonSecrète);
        int nombrePionsMalsPlacés = calculerNombrePionsMalsPlacés(combinaisonSecrète);
        rangées[getNombreDeCoupsJoués()].setActivée(false);
        scores[getNombreDeCoupsJoués()].setScore(nombrePionsBiensPlacés, nombrePionsMalsPlacés);
        nombreDeCoupsJoués.set(nombreDeCoupsJoués.get() + 1);
        if (nombrePionsBiensPlacés == nombrePionsParRangée) {
            aGagné.set(true);
        } else if (getNombreDeCoupsJoués() < nombreRangées) {
            rangées[getNombreDeCoupsJoués()].setActivée(true);
        }
    }

    private int calculerNombrePionsMalsPlacés(Combinaison combinaisonSecrète) {
        int nombrePionsMalsPlacés = 0;
        for (int i = 0; i < combinaisonSecrète.getNombrePions(); i++)
            if (combinaisonSecrète.getPion(i) != combinaisonCourante().getPion(i)
                    && combinaisonSecrète.contient(combinaisonCourante().getPion(i)))
                nombrePionsMalsPlacés++;
        return nombrePionsMalsPlacés;
    }

    private int calculerNombrePionsBiensPlacés(Combinaison combinaisonSecrète) {
        int nombrePionsBiensPlacés = 0;
        for (int i = 0; i < combinaisonSecrète.getNombrePions(); i++)
            if (combinaisonSecrète.getPion(i) == combinaisonCourante().getPion(i))
                nombrePionsBiensPlacés++;
        return nombrePionsBiensPlacés;
    }

    public boolean aGagné() {
        return aGagné.get();
    }
}
