# Test d'IHM et langage Java

## Test du samedi 9 juin 2018 – Durée 2 heures – Documents autorisés

L'objet de ce test est l'écriture en Java d'une IHM pour une version simplifiée du jeu Mastermind. Le Mastermind est un jeu de société pour deux joueurs dont le but est de trouver une combinaison de couleurs.

Il se présente sous la forme d'une zone masquant la combinaison de 4 couleurs à trouver et d'un plateau perforé de 10 **rangées**. Chaque rangée comporte 4 **cases de jeu** (ou trous) pouvant accueillir des **pions de jeu**. Il y a 8 couleurs de pions de jeu, généralement : rouge, jaune, vert, bleu, orange, marron, violet et fushia.

Il y a également 4 **cases de score** associées à chaque rangée, et pouvant accueillir des **pions de score** qui sont utilisés pour donner des indications à chaque étape du jeu. Il y a 2 couleurs de pions de score : blanc et rouge (parfois noir).

Notons que les pions de score rouges ne sont pas les mêmes que les pions de jeu rouges.

## Règles du jeu

Dans ce jeu, les joueurs endossent à tour de rôle à chaque manche un comportement actif ou passif. Un des deux joueur commence par placer une combinaison de pions de son choix (éventuellement plusieurs pions de la même couleur) sans qu'ils soient vus de l'autre joueur à l'arrière d'un cache qui les masquera à la vue de celui-ci jusqu'à la fin de la manche. Ce joueur sera appelé joueur passif car une fois la combinaison choisie, il est surtout dans un rôle d'attente. **Dans notre cas, l'ordinateur jouera entièrement le rôle du joueur passif.**

Le joueur qui n'a pas choisi la combinaison de départ (et pour qui elle est totalement inconnue) doit trouver la combinaison exacte, c'est à dire quelles sont les couleurs de ses pions, ainsi que leur position. Ce joueur est désigné comme joueur actif.
**Dans notre cas, c'est le seul joueur humain du jeu.**

Pour cela, à chaque tour, le joueur actif doit se servir de pions de jeu pour remplir les cases de jeu de la première  rangée disponible, selon l'idée qu'il se fait de la combinaison de pions à trouver.

Une fois une rangée remplie, le joueur passif (ordinateur) doit placer des pions de score sur les cases de score de cette rangée pour indiquer :

1. le nombre de pions de la bonne couleur et bien placés, en positionnant le même nombre de pions de score rouges ;
2. le nombre de pions de la bonne couleur mais mal placés, en positionnant le même nombre de pions de score blancs.

Notons que la position des pions de score est quelconque sur les cases de score. En d'autres termes, le joueur actif ne peut rien déduire de leur position.

Il arrive donc, surtout en début de partie, que le joueur passif ne fasse rien concrètement (ne place aucun pion de score) si aucun pion de jeu placé par le joueur actif ne porte une couleur de la combinaison à trouver.

La tactique du joueur actif consiste à proposer une combinaison en fonction des coups précédents et de leur score, de manière à tirer le maximum d'information de la réponse du joueur passif, puisque le nombre de tentatives est limité par le nombre de rangées de jeu.

Le joueur actif gagne cette manche s'il trouve la bonne combinaison de pions avant d'épuiser sa dernière rangée.

## Schéma directeur du travail à réaliser

Vous allez écrire une IHM d'un Mastermind en mode joueur unique, qui doit trouver la combinaison choisie aléatoirement par l'ordinateur. Aussi, pour simplifier la suite du sujet, nous utiliserons le terme *ordinateur* à la place de joueur passif, et simplement le terme *joueur* pour joueur actif.

L'objectif étant d'évaluer votre capacité à écrire une IHM à l'aide du langage Java, les méthodes complexes car trop algorithmiques n'auront pas à être implémentées.

Votre travail dans la suite de ce sujet sera d'écrire (parfois partiellement) pas à pas plusieurs classes importantes :

- Un objet `MastermindMain` est l'application principale du jeu;
- Un objet `MastermindControleur` est le contrôleur de la racine de la scène de jeu (l'intérieur de la fenêtre de l'image). ;
- Un objet `Combinaison` permet de générer et exploiter (masquer/afficher/tester) la combinaison à trouver ;
- Un objet `Plateau` représente le plateau de jeu composé des 10 rangées de 4 cases de jeu et de 4 cases de score ;
- Un objet `PionJeu` représente une pion de jeu que le joueur peut (doit) déposer dans une case de jeu ;
- Un objet `CaseJeu` représente une case de jeu du plateau, où le joueur peut placer un pion de jeu ;
- Un objet `RangéeJeu` permet de regrouper les cases de jeu d'une rangée, dans lesquelles le joueur propose une combinaison de pions ;
- Un objet `PionScore` représente un pion de score que l'ordinateur déposera dans une case de score ;
- Un objet `CaseScore` représente une case de score où l'ordinateur pourra déposer un pion de score ;
- Un objet `MarquageScore` permet de regrouper les cases de score associées à une rangée, où l'ordinateur donne les indications sur la  combinaison proposée par le joueur sur la rangée de jeu correspondante.

