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

L'IHM que vous allez réaliser ressemblera à ceci : une grille 4x4 au centre (avec des bordures
épaisses pour séparer les quatre zones), la réserve du joueur BLANC à gauche, celle du joueur NOIR à
droite, et un bandeau de statut en haut.

![Apercu de l'application Quantik que vous allez realiser](src/main/resources/assets/quantik_screenshot.png)

### Travail à réaliser

L'objectif de ce sujet est d'évaluer votre capacité à écrire une IHM en Java avec JavaFX. La logique
du jeu (le "modèle") **vous est fournie** : c'est exactement le code demandé dans le sujet R2.03,
livré dans le paquet `fr.univ_amu.iut.modele`. Les méthodes trop algorithmiques sont donc déjà
écrites. Vous pourrez retrouver une proposition de correction à l'adresse suivante :
<https://github.com/IUTInfoAix-R202/TestIHM2026/>.

L'application définit plusieurs types d'objets :

- `QuantikMain` est l'application JavaFX qui charge la vue et affiche la fenêtre.
- `quantikView.fxml` décrit la disposition de la vue (fournie, avec les `fx:id`).
- `QuantikController` est le contrôleur associé à cette vue.
- `QuantikViewModel` fait le lien entre le contrôleur et le modèle (fourni, voir ci-dessous).
- `PieceRenderer` fabrique les formes JavaFX représentant les pièces.

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
    Set<Coup> casesValidesPour(Forme forme);         // pour le bonus
}
```

Votre travail consiste à construire la vue qui consomme ce ViewModel.

---

## Partie A - Démarrage de l'application

### Exercice 1 - La classe `QuantikMain`

Écrire la méthode `public void start(Stage primaryStage)` de la classe `QuantikMain` (qui étend
`Application`). Elle devra :

1. créer un `FXMLLoader` sur la ressource `/fxml/quantikView.fxml` et charger la racine de la vue ;
2. créer une `Scene` à partir de cette racine (par exemple 720x560) ;
3. ajouter la feuille de style `/css/quantik.css` à la scène ;
4. donner le titre `"Quantik"` à la fenêtre, lui associer la scène et l'afficher.

Écrire aussi la méthode `main` la plus réduite possible pour lancer l'application.

### Exercice 2 - Le contrôleur `QuantikController`

1. Déclarer dans `QuantikController` les champs `@FXML` correspondant aux `fx:id` de la vue : le
   `Label statutLabel`, le `Button nouvellePartieBouton`, les deux `VBox poolBlanc` et `poolNoir`, et
   la `GridPane plateauGrid`.

2. Écrire la méthode `@FXML private void initialize()`. Pour l'instant, elle se contente de **lier**
   le texte du label de statut à la propriété du ViewModel, et de brancher le bouton "Nouvelle
   partie" :

   ```java
   statutLabel.textProperty().bind(viewModel.statutProperty());
   nouvellePartieBouton.setOnAction(event -> viewModel.nouvellePartie());
   ```

   (Le ViewModel est un champ : `private final QuantikViewModel viewModel = new QuantikViewModel();`.)

---

## Partie B - Le rendu d'une pièce

### Exercice 3 - La classe `PieceRenderer`

Écrire la méthode **statique** `Shape rendre(Piece piece)` qui construit la forme JavaFX représentant
une pièce. On associe une classe de forme JavaFX différente à chaque `Forme` :

| Forme      | Forme JavaFX                              |
|------------|-------------------------------------------|
| `CUBE`     | `Rectangle` (40x40)                        |
| `SPHERE`   | `Circle` (rayon 20)                        |
| `CYLINDRE` | `Ellipse` (rayons 20 et 13)                |
| `CONE`     | `Polygon` triangulaire                     |

On utilisera un `switch` sur `piece.forme()`. Les tests suivants doivent passer :

```java
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
```

### Exercice 4 - La couleur des pièces

Compléter `rendre` pour colorer la pièce selon son propriétaire : remplissage **bleu clair**
(`Color.LIGHTBLUE`) pour le joueur `BLANC`, **rouge clair** (`Color.LIGHTCORAL`) pour le joueur
`NOIR`. Ajouter un contour (`setStroke`, `setStrokeWidth`) et la classe CSS `"piece"`.

```java
@Test
void unePieceNoireEstColoreeEnRougeClair() {
    assertThat(PieceRenderer.rendre(new Piece(Forme.CUBE, Joueur.NOIR)).getFill())
        .isEqualTo(Color.LIGHTCORAL);
}
```

---

## Partie C - Les réserves cliquables

### Exercice 5 - Afficher les réserves

Dans le contrôleur, écrire une méthode `construireReserves()` (appelée depuis `initialize`) qui, pour
chaque joueur, ajoute dans la `VBox` correspondante un libellé ("BLANC" / "NOIR") puis une
**vignette** par forme. Une vignette est un `StackPane` (classe CSS `"vignette"`) contenant la forme
obtenue via `PieceRenderer.rendre(new Piece(forme, joueur))`.

### Exercice 6 - Sélectionner une forme

Faire en sorte qu'un clic sur une vignette de la réserve du **joueur courant** appelle
`viewModel.selectionner(forme)`. Matérialiser la vignette sélectionnée avec une **pseudo-classe CSS**
`:selectionne` (par exemple un contour orange). On rappelle l'API des pseudo-classes :

```java
private static final PseudoClass SELECTIONNE = PseudoClass.getPseudoClass("selectionne");
// ...
vignette.pseudoClassStateChanged(SELECTIONNE, estSelectionnee);
```

### Exercice 7 - Griser les formes épuisées

Quand une forme n'a plus de pièce pour un joueur (`viewModel.compte(joueur, forme) == 0`), réduire
l'opacité de sa vignette à `0.3` et la désactiver (`setDisable(true)`).

---

## Partie D - Le plateau interactif

### Exercice 8 - Construire la grille

Écrire une méthode `construirePlateau()` qui crée 16 cases (un `StackPane` de classe CSS
`"case-vide"` par case) et les ajoute à `plateauGrid` avec `plateauGrid.add(cellule, colonne, ligne)`.
Conserver les cases dans un tableau `StackPane[4][4]` pour pouvoir les redessiner.

### Exercice 9 - Matérialiser les quatre zones

Ajouter aux cases les classes CSS qui dessinent les séparations de zones : une bordure épaisse à
**droite** des cases de la colonne d'indice 1, et une bordure épaisse en **bas** des cases de la ligne
d'indice 1. Écrire dans `quantik.css` les règles `.bordure-droite` et `.bordure-bas` correspondantes
(par exemple une bordure de 4px côté zone).

### Exercice 10 - Jouer en cliquant

Au clic sur une case `(ligne, colonne)`, appeler `viewModel.jouerEn(ligne, colonne)`. Pour redessiner
le plateau après chaque coup, écouter la propriété `nombreCoupsProperty()` du ViewModel et, dans
l'écouteur, vider chaque case puis y placer `PieceRenderer.rendre(piece)` si `viewModel.pieceEn(...)`
n'est pas `null`.

### Exercice 11 - Signaler un coup invalide

Si `jouerEn` renvoie `false` (coup interdit), donner un retour visuel sur la case : par exemple
afficher brièvement un voile rouge semi-transparent (`Rectangle` avec une couleur `Color.color(0.9,
0.2, 0.2, 0.5)`) que l'on retire après un court délai à l'aide d'une `PauseTransition`.

---

## Partie E - Statut et fin de partie

### Exercice 12 - Le bandeau de statut

Le label de statut est déjà lié au ViewModel (exercice 2). Vérifier qu'il affiche bien "Au tour du
joueur BLANC" au démarrage, puis "Au tour du joueur NOIR" après un premier coup.

### Exercice 13 - Annoncer la victoire

Écouter la propriété `etatProperty()` ; lorsque `viewModel.estTerminee()` devient vrai, afficher une
`Alert` (type `INFORMATION`) dont l'en-tête est `viewModel.messageFin()` et qui invite à recommencer.

---

## Partie F - Nouvelle partie

### Exercice 14 - Rejouer

Le bouton "Nouvelle partie" appelle déjà `viewModel.nouvellePartie()` (exercice 2). Vérifier qu'un
clic réinitialise le plateau et les réserves dans l'affichage (l'écouteur de `nombreCoupsProperty`
s'en charge si vous l'avez branché correctement).

---

## Bonus

- **Bonus 1 (MVVM)** : quand une forme est sélectionnée, prévisualiser les cases jouables en les
  teintant de vert transparent, en s'appuyant sur `viewModel.casesValidesPour(forme)`. C'est une
  vraie démonstration de MVVM : la vue ne fait que **refléter** un état calculé par le modèle.
- **Bonus 2** : surligner l'alignement gagnant en fin de partie.
