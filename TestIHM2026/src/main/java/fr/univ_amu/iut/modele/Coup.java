package fr.univ_amu.iut.modele;

/**
 * Un coup jouable : poser une piece d'une certaine {@link Forme} sur une case du plateau.
 *
 * @param forme la forme a poser
 * @param ligne la ligne de la case (0 a 3)
 * @param colonne la colonne de la case (0 a 3)
 */
public record Coup(Forme forme, int ligne, int colonne) {}
