package fr.univ_amu.iut.tictactoe;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public enum Joueur {

  VIDE("vide.png", "Aucun", Color.RED) {
      public Joueur getSuivant() {
        return VIDE;
      }
    },

  CROIX("croix.png", "Croix", Color.BLUE) {
      public Joueur getSuivant() {
        return ROND;
      }
    },

  ROND("rond.png", "Rond", Color.GREEN) {
      public Joueur getSuivant() {
        return CROIX;
      }
    };

  private Image image;
  private Color couleur;
  private String nom;

  Joueur(String fileName, String nom, Color couleur) {
    this.image   = new Image("assets/" + fileName);
    this.couleur = couleur;
    this.nom = nom;
  }

  public abstract Joueur getSuivant();

  public Image getImage() { return this.image; }

  public Color getCouleur() { return this.couleur; }

  @Override public String toString() { return nom; }
}
