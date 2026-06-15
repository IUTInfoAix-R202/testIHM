package fr.univ_amu.iut.modele;

/**
 * Une piece du jeu : une {@link Forme} appartenant a un {@link Joueur}.
 *
 * <p>C'est un record : deux pieces sont egales si elles ont la meme forme et le meme proprietaire.
 *
 * @param forme la forme de la piece
 * @param proprietaire le joueur qui possede la piece
 */
public record Piece(Forme forme, Joueur proprietaire) {}
