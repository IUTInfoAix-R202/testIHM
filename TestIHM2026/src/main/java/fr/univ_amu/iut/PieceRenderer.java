package fr.univ_amu.iut;

import fr.univ_amu.iut.modele.Joueur;
import fr.univ_amu.iut.modele.Piece;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Fabrique les representations graphiques des pieces de Quantik.
 *
 * <p>A chaque {@link fr.univ_amu.iut.modele.Forme} correspond une forme JavaFX differente, et a
 * chaque {@link Joueur} une couleur de remplissage.
 */
public final class PieceRenderer {

  /** Couleur des pieces du joueur BLANC. */
  public static final Color COULEUR_BLANC = Color.LIGHTBLUE;

  /** Couleur des pieces du joueur NOIR. */
  public static final Color COULEUR_NOIR = Color.LIGHTCORAL;

  private PieceRenderer() {}

  /**
   * Construit la forme JavaFX representant une piece.
   *
   * <ul>
   *   <li>CUBE : un {@link Rectangle}
   *   <li>SPHERE : un {@link Circle}
   *   <li>CYLINDRE : une {@link Ellipse}
   *   <li>CONE : un {@link Polygon} triangulaire
   * </ul>
   *
   * @param piece la piece a representer
   * @return une forme JavaFX coloree selon le proprietaire
   */
  public static Shape rendre(Piece piece) {
    Shape forme =
        switch (piece.forme()) {
          case CUBE -> new Rectangle(40, 40);
          case SPHERE -> new Circle(20);
          case CYLINDRE -> new Ellipse(20, 13);
          case CONE -> new Polygon(20.0, 2.0, 38.0, 38.0, 2.0, 38.0);
        };
    forme.setFill(piece.proprietaire() == Joueur.BLANC ? COULEUR_BLANC : COULEUR_NOIR);
    forme.setStroke(Color.web("#2c3e50"));
    forme.setStrokeWidth(2);
    forme.getStyleClass().add("piece");
    return forme;
  }
}
