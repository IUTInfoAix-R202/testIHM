package fr.univ_amu.iut.modele;

/** Etat d'avancement d'une {@link Partie}. */
public enum Etat {
  /** La partie est en cours, aucun joueur n'a encore gagne. */
  EN_COURS,
  /** Le joueur BLANC a gagne (alignement complete ou adversaire bloque). */
  VICTOIRE_BLANC,
  /** Le joueur NOIR a gagne (alignement complete ou adversaire bloque). */
  VICTOIRE_NOIR
}
