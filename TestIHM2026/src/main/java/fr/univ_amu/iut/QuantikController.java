package fr.univ_amu.iut;

import fr.univ_amu.iut.modele.Forme;
import fr.univ_amu.iut.modele.Joueur;
import fr.univ_amu.iut.modele.Piece;
import fr.univ_amu.iut.modele.Plateau;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Controleur de la vue Quantik : il relie les composants graphiques (decrits dans {@code
 * quantikView.fxml}) au {@link QuantikViewModel}.
 */
public class QuantikController {

  private static final PseudoClass SELECTIONNE = PseudoClass.getPseudoClass("selectionne");

  @FXML private Label statutLabel;
  @FXML private javafx.scene.control.Button nouvellePartieBouton;
  @FXML private VBox poolBlanc;
  @FXML private VBox poolNoir;
  @FXML private GridPane plateauGrid;

  private final QuantikViewModel viewModel = new QuantikViewModel();

  private final StackPane[][] cases = new StackPane[Plateau.TAILLE][Plateau.TAILLE];

  @FXML
  private void initialize() {
    statutLabel.textProperty().bind(viewModel.statutProperty());
    nouvellePartieBouton.setOnAction(event -> viewModel.nouvellePartie());

    construirePlateau();
    construireReserves();

    // A chaque coup joue, on redessine le plateau et les reserves.
    viewModel
        .nombreCoupsProperty()
        .addListener(
            (obs, ancien, nouveau) -> {
              rafraichirPlateau();
              rafraichirReserves();
            });
    // En fin de partie, on annonce le resultat.
    viewModel
        .etatProperty()
        .addListener(
            (obs, ancien, nouveau) -> {
              if (viewModel.estTerminee()) {
                annoncerFin();
              }
            });

    rafraichirPlateau();
    rafraichirReserves();
  }

  private void construirePlateau() {
    for (int ligne = 0; ligne < Plateau.TAILLE; ligne++) {
      for (int colonne = 0; colonne < Plateau.TAILLE; colonne++) {
        StackPane cellule = new StackPane();
        cellule.getStyleClass().add("case-vide");
        // Bordures epaisses pour materialiser les quatre zones 2x2.
        if (colonne == 1) {
          cellule.getStyleClass().add("bordure-droite");
        }
        if (ligne == 1) {
          cellule.getStyleClass().add("bordure-bas");
        }
        final int l = ligne;
        final int c = colonne;
        cellule.setOnMouseClicked(event -> clicSurCase(l, c));
        cases[ligne][colonne] = cellule;
        plateauGrid.add(cellule, colonne, ligne);
      }
    }
  }

  private void clicSurCase(int ligne, int colonne) {
    boolean joue = viewModel.jouerEn(ligne, colonne);
    if (!joue) {
      signalerCoupInvalide(cases[ligne][colonne]);
    }
  }

  private void signalerCoupInvalide(StackPane cellule) {
    // Eclair rouge bref pour indiquer un coup interdit.
    Rectangle voile = new Rectangle(70, 70, Color.color(0.9, 0.2, 0.2, 0.5));
    cellule.getChildren().add(voile);
    javafx.animation.PauseTransition pause =
        new javafx.animation.PauseTransition(javafx.util.Duration.millis(250));
    pause.setOnFinished(event -> cellule.getChildren().remove(voile));
    pause.play();
  }

  private void rafraichirPlateau() {
    for (int ligne = 0; ligne < Plateau.TAILLE; ligne++) {
      for (int colonne = 0; colonne < Plateau.TAILLE; colonne++) {
        StackPane cellule = cases[ligne][colonne];
        cellule.getChildren().clear();
        Piece piece = viewModel.pieceEn(ligne, colonne);
        if (piece != null) {
          cellule.getChildren().add(PieceRenderer.rendre(piece));
        }
      }
    }
  }

  private void construireReserves() {
    poolBlanc.getChildren().clear();
    poolNoir.getChildren().clear();
    poolBlanc.getChildren().add(new Label("BLANC"));
    poolNoir.getChildren().add(new Label("NOIR"));
    for (Forme forme : Forme.values()) {
      poolBlanc.getChildren().add(vignetteReserve(Joueur.BLANC, forme));
      poolNoir.getChildren().add(vignetteReserve(Joueur.NOIR, forme));
    }
  }

  private StackPane vignetteReserve(Joueur joueur, Forme forme) {
    StackPane vignette = new StackPane();
    vignette.getStyleClass().add("vignette");
    Shape shape = PieceRenderer.rendre(new Piece(forme, joueur));
    vignette.getChildren().add(shape);
    vignette.setUserData(new Object[] {joueur, forme});
    vignette.setOnMouseClicked(
        event -> {
          if (joueur == viewModel.joueurCourant()) {
            viewModel.selectionner(forme);
            rafraichirReserves();
          }
        });
    return vignette;
  }

  private void rafraichirReserves() {
    rafraichirPool(poolBlanc, Joueur.BLANC);
    rafraichirPool(poolNoir, Joueur.NOIR);
  }

  private void rafraichirPool(VBox pool, Joueur joueur) {
    for (var noeud : pool.getChildren()) {
      if (!(noeud instanceof StackPane vignette) || vignette.getUserData() == null) {
        continue;
      }
      Object[] donnees = (Object[]) vignette.getUserData();
      Forme forme = (Forme) donnees[1];
      int reste = viewModel.compte(joueur, forme);
      vignette.setOpacity(reste == 0 ? 0.3 : 1.0);
      vignette.setDisable(reste == 0);
      boolean choisie =
          joueur == viewModel.joueurCourant() && forme == viewModel.formeSelectionnee();
      vignette.pseudoClassStateChanged(SELECTIONNE, choisie);
    }
  }

  private void annoncerFin() {
    Alert alerte = new Alert(Alert.AlertType.INFORMATION);
    alerte.setTitle("Partie terminee");
    alerte.setHeaderText(viewModel.messageFin());
    alerte.setContentText("Cliquez sur \"Nouvelle partie\" pour rejouer.");
    alerte.show();
  }

  /** Accesseur utilise par les tests pour piloter le ViewModel. */
  QuantikViewModel getViewModel() {
    return viewModel;
  }
}
