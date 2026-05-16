# <img src="https://raw.githubusercontent.com/IUTInfoAix-M2105/Syllabus/master/assets/logo.png" alt="class logo" class="logo"/> Module 2105 : Introduction aux IHM en Java 

## Test d'IHM et langage Java [![Build Status](https://travis-ci.com/IUTInfoAix-M2105/TestIHM2017.svg?token=zPXgu159amQhEb4ShTxW&branch=master)](https://travis-ci.com/IUTInfoAix-M2105/TestIHM2017)

**Test du vendredi 16 juin 2017 – Durée 2 heures – Documents autorisés**

L’objet de cet exercice est la programmation d’un jeu du **taquin**. Le taquin est un jeu solitaire en forme de **damier carré** créé vers 1870 aux États-Unis. Sa théorie mathématique a été publiée par l'American Journal of mathematics pure and applied en 1879. En 1891, son invention fut revendiquée par Sam Loyd, au moment où le jeu connaissait un engouement considérable, tant aux États-Unis qu'en Europe. 

Il est composé de 15 petits **carreaux numérotés de 1 à 15** qui glissent dans un cadre 8x8, prévu pour 16. Il consiste à remettre dans l'ordre les 15 carreaux à partir d'une configuration initiale quelconque par des déplacements (glissements) de carreaux voisins de la **case vide** jusqu'à former la configuration solution. Le principe a été étendu à toutes sortes d'autres jeux. La plupart sont à base de *blocs* rectangulaires plutôt que carrés, mais le but est toujours de disposer les blocs d'une façon déterminée par un nombre minimal de mouvements. Le Rubik's Cube est aujourd'hui considéré comme l'un des « descendants » du taquin.

L'image ci-dessous, illustre un jeu du taquin résolu :

![](https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/15-puzzle.svg/300px-15-puzzle.svg.png)

### Travail à réaliser

L'IHM que vous allez en partie réaliser ressemblera à la fenêtre suivante :

![IHM](src/main/resources/assets/taquin_screenshot.png)

L'application définit ainsi plusieurs types d'objets :
- Un objet `TaquinMain` est une application JavaFX permettant de jouer au Taquin.
- Un objet `TaquinIHM` est la racine de la scène de jeu (l'intérieur de la fenêtre de l'image).
- Un objet `TaquinBoard` est le plateau de jeu composé des 16 cases, que l'on voit au centre du `TaquinIHM`
- Un objet `Carreau` représente un carreau du taquin.
- Un objet `StatusBar` est la barre en bas du `TaquinIHM` qui affiche le score et l'état de la partie.
- Un objet `Position` contient la position d'un carreau dans le plateau


Le diagramme UML suivant donne un aperçu synthétique de la structure des classes de l'application. Il n'est pas nécessaire de l'étudier pour l'instant, mais il vous sera très utile pour retrouver les données membres et méthodes des différentes classes.

![UML](src/main/resources/diagram.png)

L'objectif de ce test est d'évaluer votre capacité à écrire une IHM à l'aide du langage Java, les méthodes complexes car trop algorithmiques n'auront pas à être implémentées. Vous pourrez retrouver une proposition de correction à l'adresse suivante : https://github.com/IUTInfoAix-m2105/TestIHM2017/

Votre travail dans la suite de ce sujet sera d'écrire pas à pas les 4 premières classes. Le code des classes `StatusBar` et `Position` vous est donné à titre d'information ci-dessous, pour que vous puissiez vous y référer si besoin au cours des exercices. 

### La classe `Position`

Cette classe permet d'enregistrer la position d'un `Carreau` sur le plateau de jeu. Son implémentation vous est donnée ci-dessous à titre d'information :
```java
public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position location = (Position) o;
        return x == location.x && y == location.y;
    }

}
```

### La classe `StatusBar`
La classe `StatusBar` est un composant graphique permettant d'afficher l'état de la partie en cours. L'implémentation de cette classe vous est donnée ci-dessous (où la gestion et l'affichage de la durée ont été omis pour ne pas surcharger le texte):
```java
public class StatusBar extends BorderPane {
    private Label labelNombreDeMouvement = new Label();
    private Label labelpartieTerminee = new Label();
    private IntegerProperty nombreDeMouvement = new SimpleIntegerProperty();
    private BooleanProperty estPartieTerminee = new SimpleBooleanProperty();

    public StatusBar() {
        creerBindings();
        setLeft(labelNombreDeMouvement);
        setCenter(labelpartieTerminee);
    }

    private void creerBindings() {
        labelNombreDeMouvement.textProperty()
            .bind(Bindings.concat("Mouvements : ",nombreDeMouvement));
        labelpartieTerminee.textProperty()
            .bind(when(estPartieTerminee)
                .then("Partie terminée !")
                .otherwise(""));
    }

    void nouvellePartie() {
        // réinitialisation de la durée
    }

    public IntegerProperty nombreDeMouvementProperty() {
        return nombreDeMouvement;
    }

    public BooleanProperty estPartieTermineeProperty() {
        return estPartieTerminee;
    }
}
```

