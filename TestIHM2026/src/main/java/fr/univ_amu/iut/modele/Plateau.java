package fr.univ_amu.iut.modele;

import java.util.EnumSet;
import java.util.Set;

/**
 * Le plateau de Quantik : une grille 4x4 decoupee en quatre zones de 2x2.
 *
 * <pre>
 *      c0  c1 | c2  c3
 *    +--------+--------+
 * l0 | z0  z0 | z1  z1 |
 * l1 | z0  z0 | z1  z1 |
 *    +--------+--------+
 * l2 | z2  z2 | z3  z3 |
 * l3 | z2  z2 | z3  z3 |
 *    +--------+--------+
 * </pre>
 *
 * <p>La regle de pose est la contrainte centrale du jeu : une forme ne peut etre posee sur une case
 * que si cette forme n'est pas deja presente sur la meme ligne, la meme colonne ou la meme zone,
 * <strong>quel que soit le proprietaire</strong> des pieces deja en place. Un alignement (ligne,
 * colonne ou zone) est gagnant des qu'il reunit les quatre formes differentes.
 */
public class Plateau {

  /** Cote du plateau (et nombre de formes a reunir pour gagner). */
  public static final int TAILLE = 4;

  private final Piece[][] cases = new Piece[TAILLE][TAILLE];

  /**
   * Indique si une case est vide.
   *
   * @param ligne la ligne (0 a 3)
   * @param colonne la colonne (0 a 3)
   * @return {@code true} si aucune piece n'occupe la case
   */
  public boolean estVide(int ligne, int colonne) {
    return cases[ligne][colonne] == null;
  }

  /**
   * Renvoie la piece occupant une case, ou {@code null} si la case est vide.
   *
   * @param ligne la ligne (0 a 3)
   * @param colonne la colonne (0 a 3)
   * @return la piece presente, ou {@code null}
   */
  public Piece pieceEn(int ligne, int colonne) {
    return cases[ligne][colonne];
  }

  /**
   * Renvoie l'indice (0 a 3) de la zone 2x2 contenant une case.
   *
   * @param ligne la ligne (0 a 3)
   * @param colonne la colonne (0 a 3)
   * @return l'indice de la zone
   */
  public static int zoneDe(int ligne, int colonne) {
    return 2 * (ligne / 2) + (colonne / 2);
  }

  private boolean formePresenteSurLigne(Forme forme, int ligne) {
    for (int c = 0; c < TAILLE; c++) {
      Piece piece = cases[ligne][c];
      if (piece != null && piece.forme() == forme) {
        return true;
      }
    }
    return false;
  }

  private boolean formePresenteSurColonne(Forme forme, int colonne) {
    for (int l = 0; l < TAILLE; l++) {
      Piece piece = cases[l][colonne];
      if (piece != null && piece.forme() == forme) {
        return true;
      }
    }
    return false;
  }

  private boolean formePresenteDansZone(Forme forme, int ligne, int colonne) {
    int zone = zoneDe(ligne, colonne);
    for (int l = 0; l < TAILLE; l++) {
      for (int c = 0; c < TAILLE; c++) {
        if (zoneDe(l, c) == zone) {
          Piece piece = cases[l][c];
          if (piece != null && piece.forme() == forme) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Indique si une forme peut etre legalement posee sur une case.
   *
   * <p>La pose est autorisee si la case est vide et qu'aucune des trois contraintes (ligne,
   * colonne, zone) n'interdit cette forme.
   *
   * @param forme la forme a poser
   * @param ligne la ligne (0 a 3)
   * @param colonne la colonne (0 a 3)
   * @return {@code true} si le coup est legal
   */
  public boolean peutPoser(Forme forme, int ligne, int colonne) {
    return estVide(ligne, colonne)
        && !formePresenteSurLigne(forme, ligne)
        && !formePresenteSurColonne(forme, colonne)
        && !formePresenteDansZone(forme, ligne, colonne);
  }

  /**
   * Pose une piece sur le plateau.
   *
   * @param piece la piece a poser
   * @param ligne la ligne (0 a 3)
   * @param colonne la colonne (0 a 3)
   * @throws IllegalArgumentException si le coup n'est pas legal (voir {@link #peutPoser})
   */
  public void poser(Piece piece, int ligne, int colonne) {
    if (!peutPoser(piece.forme(), ligne, colonne)) {
      throw new IllegalArgumentException("Coup interdit en (" + ligne + ", " + colonne + ")");
    }
    cases[ligne][colonne] = piece;
  }

  /**
   * Indique si quatre cases forment un alignement gagnant.
   *
   * <p>Un alignement est gagnant s'il est complet (aucune case vide) et qu'il reunit les quatre
   * formes differentes, sans tenir compte du proprietaire des pieces.
   *
   * @param quatre les quatre pieces d'une ligne, colonne ou zone (certaines pouvant etre {@code
   *     null})
   * @return {@code true} si les quatre formes sont presentes et distinctes
   */
  public static boolean estAlignementComplet(Piece[] quatre) {
    Set<Forme> formes = EnumSet.noneOf(Forme.class);
    for (Piece piece : quatre) {
      if (piece == null) {
        return false;
      }
      formes.add(piece.forme());
    }
    return formes.size() == TAILLE;
  }

  private Piece[] ligne(int ligne) {
    Piece[] resultat = new Piece[TAILLE];
    for (int c = 0; c < TAILLE; c++) {
      resultat[c] = cases[ligne][c];
    }
    return resultat;
  }

  private Piece[] colonne(int colonne) {
    Piece[] resultat = new Piece[TAILLE];
    for (int l = 0; l < TAILLE; l++) {
      resultat[l] = cases[l][colonne];
    }
    return resultat;
  }

  private Piece[] zone(int zone) {
    Piece[] resultat = new Piece[TAILLE];
    int index = 0;
    for (int l = 0; l < TAILLE; l++) {
      for (int c = 0; c < TAILLE; c++) {
        if (zoneDe(l, c) == zone) {
          resultat[index++] = cases[l][c];
        }
      }
    }
    return resultat;
  }

  /**
   * Indique si la pose en (ligne, colonne) vient de completer un alignement gagnant.
   *
   * <p>On verifie uniquement la ligne, la colonne et la zone de la derniere case jouee : ce sont
   * les seuls alignements susceptibles d'avoir change.
   *
   * @param ligne la ligne de la derniere piece posee (0 a 3)
   * @param colonne la colonne de la derniere piece posee (0 a 3)
   * @return {@code true} si un alignement gagnant existe sur cette ligne, cette colonne ou cette
   *     zone
   */
  public boolean estVictoireApres(int ligne, int colonne) {
    return estAlignementComplet(ligne(ligne))
        || estAlignementComplet(colonne(colonne))
        || estAlignementComplet(zone(zoneDe(ligne, colonne)));
  }
}
