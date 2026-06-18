package fr.univ_amu.iut;

import static org.assertj.core.api.Assertions.assertThat;

import fr.univ_amu.iut.modele.Forme;
import fr.univ_amu.iut.modele.Joueur;
import fr.univ_amu.iut.modele.Piece;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

/**
 * Tests de {@link PieceRenderer}.
 *
 * <p>Construire des formes JavaFX ne demande pas de demarrer le moteur graphique : ces tests sont
 * de simples tests unitaires JUnit, sans TestFX.
 */
class PieceRendererTest {

  @Test
  void leCubeEstUnRectangle() {
    assertThat(PieceRenderer.rendre(new Piece(Forme.CUBE, Joueur.BLANC)))
        .isInstanceOf(Rectangle.class);
  }

  @Test
  void laSphereEstUnCercle() {
    assertThat(PieceRenderer.rendre(new Piece(Forme.SPHERE, Joueur.BLANC)))
        .isInstanceOf(Circle.class);
  }

  @Test
  void leCylindreEstUneEllipse() {
    assertThat(PieceRenderer.rendre(new Piece(Forme.CYLINDRE, Joueur.BLANC)))
        .isInstanceOf(Ellipse.class);
  }

  @Test
  void leConeEstUnPolygone() {
    assertThat(PieceRenderer.rendre(new Piece(Forme.CONE, Joueur.BLANC)))
        .isInstanceOf(Polygon.class);
  }

  @Test
  void unePieceBlancheEstColoreeEnBleuClair() {
    assertThat(PieceRenderer.rendre(new Piece(Forme.CUBE, Joueur.BLANC)).getFill())
        .isEqualTo(PieceRenderer.COULEUR_BLANC);
  }

  @Test
  void unePieceNoireEstColoreeEnRougeClair() {
    assertThat(PieceRenderer.rendre(new Piece(Forme.CUBE, Joueur.NOIR)).getFill())
        .isEqualTo(PieceRenderer.COULEUR_NOIR);
  }
}
