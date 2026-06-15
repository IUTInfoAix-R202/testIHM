package fr.univ_amu.iut.modele;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Une partie de Quantik : le plateau, les deux reserves, le joueur courant et l'etat d'avancement.
 *
 * <p>Le joueur BLANC commence. Un coup consiste a prendre une piece dans sa reserve et a la poser
 * sur une case legale. La partie se termine quand un joueur complete un alignement (il gagne) ou
 * quand l'adversaire ne peut plus jouer (le joueur courant gagne).
 */
public class Partie {

  private final Plateau plateau = new Plateau();
  private final Map<Joueur, Reserve> reserves = new EnumMap<>(Joueur.class);
  private Joueur joueurCourant = Joueur.BLANC;
  private Etat etat = Etat.EN_COURS;

  /** Cree une nouvelle partie : plateau vide, reserves pleines, c'est au BLANC de jouer. */
  public Partie() {
    reserves.put(Joueur.BLANC, new Reserve(Joueur.BLANC));
    reserves.put(Joueur.NOIR, new Reserve(Joueur.NOIR));
  }

  public Plateau plateau() {
    return plateau;
  }

  public Joueur joueurCourant() {
    return joueurCourant;
  }

  public Etat etat() {
    return etat;
  }

  /**
   * Renvoie la reserve d'un joueur.
   *
   * @param joueur le joueur dont on veut la reserve
   * @return la reserve correspondante
   */
  public Reserve reserve(Joueur joueur) {
    return reserves.get(joueur);
  }

  /**
   * Liste tous les coups legaux d'un joueur.
   *
   * <p>Un coup est legal si le joueur possede encore au moins une piece de la forme et si cette
   * forme peut etre posee sur la case (voir {@link Plateau#peutPoser}).
   *
   * @param joueur le joueur dont on enumere les coups
   * @return la liste (eventuellement vide) des coups jouables
   */
  public List<Coup> coupsPossibles(Joueur joueur) {
    List<Coup> coups = new ArrayList<>();
    Reserve reserve = reserves.get(joueur);
    for (Forme forme : Forme.values()) {
      if (reserve.compte(forme) == 0) {
        continue;
      }
      for (int ligne = 0; ligne < Plateau.TAILLE; ligne++) {
        for (int colonne = 0; colonne < Plateau.TAILLE; colonne++) {
          if (plateau.peutPoser(forme, ligne, colonne)) {
            coups.add(new Coup(forme, ligne, colonne));
          }
        }
      }
    }
    return coups;
  }

  /**
   * Joue un coup pour le joueur courant.
   *
   * <p>La methode verifie d'abord la legalite du coup, puis pose la piece. Si la pose complete un
   * alignement, le joueur courant gagne. Sinon, si l'adversaire n'a plus aucun coup possible, le
   * joueur courant gagne egalement (blocage). Dans les autres cas, c'est au tour de l'adversaire.
   *
   * @param forme la forme a poser (prise dans la reserve du joueur courant)
   * @param ligne la ligne de la case (0 a 3)
   * @param colonne la colonne de la case (0 a 3)
   * @throws IllegalStateException si la partie est deja terminee
   * @throws IllegalArgumentException si la forme est epuisee ou si la case est interdite
   */
  public void jouer(Forme forme, int ligne, int colonne) {
    if (etat != Etat.EN_COURS) {
      throw new IllegalStateException("La partie est terminee");
    }
    Reserve reserve = reserves.get(joueurCourant);
    if (reserve.compte(forme) == 0) {
      throw new IllegalArgumentException("Forme " + forme + " epuisee pour " + joueurCourant);
    }
    if (!plateau.peutPoser(forme, ligne, colonne)) {
      throw new IllegalArgumentException("Coup interdit en (" + ligne + ", " + colonne + ")");
    }

    plateau.poser(reserve.prendre(forme), ligne, colonne);

    if (plateau.estVictoireApres(ligne, colonne)) {
      etat = victoirePour(joueurCourant);
      return;
    }
    Joueur adversaire = joueurCourant.adversaire();
    if (coupsPossibles(adversaire).isEmpty()) {
      etat = victoirePour(joueurCourant);
      return;
    }
    joueurCourant = adversaire;
  }

  private static Etat victoirePour(Joueur joueur) {
    return joueur == Joueur.BLANC ? Etat.VICTOIRE_BLANC : Etat.VICTOIRE_NOIR;
  }
}