Comme vous le verrez ci-après, le test est volontairement pas évident à terminer à 100%. **Concentrez-vous en priorité sur ce que vous savez faire 
et laissez de coté le reste**. Un étudiant qui aura fait plus de 80% des questions pourra espérer avoir la note maximale.

### Implémentation de la classe `PionJeu`

La classe `PionJeu` permet de représenter les pions de couleurs utilisés par le joueur actif. La responsabilité principale de cette classe est de conserver l'ensemble des pions ainsi que leur apparence.

1. Écrire la classe `PionJeu` ayant pour commencer, une seule donnée membre privée. Elle se nommera `image` de type `Image` et permettra de conserver l'image affichée dans les cases.
2. Écrire le constructeur `PionJeu(String fileName)` qui crée l'`Image` à partir du nom de fichier passé en paramètre.
3. Écrire l’accesseur `public Image getImage()` qui retourne la donnée membre `image`.
4. Les couleurs de pion étant connus à l'avance (`ROUGE`, `BLEU`, `FUCHSIA`, `JAUNE`, `MARRON`, `ORANGE`, `VERT` et `VIOLET`), leur création peut être faite de manière statique. Pour éviter d'avoir à complexifier notre code avec des valeurs nulles, un pion virtuel (`VIDE`) sera ajouté. Écrire la déclaration statiques des 3 première couleurs de pions  (`ROUGE`, `BLEU`, `FUCHSIA`) qui devront utiliser les images appelées "rouge.png", "bleu.png", "fuchsia.png". Pour la suite on considérera que toutes les données membres ont été correctement initialisées.
5. Écrire la méthode `public PionJeu suivant()` qui retourne le pion `BLEU` si le pion est `ROUGE` et `FUCHSIA` si le pion est `BLEU` et ainsi de suite. Pour vous faciliter l'écriture de cette méthode, il peut être astucieux de stocker vos pions précédemment créés dans un tableau.

### Implémentation de la classe `CaseJeu`

Pour réaliser le plateau de jeu, il nous faut des boutons sur lesquels on pourra déposer des pions.

Écrire la classe publique `CaseJeu` qui représente les boutons de notre plateau de jeu. Cette classe aura les caractéristiques suivantes :

- Elle étend la classe `Button`.
- Elle dispose d'une donnée membre privée nommée `imageView` qui est le composant affichant l'image du pion déposé sur la case.
- Elle dispose d'une donnée membre privée nommée `pion` mémorisant le pion déposé sur la case.
- Elle possède un accesseur et un modifieur publics pour cette donnée membre. Le modifieur à la responsabilité de changer l'image de la case (avec la méthode `setImage(Image image)` de l'objet `imageView`).
- Elle possède une méthode `void vider()` qui s'occupe d'enlever le pion de la case (penser à `PionJeu.VIDE`).
- Elle possède une méthode `void setMasqué(boolean masqué)` qui s'occupe de masquer/démasquer le pion contenu dans la case. Cette méthode sera utilisée par le joueur passif pour masquer sa combinaison et la montrer en fin de partie.
- Elle dispose d'une donnée membre `boutonPionJeuListener` du type `EventHandler<ActionEvent>`. Son initialisation sera faite en même temps que sa déclaration avec une classe anonyme qui surchargera la méthode `void handle(ActionEvent actionEvent)`. Cette surcharge fera en sorte qu'à chaque action sur une case, le pion déposé dessus passe au `suivant`. Pour ce faire vous pouvez utiliser indifféremment une expression lambda ou une classe anonyme.
- Elle a un unique constructeur sans argument, qui vide la case, lui affecte un auditeur d'événements (précédemment créé), par défaut la case est inactive (avec `setDisable(Boolean b)`).

### Implémentation de la classe `Combinaison`

Pour pouvoir implémenter la logique du jeu, il faut disposer d'une abstraction permettant de comparer deux combinaisons de pion de couleur. 

