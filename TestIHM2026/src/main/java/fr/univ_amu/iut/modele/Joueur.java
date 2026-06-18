package fr.univ_amu.iut.modele;

/** Les deux joueurs de Quantik, distingues par la couleur de leurs pieces. */
public enum Joueur {
  BLANC,
  NOIR;

  /**
   * Renvoie l'autre joueur.
   *
   * @return {@code NOIR} pour {@code BLANC} et inversement
   */
  public Joueur adversaire() {
    return this == BLANC ? NOIR : BLANC;
  }
}
