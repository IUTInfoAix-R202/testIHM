package fr.univ_amu.iut.modele;

/**
 * Les quatre formes de pieces du jeu Quantik.
 *
 * <p>Chaque joueur possede deux exemplaires de chacune de ces formes (voir {@link Reserve}). La
 * regle du jeu ne distingue jamais les formes par leur proprietaire : une ligne, une colonne ou une
 * zone est gagnante des qu'elle reunit les quatre formes differentes.
 */
public enum Forme {
  CUBE,
  SPHERE,
  CYLINDRE,
  CONE
}