1. Écrire la classe publique `Combinaison` ayant deux données membres privée. La première appelée `taille` sera de type `int` et permettra de connaître le nombre de pions qui constitue la combinaison. La seconde appelée `pions` sera un tableau de `PionJeu` qui conservera les couleurs constitutives de la combinaison.
2. Écrire le constructeur `Combinaison(int taille)` qui initialise correctement les deux données membres. Par défaut, la combinaison sera vide (penser `PionJeu.VIDE`).
3. Écrire l'accesseur `public PionJeu getPion(int i)` et le modifieur `public void setPion(int i, PionJeu pion)` associés à la donnée membre privée `pions`.
4. Écrire l'accesseur de la donnée membre `taille`.
5. Écrire la méthode publique `boolean contient(PionJeu pion)` qui permet de savoir si un `PionJeu` appartient à une combinaison.

Pour la suite, on suppose que l'on disposera de la méthode de classe `static Combinaison genererCombinaisonAléatoire(int taille)` qui permet comme son nom l'indique de générer une combinaison aléatoire.

### Implémentation de la classe `Rangée`

La classe `Rangée` à pour responsabilité de regrouper l'ensemble des cases dans lesquels le joueur peut déposer une combinaison de couleur. 

Écrire la classe publique `Rangée` qui représente une rangée de case de notre plateau de jeu. Cette classe aura les caractéristiques suivantes :

- Elle étend la classe `HBox`.
- Elle dispose d'une donnée membre privée nommée `pions` de type `CaseJeu[]` qui conserve toutes les cases constitutives de la rangée.
- Elle dispose d'une donnée membre privée nommée `taille` qui permet de connaître le nombre de case dans la rangée.
- Elle possède une méthode `void vider()` qui s'occupe d'enlever les pion (vider) des différentes cases.
- Elle possède une méthode `void setMasquée(boolean enabled)` qui s'occupe de masquer/démasquer les pion des différentes cases.
- Elle possède une méthode `void setActivée(boolean enabled)` qui s'occupe d'activer/désactiver les pion des différentes cases.
- Elle possède une méthode `void setCombinaison(Combinaison combinaison)` qui s'occupe de modifier les pion des différentes cases en fonction de la combinaison passée en argument.
- Elle possède une méthode `Combinaison getCombinaison()` qui crée et retourne une combinaison de même taille que la rangée et qui parcours les `CaseJeu` pour y lire les pions contenus.
- Elle a un unique constructeur,`Rangée()`. Ce constructeur doit initialiser les deux données membres. Parcourir l'ensemble des cases pour les créer et les rajouter à la rangée (pensez à l'utilisation de `getChildrens().addAll(...)`).

### Implémentation des classes `PionScore`, `CaseScore` et `MarquageScore`

Les classes `PionScore`, `CaseScore` et `MarquageScore` sont très similaires aux classes que l'on vient d'implémenter. Pour la suite du travail, vous aurez juste besoin d'utiliser la méthode `public void setScore(int nombrePionsBiensPlacés, int nombrePionsMalsPlacés)` et `public void vider()` de la classe `MarquageScore`. Vous considérez donc en disposer.

### Implémentation de la classe `Plateau`

Cette classe est celle qui permet d'implémenter toute la logique du jeu. Elle est celle qui demanderait le plus de travail dans une implémentation complète. Dans votre cas, vous n'aurez pas à implémenter les méthodes qui calculeront le nombre de pions bien ou mal placés. Vous supposerez que vous disposer de deux méthodes qui remplissent cet office(`int calculerNombrePionsMalsPlacés(Combinaison combinaisonSecrète)` et `int calculerNombrePionsBiensPlacés(Combinaison combinaisonSecrète)`).

