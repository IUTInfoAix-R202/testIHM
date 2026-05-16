package fr.univ_amu.iut.taquin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaquinBoard extends GridPane {
    public static final int TAILLE_CASE = 100;
    private List<Carreau> carreaux;
    private int taille;

    private Carreau carreauVide;

    private BooleanProperty estPartieTerminee;

    private IntegerProperty nombreDeMouvement;

    private final EventHandler<ActionEvent> ecouteurDeCarreau = event -> {
        Carreau carreau = (Carreau) event.getSource();
        System.out.println("Déplacement de "+ carreau);
        deplacer(carreau);
        verifierFinDePartie();
    };

    public TaquinBoard(int taille) {
        this.taille = taille;
        estPartieTerminee = new SimpleBooleanProperty(false);
        nombreDeMouvement = new SimpleIntegerProperty(0);
        carreaux = new ArrayList<>(taille*taille);
        initCarreaux();
    }

    public BooleanProperty estPartieTermineeProperty() {
        return estPartieTerminee;
    }

    private void initCarreaux() {
        int numeroCarreau = 0;
        for (int row = 0; row < taille; row++) {
            for (int col = 0; col < taille; col++) {
                Carreau carreau = new Carreau(++numeroCarreau, new Position(col,row));
                this.add(carreau, col, row);
                carreau.setOnAction(ecouteurDeCarreau);
                carreaux.add(carreau);
            }
        }

        initCarreauVide();
    }

    private void initCarreauVide() {
        carreauVide = carreaux.get(taille*taille-1);
        carreauVide.setText("");
        carreauVide.setDisable(true);
    }

    void nouvellePartie() {
        melanger();
        estPartieTerminee.set(false);
        nombreDeMouvement.set(0);
    }

    private void deplacer(Carreau carreau){
        Position positionCarreau = carreau.getPosition();
        Position positionVide = carreauVide.getPosition();

        Position positionDroite = new Position(positionCarreau.getX() + 1,
                                                  positionCarreau.getY());
        Position positionGauche = new Position(positionCarreau.getX() - 1,
                                                  positionCarreau.getY());
        Position positionHaut = new Position(positionCarreau.getX(),
                                                  positionCarreau.getY()+1);
        Position positionBas = new Position(positionCarreau.getX(),
                                                  positionCarreau.getY()-1);

        if(positionGauche.equals(positionVide)||
                positionDroite.equals(positionVide)||
                positionHaut.equals(positionVide)||
                positionBas.equals(positionVide)){
            permuter(carreau, carreauVide);
        }
    }

    private void placer(Carreau carreau, Position position) {
        System.out.println(carreau + " se déplace en "+position);
        carreau.setPosition(position);
        GridPane.setConstraints(carreau, carreau.getPosition().getX(), carreau.getPosition().getY());
    }

    private void permuter(Carreau carreau1, Carreau carreau2) {
        Position position1 = carreau1.getPosition();
        Position position2 = carreau2.getPosition();

        placer(carreau1, position2);
        placer(carreau2, position1);

        nombreDeMouvement.set(nombreDeMouvement.get() + 1);
    }

    private void verifierFinDePartie() {
        boolean estDansLOrdre = true;
        for (Carreau carreau : carreaux) {
            if (!carreau.estBienPlace())
                estDansLOrdre = false;
        }

        estPartieTerminee.set(estDansLOrdre);
    }

    private void melanger() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < taille * taille * 100; i++) {
            int indiceAleatoire = random.nextInt(taille * taille);
            deplacer(carreaux.get(indiceAleatoire));
        }
    }

    public int getNombreDeMouvement() {
        return nombreDeMouvement.get();
    }

    public IntegerProperty nombreDeMouvementProperty() {
        return nombreDeMouvement;
    }
}
