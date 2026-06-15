/**
 * Module JavaFX du Test d'IHM 2026 - Quantik.
 *
 * <p>Le paquet {@code modele} contient toute la logique du jeu (sujet R2.03) ; le paquet racine
 * contient l'IHM JavaFX (sujet R2.02). Le module est ouvert pour autoriser FXMLLoader a injecter
 * les champs {@code @FXML} par reflexion.
 */
open module quantik {
  // Dependances JavaFX
  requires transitive javafx.base;
  requires transitive javafx.controls;
  requires transitive javafx.graphics;
  requires transitive javafx.fxml;

  // Logique du jeu (R2.03)
  exports fr.univ_amu.iut.modele;

  // IHM JavaFX (R2.02)
  exports fr.univ_amu.iut;
}
