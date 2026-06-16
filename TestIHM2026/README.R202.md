# Test d'IHM et langage Java

### Test du jeudi 18 juin 2026 - Durée 2 heures - Documents non autorisés

L'objectif de ce sujet est la programmation d'une version JavaFX du jeu **Quantik**.

**Quantik** est un jeu de stratégie abstrait édité par Gigamic (auteur : Nouredine Hilal, 2019). Deux
joueurs s'affrontent sur un plateau de 4x4 cases, découpé en quatre zones de 2x2. Chaque joueur
dispose de huit pièces : deux exemplaires de chacune des quatre formes (cube, sphère, cylindre, cône).

### Description du jeu

On pose à tour de rôle une pièce sur une case vide en respectant une seule règle : interdit de poser
une forme sur une ligne, une colonne ou une zone qui contient déjà cette forme (peu importe le
joueur). Gagne celui qui pose la pièce complétant une ligne, une colonne ou une zone réunissant les
quatre formes différentes.

L'IHM que vous allez réaliser ressemble à ceci : une grille 4x4 au centre (avec des bordures épaisses
pour séparer les quatre zones), la réserve du joueur BLANC à gauche, celle du joueur NOIR à droite, et
un bandeau de statut en haut.

![Apercu de l'application Quantik que vous allez realiser](src/main/resources/assets/quantik_screenshot.png)

### Travail à réaliser

L'objectif de ce sujet est d'évaluer votre capacité à écrire une IHM en Java avec JavaFX. La logique
du jeu (le "modèle") **vous est fournie** : c'est exactement le code demandé dans le sujet R2.03,
livré dans le paquet `fr.univ_amu.iut.modele`. Les méthodes trop algorithmiques sont donc déjà
écrites. Vous pourrez retrouver une proposition de correction à l'adresse suivante :
<https://github.com/IUTInfoAix-R202/TestIHM2026/>.

L'application définit plusieurs types d'objets :

- `QuantikController` est le contrôleur associé à la vue `quantikView.fxml` (fournie, avec les `fx:id`).
- `PieceRenderer` fabrique les formes JavaFX représentant les pièces.
- `QuantikViewModel` fait le lien entre le contrôleur et le modèle (fourni, voir ci-dessous).
- `QuantikMain` est l'application JavaFX qui charge la vue et affiche la fenêtre.

Vous écrirez ces classes pas à pas, en commençant par la structure du contrôleur et en terminant par
l'application.

#### Le modèle fourni (`fr.univ_amu.iut.modele`)

Vous pouvez vous appuyer sur les types suivants sans avoir à les écrire :

```java
enum Forme { CUBE, SPHERE, CYLINDRE, CONE }
enum Joueur { BLANC, NOIR }
record Piece(Forme forme, Joueur proprietaire) {}
```

#### Le `QuantikViewModel` fourni

Le ViewModel expose l'état du jeu sous forme de **propriétés observables** et de méthodes prêtes à
l'emploi. Voici son interface publique :

```java
public class QuantikViewModel {
    // Propriétés observables (pour les liaisons)
    ReadOnlyStringProperty statutProperty();        // ex. "Au tour du joueur BLANC"
    ReadOnlyObjectProperty<Joueur> joueurCourantProperty();
    ReadOnlyObjectProperty<Etat> etatProperty();
    ReadOnlyIntegerProperty nombreCoupsProperty();  // change à chaque coup joué

    // Lecture de l'état
    Piece pieceEn(int ligne, int colonne);          // la pièce d'une case, ou null
    int compte(Joueur joueur, Forme forme);         // pièces restantes d'un joueur
    Joueur joueurCourant();
    Forme formeSelectionnee();                       // forme choisie, ou null
    boolean estTerminee();
    String messageFin();                             // ex. "Victoire du joueur BLANC !"

    // Actions
    void selectionner(Forme forme);                  // choisir une forme à poser
    boolean peutJouerEn(int ligne, int colonne);     // le coup est-il légal ?
    boolean jouerEn(int ligne, int colonne);         // jouer (renvoie false si illégal)
    void nouvellePartie();
}
```

---

## Exercice 1 - La structure du contrôleur

On commence par poser le squelette du contrôleur. La vue `quantikView.fxml` est fournie : ses
composants portent déjà un `fx:id`, il suffit de déclarer les champs correspondants. La voici :

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.BorderPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>

<BorderPane xmlns="http://javafx.com/javafx"
            xmlns:fx="http://javafx.com/fxml"
            fx:controller="fr.univ_amu.iut.QuantikController"
            styleClass="racine">
  <top>
    <HBox styleClass="barre-haut" alignment="CENTER" spacing="20.0">
      <children>
        <Label fx:id="statutLabel" styleClass="statut" text="Au tour du joueur BLANC" />
        <Button fx:id="nouvellePartieBouton" text="Nouvelle partie" />
      </children>
    </HBox>
  </top>
  <left>
    <VBox fx:id="poolBlanc" styleClass="pool" alignment="TOP_CENTER" spacing="12.0" />
  </left>
  <right>
    <VBox fx:id="poolNoir" styleClass="pool" alignment="TOP_CENTER" spacing="12.0" />
  </right>
  <center>
    <GridPane fx:id="plateauGrid" styleClass="plateau" alignment="CENTER" />
  </center>
</BorderPane>
```

1. Écrire la déclaration de la classe publique `QuantikController`.

2. Déclarer les cinq champs `@FXML` correspondant aux `fx:id` de la vue : le label de statut
   `statutLabel` (de type `Label`), le bouton `nouvellePartieBouton` (de type `Button`), les deux
   réserves `poolBlanc` et `poolNoir` (de type `VBox`) et le plateau `plateauGrid` (de type
   `GridPane`).

3. Déclarer un champ `viewModel` de type `QuantikViewModel`, initialisé avec une nouvelle instance.

4. Déclarer un tableau de `StackPane` à deux dimensions, de taille 4x4 et nommé `cases`, qui mémorisera
   les cases du plateau.

5. Écrire une méthode `initialize()` annotée `@FXML`, vide pour l'instant (elle sera complétée au
   dernier exercice).

---

## Exercice 2 - Le rendu des pièces

La classe `PieceRenderer` transforme une `Piece` du modèle en une forme JavaFX (`Shape`) que l'on
pourra afficher.

1. Écrire la méthode **statique** `Shape rendre(Piece piece)` avec un `switch` sur `piece.forme()` qui
   renvoie un `Rectangle` (40x40) pour `CUBE`.

   ```java
   @Test
   void leCubeEstUnRectangle() {
       assertThat(PieceRenderer.rendre(new Piece(Forme.CUBE, Joueur.BLANC)))
           .isInstanceOf(Rectangle.class);
   }
   ```

2. Compléter le `switch` pour les autres formes : un `Circle` (rayon 20) pour `SPHERE`, une `Ellipse`
   (rayons 20 et 13) pour `CYLINDRE`, un `Polygon` triangulaire pour `CONE`.

   ```java
   @Test
   void laSphereEstUnCercle() {
       assertThat(PieceRenderer.rendre(new Piece(Forme.SPHERE, Joueur.BLANC)))
           .isInstanceOf(Circle.class);
   }
   ```

3. Colorer la pièce selon son propriétaire : remplissage `Color.LIGHTBLUE` pour `BLANC`,
   `Color.LIGHTCORAL` pour `NOIR` (méthode `setFill`).

   ```java
   @Test
   void unePieceNoireEstColoreeEnRougeClair() {
       assertThat(PieceRenderer.rendre(new Piece(Forme.CUBE, Joueur.NOIR)).getFill())
           .isEqualTo(Color.LIGHTCORAL);
   }
   ```

4. Ajouter un contour à la forme (`setStroke`, `setStrokeWidth`).

5. Ajouter à la forme la classe CSS `"piece"` (via `getStyleClass().add(...)`).

---

## Exercice 3 - Le plateau

On construit la grille de jeu dans le conteneur `plateauGrid`.

1. Écrire la méthode `construirePlateau()` qui crée 16 cases (un `StackPane` de classe CSS `"case-vide"`
   chacune), les mémorise dans `cases` et les ajoute à la grille avec `plateauGrid.add(cellule, colonne,
   ligne)`.

2. Ajouter aux cases les classes CSS qui dessinent les zones : `"bordure-droite"` pour les cases de la
   colonne d'indice 1, `"bordure-bas"` pour celles de la ligne d'indice 1. Écrire les règles
   correspondantes dans `quantik.css` (une bordure épaisse, par exemple 4px, du côté de la zone).

3. Faire réagir un clic sur une case : appeler `viewModel.jouerEn(ligne, colonne)`.

4. Écrire la méthode `rafraichirPlateau()` qui, pour chaque case, vide son contenu puis, si
   `viewModel.pieceEn(ligne, colonne)` n'est pas `null`, y ajoute `PieceRenderer.rendre(piece)`.

5. Si `jouerEn` renvoie `false` (coup interdit), donner un retour visuel sur la case : afficher
   brièvement un voile rouge semi-transparent (un `Rectangle` de couleur `Color.color(0.9, 0.2, 0.2,
   0.5)`) retiré après un court délai via une `PauseTransition`.

---

## Exercice 4 - Les réserves cliquables

Les deux réserves (`poolBlanc` et `poolNoir`) affichent les pièces disponibles et permettent de choisir
la forme à jouer.

1. Écrire la méthode `construireReserves()` qui, pour chaque joueur, ajoute dans sa `VBox` un libellé
   ("BLANC" / "NOIR") puis une **vignette** par forme.

2. Écrire une méthode `vignetteReserve(Joueur joueur, Forme forme)` qui crée la vignette : un
   `StackPane` de classe CSS `"vignette"` contenant `PieceRenderer.rendre(new Piece(forme, joueur))`.

3. Faire en sorte qu'un clic sur une vignette de la réserve du **joueur courant** appelle
   `viewModel.selectionner(forme)`.

4. Surligner la vignette sélectionnée à l'aide d'une **pseudo-classe CSS** `:selectionne` (par exemple
   un contour orange). On rappelle l'API :

   ```java
   private static final PseudoClass SELECTIONNE = PseudoClass.getPseudoClass("selectionne");
   // ...
   vignette.pseudoClassStateChanged(SELECTIONNE, estSelectionnee);
   ```

5. Écrire la méthode `rafraichirReserves()` qui, pour chaque vignette, réduit l'opacité à `0.3` et la
   désactive quand `viewModel.compte(joueur, forme) == 0`, et applique le surlignage de l'étape 4.

---

## Exercice 5 - L'assemblage et l'application

On réunit enfin toutes les briques dans `initialize()` (comme un constructeur), puis on écrit la classe
`QuantikMain`, qui est la **dernière** à réaliser.

1. Dans `initialize()`, lier le texte du label de statut (`statutLabel`) à la propriété
   `statutProperty()` du ViewModel, et faire en sorte qu'un clic sur le bouton « Nouvelle partie »
   appelle `viewModel.nouvellePartie()`.

2. Toujours dans `initialize()`, appeler `construirePlateau()` et `construireReserves()`.

3. Dans `initialize()`, écouter `viewModel.nombreCoupsProperty()` : à chaque changement, appeler
   `rafraichirPlateau()` et `rafraichirReserves()`. Appeler aussi ces deux méthodes une fois en fin de
   `initialize()` pour l'affichage initial.

4. Écrire la méthode `annoncerFin()` (une `Alert` de type `INFORMATION` dont l'en-tête est
   `viewModel.messageFin()`) et, dans `initialize()`, écouter `viewModel.etatProperty()` pour l'appeler
   quand `viewModel.estTerminee()`.

5. Écrire `QuantikMain.start(Stage primaryStage)` : charger `/fxml/quantikView.fxml` avec un
   `FXMLLoader`, créer une `Scene` (par exemple 720x560), lui ajouter `/css/quantik.css`, donner le
   titre `"Quantik"` à la fenêtre et l'afficher.

6. Écrire la méthode `main` la plus réduite possible pour lancer l'application (`launch(args);`).