### Exercice 1 - Implémentation de la classe `Carreau`

Le plateau de jeu du taquin disposera de 16 cases, dont 15 seront en permanence occupées par des **carreaux**, et une case sera toujours libre. Chaque carreau est numéroté de 1 à 15, sera placé au hasard sur une case en début de partie, et devra être placé à une position précise en fin de partie.

1. Écrire la déclaration d'une classe publique `Carreau`, sous-classe de (étendant) `Button`, réduite pour le moment à la déclaration des variables d'instance suivantes, toutes privées (cf. diagramme UML) :
     - `value` de type `Integer` qui correspond au numéro du carreau. 
     - `position` de type `Position`, la position courante dans le plateau.
     - `positionAttendue` de type `Position`, la position attendue en fin de jeu.

2. Écrire le constructeur public `Carreau(Integer numero, Position position)` qui : 
    - Assigne les données membres aux paramètres donnés correspondants, sachant que la `position` en paramètre correspond à sa position attendue, ainsi qu'à sa position initiale avant mélange.
    - Fixe la largeur et la hauteur du `Carreau` à `TaquinBoard.CELL_SIZE`, soit la taille d'une cellule. Aide : utilisez les méthodes `setMinSize()`, `setMaxSize()` et `setPrefSize()` qu'un `Carreau` hérite de `Button`.
    - Fixe l'alignement du contenu du carreau au centre.
    - Fixe comme texte du bouton le numéro du carreau courant.

3. Écrire les accesseurs publics `getValue()` et `getPosition()` qui renvoient la donnée membre correspondante.

4. Écrire le modifieur public `setPosition(Position position)` qui assigne la donnée membre correspondante.

5. Écrire la méthode `public boolean estBienPlace()` qui indique si la position courante du carreau est la même que sa position attendue en fin de partie.


### Exercice 2 - Implémentation de la classe `TaquinBoard`

La classe `TaquinBoard` est un composant graphique qui hérite de `GridPane` pour représenter visuellement le plateau de jeu. Elle crée les carreaux du plateau, les place à une position au hasard en les mélangeant, et gère les déplacements (coups) du joueur. Pour simplifier, la case vide sera matérialisée par un carreau particulier, dit "vide".

1. Écrire la déclaration d'une classe publique `TaquinBoard`, sous-classe de `GridPane`, réduite pour le moment à la déclaration des variables d'instance suivantes, toutes privées :
     - `taille` de type `int` qui contiendra la taille en largeur et en hauteur du plateau, de laquelle on en déduira le nombre de carreaux : `taille` x `taille` (ce qui comprend le carreau vide).
     - `carreaux` qui est une liste (`List`) d'objets `Carreau`, créée et peuplée dans le constructeur
     - `carreauVide` qui est le `Carreau` particulier de la liste `carreaux` qui représente la case vide
     - `estPartieTerminee` qui est une propriété booléenne qui indique si la partie est terminée
     - `nombreDeMouvement` qui est une propriété entière qui indique le nombre de coups (déplacements) joués au cours de la partie courante
     - `ecouteurDeCarreau` qui est un gestionnaire des événements `ActionEvent` qui s'occupe de traiter les clics sur les carreaux, permettant au joueur de les déplacer

2. Écrire le constructeur public `TaquinBoard(int taille)` qui :
     - Affecte à la variable d'instance correspondante la `taille` passée en paramètre
     - Crée et initialise à `false` sa propriété `estPartieTerminee`
     - Crée et initialise à 0 sa propriété `nombreDeMouvement`
     - Initialise la liste (vide) des carreaux avec un objet `ArrayList`
     - Appelle la méthode `creerEcouteurDeCarreau()`
     - Appelle la méthode `initCarreaux()`

3. Écrire la méthode `private void initCarreaux()` qui crée et place tous les carreaux du plateau. Pour cela, elle :
     - Initialise à 0 une variable locale contenant le numéro du carreau courant
     - Utilise deux boucles imbriquées, l'une pour les lignes et l'autre pour les colonnes, pour créer les `taille` x `taille` objets `Carreau`. Pour chaque carreau à créer, il faut :
          * incrémenter le numéro du carreau courant
          * créer l'objet `Carreau` avec son numéro et sa `Position` (courante et attendue)
          * ajouter le carreau au `TaquinBoard` (qui est un `GridPane`) à cette position dans le plateau
          * fixer `ecouteurDeCarreau` comme son gestionnaire de l'événement clic de souris (actionnement du bouton)
          * ajouter ce carreau à la liste `carreaux` du plateau
     - Puis fait appel à la méthode `initCarreauVide()`

4. Écrire la méthode `private void initCarreauVide()` qui :
     - Affecte à `carreauVide` le dernier carreau de la liste `carreaux`
     - Remplace son texte par la chaîne vide
     - Désactive ce carreau pour qu'il ne soit plus cliquable, avec la méthode `setDisable(boolean)` héritée de `Button`