1. Écrire la classe `Plateau` qui dérive de `GridPane`. Cette classe aura les données membres privées suivantes :
     - `nombrePionsParRangée` de type `int` qui mémorise la taille d'une rangée du plateau de jeu.
     - `nombreRangées` de type `int` qui mémorise le nombre de rangées du plateau de jeu.
     - `rangéeSecrète` est la `Rangée` contenant la combinaisons secrète.
     - `combinaisonSecrète` est la combinaison générée aléatoirement par l'ordinateur que le joueur devra découvrir.
     - `rangées` est un tableau `nombreRangées` `Rangée` qui représente le plateau de jeu.
     - `scores` est un tableau `nombreRangées` `MarquageScore` qui représente la colonne de marquage de pions bien/mal placés.
     - `nombreDeCoupsJoués` de type `IntegerProperty` qui mémorise le nombre de coups joué depuis le début de la partie (la valeur de cette propriété est aussi l'indice de la rangée actuellement jouée).
     - `aGagné` est une propriété booléenne qui permet de savoir si le dernier coup était gagnant.
     - `aPerdu` est une propriété booléenne qui permet de savoir si le dernier coup était perdant.
     - `estPartieTerminée` est une propriété booléenne qui permet de savoir si le dernier coup a terminée la partie.
2. Écrire le constructeur `Plateau()` qui initialise toute les données membres. Seule la première ligne devra être activée en début de partie(`setActivée(true)`). Les méthodes `creerBindings()`, `remplirRangéesEtMarquages()` et `nouvellePartie()` seront appelées.
3. Écrire la méthode `private void vider()` qui parcourt toutes les rangées et tous les scores un par un pour les vider.
4. Écrire la méthode `private void viderRangéeCourante()` qui vide uniquement la rangée courante (pensez à utiliser la propriété `nombreDeCoupsJoués`).
5. Écrire la méthode `public int getNombreDeCoupsJoués()` qui retourne le nombre de coups joués depuis le début de la partie.
6. Écrire la méthode `public creerBindings()` qui s'occupe de correctement lier les propriétés `aPerdu` et `estPartieTerminée`. La première sera vraie si le nombre de coups joués dépassera le nombre de rangée. Quant à la seconde, sa valeur indiquera si le dernier coup été gagnant ou perdant. Sur la propriété `estPartieTerminee`, ajouter un écouteur de changement rend visible la rangée secrète(`setMasquée(false)`) lorsque la partie est terminée.
7. Écrire la méthode `public void nouvellePartie()` qui réinitialise le plateau de jeu en vidant les scores, les rangées et en activant uniquement la première rangée.
8. Écrire la méthode `void validerRangéeCourante()` qui a pour responsabilité de valider la combinaison courante, de calculer les scores et de mettre à jour la propriété `aGagné` et la donnée membre `nombreDeCoupsJoués`. C'est cette méthode qui doit s'occuper de *désactiver* la rangée qui vient d'être jouée et *d'activer* la suivante.

Même s'ils n'ont pas été écrit, vous supposerez dans la suite que vous disposez des accesseurs des différences propriétés de cette classe.

### Implémentation de la fenêtre principale

Le fichier `MasterMindView.fxml` représente la fenêtre principale du Jeu. En plus du plateau situé au centre, cette fenêtre contient une barre de menu située en haut,  la partie basse de la vue contiendra la barre de statut ainsi qu'une boite horizontale contenant deux boutons qui permettent de valider un coup ou d'effacer la dernière combinaison non validée. La barre de menu contient un menu "Jeu" constitué d'une entrée "Nouvelle Partie" et d'une entrée "Quitter".

Voici la classe `MasterMindControleur` associée à cette vue :

```java
public class MasterMindControleur implements Initializable {
    static final int NB_RANGEES = 8;
    static final int NB_PIONS_PAR_RANGEES = 4;
    @FXML
    private Plateau plateau;
    @FXML
    private StatusBar statusBar;
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        creerBindings();
    }
    private void creerBindings() {
        plateau.estPartieTerminéeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                afficherDialogFinDePartie();
        });
    }
    void setStageAndSetupListeners(Stage stage) {
        stage.setOnCloseRequest(event -> {
            this.actionMenuJeuQuitter();
            event.consume();
        });
    }
    @FXML
    void actionBoutonEffacer() {
        plateau.viderRangéeCourante();
    }
    @FXML
    void actionBoutonValider() {
        if (plateau.combinaisonCourante().contient(PionJeu.VIDE))
            return;
        plateau.validerRangéeCourante();
    }
    @FXML
    void actionMenuJeuNouveau() {
        plateau.nouvellePartie();
    }
    @FXML
    void actionMenuJeuQuitter() {
        Alert alert = new Alert(CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Êtes vous certain de quitter ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
            Platform.exit();
    }

    private void afficherDialogFinDePartie() {
        String messageFinDePartie;
        if (plateau.aGagné())
            messageFinDePartie = "Vous avez trouvez la bonne combinaison en " + plateau.getNombreDeCoupsJoués() + " coups";
        else
            messageFinDePartie = "Vous avez perdu !";
        Alert alert = new Alert(INFORMATION);
        alert.setTitle("Fin de partie");
        alert.setContentText(messageFinDePartie);
        alert.showAndWait();
    }
}
```

1. Écrire le contenu de `MasterMindView.fxml` en n'oubliant pas d’associer les actions adéquates aux boutons et aux items du menu.
