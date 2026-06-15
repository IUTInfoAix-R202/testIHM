package fr.univ_amu.iut;

import fr.univ_amu.iut.modele.Coup;
import fr.univ_amu.iut.modele.Etat;
import fr.univ_amu.iut.modele.Forme;
import fr.univ_amu.iut.modele.Joueur;
import fr.univ_amu.iut.modele.Partie;
import fr.univ_amu.iut.modele.Piece;
import fr.univ_amu.iut.modele.Plateau;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel de l'IHM Quantik : il enveloppe une {@link Partie} (le modele) et expose son etat sous
 * forme de proprietes observables que la vue peut suivre par liaison.
 *
 * <p>La {@link Partie} n'est pas observable ; apres chaque action ({@link #jouerEn}, {@link
 * #nouvellePartie}) le ViewModel republie l'etat via ses proprietes. La propriete {@link
 * #nombreCoupsProperty} sert de signal de rafraichissement global pour redessiner le plateau.
 */
public class QuantikViewModel {

  private Partie partie = new Partie();

  private final ObjectProperty<Forme> formeSelectionnee = new SimpleObjectProperty<>(null);
  private final ObjectProperty<Joueur> joueurCourant = new SimpleObjectProperty<>(Joueur.BLANC);
  private final ObjectProperty<Etat> etat = new SimpleObjectProperty<>(Etat.EN_COURS);
  private final IntegerProperty nombreCoups = new SimpleIntegerProperty(0);
  private final StringProperty statut = new SimpleStringProperty("");

  /** Cree un ViewModel sur une partie neuve. */
  public QuantikViewModel() {
    rafraichir();
  }

  public ObjectProperty<Forme> formeSelectionneeProperty() {
    return formeSelectionnee;
  }

  public ReadOnlyObjectProperty<Joueur> joueurCourantProperty() {
    return joueurCourant;
  }

  public ReadOnlyObjectProperty<Etat> etatProperty() {
    return etat;
  }

  public ReadOnlyIntegerProperty nombreCoupsProperty() {
    return nombreCoups;
  }

  public ReadOnlyStringProperty statutProperty() {
    return statut;
  }

  public Forme formeSelectionnee() {
    return formeSelectionnee.get();
  }

  public Joueur joueurCourant() {
    return partie.joueurCourant();
  }

  public Etat etat() {
    return partie.etat();
  }

  /**
   * Renvoie la piece occupant une case, ou {@code null}.
   *
   * @param ligne la ligne (0 a 3)
   * @param colonne la colonne (0 a 3)
   * @return la piece presente, ou {@code null}
   */
  public Piece pieceEn(int ligne, int colonne) {
    return partie.plateau().pieceEn(ligne, colonne);
  }

  /**
   * Nombre de pieces d'une forme restant a un joueur.
   *
   * @param joueur le joueur concerne
   * @param forme la forme interrogee
   * @return le nombre d'exemplaires disponibles
   */
  public int compte(Joueur joueur, Forme forme) {
    return partie.reserve(joueur).compte(forme);
  }

  /**
   * Selectionne une forme dans la reserve du joueur courant (s'il lui en reste).
   *
   * @param forme la forme choisie
   */
  public void selectionner(Forme forme) {
    if (partie.etat() == Etat.EN_COURS && compte(joueurCourant(), forme) > 0) {
      formeSelectionnee.set(forme);
    }
  }

  /**
   * Indique si la forme actuellement selectionnee peut etre posee sur une case.
   *
   * @param ligne la ligne (0 a 3)
   * @param colonne la colonne (0 a 3)
   * @return {@code true} si une forme est selectionnee et le coup est legal
   */
  public boolean peutJouerEn(int ligne, int colonne) {
    Forme forme = formeSelectionnee.get();
    return forme != null
        && partie.etat() == Etat.EN_COURS
        && partie.plateau().peutPoser(forme, ligne, colonne);
  }

  /**
   * Joue la forme selectionnee sur une case si le coup est legal.
   *
   * @param ligne la ligne (0 a 3)
   * @param colonne la colonne (0 a 3)
   * @return {@code true} si le coup a ete joue, {@code false} s'il etait illegal
   */
  public boolean jouerEn(int ligne, int colonne) {
    if (!peutJouerEn(ligne, colonne)) {
      return false;
    }
    partie.jouer(formeSelectionnee.get(), ligne, colonne);
    formeSelectionnee.set(null);
    rafraichir();
    return true;
  }

  /**
   * Ensemble des cases ou la forme donnee pourrait etre posee (utile pour previsualiser les coups).
   *
   * @param forme la forme a tester
   * @return l'ensemble des coups legaux pour cette forme
   */
  public Set<Coup> casesValidesPour(Forme forme) {
    Set<Coup> cases = new HashSet<>();
    if (forme == null || partie.etat() != Etat.EN_COURS) {
      return cases;
    }
    for (int ligne = 0; ligne < Plateau.TAILLE; ligne++) {
      for (int colonne = 0; colonne < Plateau.TAILLE; colonne++) {
        if (partie.plateau().peutPoser(forme, ligne, colonne)) {
          cases.add(new Coup(forme, ligne, colonne));
        }
      }
    }
    return cases;
  }

  /** Indique si la partie est terminee. */
  public boolean estTerminee() {
    return partie.etat() != Etat.EN_COURS;
  }

  /**
   * Message decrivant l'issue de la partie.
   *
   * @return un message lisible si la partie est terminee, sinon une chaine vide
   */
  public String messageFin() {
    return switch (partie.etat()) {
      case VICTOIRE_BLANC -> "Victoire du joueur BLANC !";
      case VICTOIRE_NOIR -> "Victoire du joueur NOIR !";
      case EN_COURS -> "";
    };
  }

  /** Remet la partie a zero. */
  public void nouvellePartie() {
    partie = new Partie();
    formeSelectionnee.set(null);
    rafraichir();
  }

  private void rafraichir() {
    joueurCourant.set(partie.joueurCourant());
    etat.set(partie.etat());
    statut.set(calculerStatut());
    // Signal de rafraichissement global : la vue redessine le plateau a chaque incrementation.
    nombreCoups.set(nombreCoups.get() + 1);
  }

  private String calculerStatut() {
    if (partie.etat() != Etat.EN_COURS) {
      return messageFin();
    }
    return "Au tour du joueur " + partie.joueurCourant();
  }
}