5. Écrire la méthode `private void placer(Carreau carreau, Position position)` qui :
     - Place le carreau dans la nouvelle position donnée 
     - Met à jour la position du `carreau` dans le `GridPane` (`TaquinBoard`) par une contrainte fixée avec la méthode statique `GridPane.setConstraints()`.

6. Écrire la méthode `private void permuter(Carreau carreau1, Carreau carreau2)` qui permute les positions des deux carreaux donnés en paramètres à l'aide de la méthode `placer()`, puis incrémente le nombre de mouvements.

7. Écrire la méthode `private void deplacer(Carreau carreau)` qui déplace le `carreau` s'il est adjacent à la case vide. Dans cette méthode, vous devrez :
    - Créer une variable `positionCarreau` qui mémorise la position courante du carreau passé en paramètre.
    - Créer une variable `positionVide` qui mémorise la position de la case vide (`carreauVide`).
    - Créer des variables de type `Position` correspondant aux coordonnées à gauche, à droite, en haut et en bas de la position du carreau courant.
    - Tester si l'une de ces positions correspond à celle de la case vide. Si tel est le cas, les permuter.

8. Écrire la méthode `private void verifierFinDePartie()` qui fixe la propriété `estPartieTerminee` comme vraie si tous les carreaux de la liste sont bien placés à leur position attendue en fin de partie.

9. Écrire la méthode `private void creerEcouteurDeCarreau()` qui crée l'écouteur des carreaux (comme une expression lambda ou une instance d'une classe à définir) qui :
    - Détermine quel est le carreau à l'origine de l'événement
    - Déplace ce carreau
    - Vérifie si la partie est terminée
    
Il ne vous est pas demandé d'écrire les méthodes restantes de cette classe (cf. diagramme UML).


### Exercice 3 - Implémentation de la classe `TaquinIHM`
La classe `TaquinIHM` représente le contenu de la fenêtre principale du Jeu (le noeud racine de la scène). En plus du plateau situé au centre, cette fenêtre contient une barre de menu en haut et une barre de statut en bas. La barre de menu contient un menu "Jeu" constitué d'une entrée "Nouvelle Partie" et d'une entrée "Quitter".

1. Écrire la déclaration d’une classe `TaquinIHM`, sous-classe de `BorderPane`, réduite, pour commencer, à ses variables d’instance, toutes privées :
    - `taille` de type `int` représente la taille du plateau de jeu.
    - `statusBar` de type `StatusBar` est l'objet matérialisant la barre de statut
    - `taquinBoard` de type `TaquinBoard` est l'objet plateau de jeu
    - `menuBar` de type `MenuBar` est la barre de menu de notre jeu

2. Écrire la méthode `void nouvellePartie()` qui s'occupe de créer une nouvelle partie en effectuant le bon traitement sur le plateau de jeu et sur la barre de statut (regarder le diagramme UML pour trouver les méthodes à appeler).

3. Écrire la méthode `private Menu creerMenuJeu()` qui crée le menu "Jeu". Ce menu devra être constitué de deux items, un premier pour l'entrée "Nouvelle Partie" et un second pour "Quitter". Ne pas oublier d'ajouter un écouteur d'action (de la même façon que pour un bouton). "Nouvelle Partie" se contente d'appeler la méthode `nouvellePartie()` et "Quitter" termine proprement de l'application.

4. Écrire la méthode `private MenuBar creerBarreDeMenu()` qui s'occupe de créer la barre de menu ne contenant que le menu "Jeu".

5. Écrire la méthode `private void creerBindings()` qui s'occupe de créer tous les bindings entre les différents composants de notre IHM. Cette méthode devra, lier les propriétés qui ont le même nom dans les classes `StatusBar` et `TaquinBoard`. Sur la propriété `estPartieTerminee` de la donnée membre `taquinBoard`, ajouter un écouteur de changement qui appelle la méthode `afficheDialogFinDePartie()` lorsque la partie est terminée. 

6. Écrire le constructeur par défaut de la classe `TaquinIHM`. Ce constructeur devra :
    - Initialiser correctement les différentes données membres.
    - Ajouter la barre de menu.
    - Placer le plateau de jeu au centre de la fenêtre.
    - Placer la barre de statut en bas de la fenêtre.
    - Créer les bindings.
    - Lancer une nouvelle partie.

### Exercice 4 - Implémentation de la classe `TaquinMain`

La classe `TaquinMain` est le programme principal de notre application. C'est elle qui a la responsabilité de charger la vue principale et de l'ajouter à la scène. Cette classe est une application JavaFX qui étend donc `Application`.

1. Écrire une méthode `main` aussi réduite que possible pour lancer l’exécution de l'application.

2. Écrire la méthode `public void start(Stage primaryStage)` qui :
    - Modifie le titre de la fenêtre en "Taquin".
    - Crée un objet `TaquinIHM`
    - L'ajoute le comme racine du graphe de scène.
    - Rend le stage visible.
