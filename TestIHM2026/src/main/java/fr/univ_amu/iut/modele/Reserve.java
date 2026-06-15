package fr.univ_amu.iut.modele;

import java.util.EnumMap;
import java.util.Map;

/**
 * La reserve de pieces d'un joueur.
 *
 * <p>Au debut de la partie, chaque joueur dispose de huit pieces : deux exemplaires de chacune des
 * quatre {@link Forme}. Quand un joueur pose une piece, il la retire de sa reserve.
 */
public class Reserve {

  private final Joueur proprietaire;
  private final Map<Forme, Integer> stock = new EnumMap<>(Forme.class);

  /**
   * Cree une reserve pleine pour un joueur : deux pieces de chaque forme.
   *
   * @param proprietaire le joueur a qui appartiennent les pieces
   */
  public Reserve(Joueur proprietaire) {
    this.proprietaire = proprietaire;
    for (Forme forme : Forme.values()) {
      stock.put(forme, 2);
    }
  }

  public Joueur proprietaire() {
    return proprietaire;
  }

  /**
   * Nombre de pieces d'une forme encore disponibles.
   *
   * @param forme la forme interrogee
   * @return le nombre d'exemplaires restants (0, 1 ou 2)
   */
  public int compte(Forme forme) {
    return stock.get(forme);
  }

  /**
   * Indique si la reserve est totalement vide.
   *
   * @return {@code true} si plus aucune piece n'est disponible
   */
  public boolean estVide() {
    return stock.values().stream().allMatch(n -> n == 0);
  }

  /**
   * Retire et renvoie une piece de la forme demandee.
   *
   * @param forme la forme a prendre
   * @return la piece retiree, appartenant au proprietaire de la reserve
   * @throws IllegalArgumentException si aucune piece de cette forme n'est disponible
   */
  public Piece prendre(Forme forme) {
    if (compte(forme) == 0) {
      throw new IllegalArgumentException("Plus de piece de forme " + forme + " dans la reserve");
    }
    stock.put(forme, compte(forme) - 1);
    return new Piece(forme, proprietaire);
  }
}
