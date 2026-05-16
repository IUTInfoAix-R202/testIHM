# Test d'IHM et langage Java

**Test du samedi 4 juin 2016 – Durée 2 heures – Documents autorisés**

L’objet de cet exercice est la programmation d’un jeu de tic-tac-toe (souvent nommé à tord morpion). Le tic-tac-toe est un jeu de réflexion se pratiquant à deux joueurs au tour par tour et dont le but est de créer le premier un alignement sur une grille. Le jeu se joue généralement avec papier et crayon.


## Règles du jeu

Le tic-tac-toe est souvent appelé « morpion », ce qui entraîne une confusion avec le jeu de Morpion qui lui ressemble par ses mécanismes mais dont le but est de former des lignes de cinq et non de trois sur un espace quadrillé. Le Tic-tac-toe se joue sur une grille carrée de 3×3 cases. Deux joueurs s'affrontent, respectivement appelés « Joueur 1 » et « Joueur 2 ». Ils doivent remplir chacun à leur tour une case de la grille avec le symbole qui leur est attribué : O (aussi dénommé rond) ou X (appelé croix). Le gagnant est celui qui arrive à aligner trois symboles identiques, horizontalement, verticalement ou en diagonale.

En raison du nombre de combinaisons limité, l'analyse complète du jeu est facile à réaliser : si les deux joueurs jouent chacun de manière optimale, la partie doit toujours se terminer par un match nul.

Le tic-tac-toe donne un avantage assez important à celui qui commence. Des formes évoluées existent, comme le Gomoku ou le Pente, qui ajoutent à la notion d'alignement une notion de prise. Le renju prévoit des handicaps pour le joueur qui commence, ce qui permet d'équilibrer les chances des deux joueurs. Une partie dure environ une minute.

Dans la versoin que vous allez implémenter, les deux joueurs sont des humains, l’ordinateur ne joue pas ; il ne fait
que fournir le damier, la possibilité de cliquer sur les cases, l’affichage des deux signes (une croix et un rond) qui représentent les coups de chaque joueur et la détection de la fin de partie avec l’indication du joueur qui a gagné.

Par exemple, dans la partie montrée sur les figures ci après, le joueur 1 fait des croix, le joueur 2 des ronds. L'application indique qui doit jouer un coup et marque le coup joué avec l'icône (croix/rond) qui représente ce joueur. Au septième coup, le joueur 1 ayant aligné trois croix, a gagné la partie.

## Travail à réaliser

Dans la suite de ce sujet vous aurez à écrire deux classes importantes :
- un objet `MorpionJeu` contient les données manipulées par le programme indépendamment de la présentation graphique du jeu qui est faite par ailleurs,
- un objet `MorpionIHM` est une présentation graphique du jeu faite à l’intention des joueurs humains. 

Il y aura aussi plusieurs classes de moindre importance qui serviront d'outils pour les classes principales.

L'objectif de ce test est d'évaluer votre capacité à écrire une IHM à l'aide du langage Java, les méthodes complexes 
car trop algorithmiques n'auront pas à être implémentées. Vous pourrez retrouver une proposition de correction à l'adresse suivante : https://github.com/IUTInfoAix/TestIHM2016/

Le résultat attendu devra ressembler à la fenêtre suivante :

<img src="screenshoot.png" width="40%"/>
<img src="screenshoot2.png" width="40%"/>

## La classe `MorpionJeu`
A l’intérieur du programme les deux joueurs sont représentés par les entiers `0` et `1`. 
Cependant, sur l’interface graphique, le joueur `0` sera nommé « Joueur 1 » et le joueur `1` sera nommé « Joueur 2 ».

1. Ecrivez une classe `MorpionJeu` ayant, pour commencer, une seule donnée membre : 
   une variable d’instance privée, nommée `grille`, qui est une matrice (tableau à deux dimensions) dont les éléments seront de type `int`.
2. Ecrivez un constructeur public sans argument de la classe `MorpionJeu` qui devra :
  - créer la matrice `grille` de telle manière qu’elle comporte trois lignes de trois éléments chacune (matrice 3 x 3)
  - initialiser toutes les cases de la matrice `grille` avec la valeur -1. Par convention, cette valeur indiquera que la case est libre (c’est-à-dire qu’elle n’a pas encore été jouée par un des joueurs).
3. Ecrivez la méthode public `int autreJoueur(int joueur)` qui reçoit l’indice d’un des
   joueurs et renvoie l’indice de l’autre joueur. Autrement dit, `autreJoueur(0)` renvoie `1` et `autreJoueur(1)` renvoie `0`.
4. Ecrivez la méthode publique `String nomJoueur(int joueur)` qui reçoit l’indice d’un joueur et renvoie la chaîne de caractères qui le représente. Ainsi, `nomJoueur(0)` renvoie la chaîne `"Joueur 1"` et
`nomJoueur(1)` renvoie la chaîne `"Joueur 2"`.
5. Ecrivez la méthode publique `void enregistrerCoupJoueur(int i, int j, int joueur)` qui reçoit les indices `i` et `j` d’une case libre (c’est-à-dire contenant la valeur -1) et qui y enregistre l’indice du joueur indiqué.
6. Ecrivez la méthode publique `boolean grilleEstRemplie()` qui renvoie `true` ou `false` en réponse à la question « la grille est-elle entièrement remplie ? » (ce qui signifie qu’il n’y a alors plus de case à jouer).
7. Cette question bonus est plus complexe que les précédentes, nous vous conseillons de la laisser pour la fin.
Ecrivez la méthode publique `boolean aGagne(int joueur)` qui renvoie `true` ou `false` en réponse à la question « le joueur indiqué a-t-il gagné la partie (c’est-à-dire, a-t-il écrit trois marques alignées) ? »
Faites simple : pour une matrice de trois lignes et trois colonnes, ce n’est pas la peine de mettre au point des algorithmes savants, quelques tests exhaustifs suffisent.


## La classe MorpionIHM
L’interface de notre application (voyez les images montrées au début du sujet) se compose d’un cadre contenant au centre un panneau portant 9 boutons et en bas une barre d’état affichant un texte informatif.
Les boutons sont initialement tous sans icône et acquièrent, au cours de la partie, l’une ou l’autre des deux
icônes qui sont les marques des joueurs. La barre d’état affiche constamment un texte comme « Joueur 2, à vous », « Terminé : Joueur 1 a gagné », « Match nul », etc.

8. Ecrivez la déclaration d’une classe `MorpionIHM`, sous-classe de `JFrame`, réduite, pour commencer, à
ses variables d’instance, toutes privées :
  - `icones`, un tableau de deux éléments de type ImageIcon (destiné à contenir les deux icônes
qui sont les marques des joueurs),
  - `jeu`, de type `MorpionJeu`, pour accéder à la représentation interne du jeu, 
  - `barreEtat`, de type `JLabel`, destiné à désigner la barre d’état en bas de l’interface,
  - `joueurCourant`, de type `int`, pour désigner le joueur dont c’est le tour de jouer.
9. Ecrivez une méthode privée `void chargerIcones()` qui construit les deux éléments du tableau icones à partir de deux fichiers nommés respectivement "croix.jpg" et "rond.jpg". Ces icones, que vous pouvez voir sur les figures de la première page, seront utilisées pour marquer les coups des joueurs.
Vous pouvez supposer que ces fichiers sont placés dans le répertoire qu’il faut pour qu’il n’y ait pas besoin
d’ajouter au nom un chemin pour les atteindre depuis votre programme.
10. Pour réaliser le damier il nous faut "une sorte de" boutons qui se souviennent de leur position dans le
damier. Au moment de leur construction, de tels boutons reçoivent les valeurs des indices ligne et colonne
qui définissent leur placement dans la matrice et ils les mémoriseront dans des variables d’instance.
Ecrivez la classe `BoutonJeu` ayant les caractéristiques suivantes :
  - elle est sous-classe de `JButton`,
  - elle a deux variables d’instance privées de type `int` nommées `ligne` et `colonne`,
  - elle a un unique constructeur qui prend deux arguments `(ligne, colonne)` et les mémorise dans les variables d’instance correspondantes,
  - elle possède deux accesseurs `public int getLigne()` et `public int getColonne()` permettant d’obtenir les valeurs des variables privées.
11. On s’intéresse maintenant à ce qui doit se passer lorsqu’un joueur appuie sur un bouton. Pour cela,
vous allez écrire une classe implémentant l’interface `ActionListener`. Cette classe aura une unique
instance utilisée comme auditeur de tous les « événements action » produits par tous les boutons du jeu.
Ecrivez une classe `AuditeurJeu`, interne à la classe `MorpionIHM`, implémentant l’interface
`ActionListener`. Cette classe se réduit à la méthode imposée public `void actionPerformed(ActionEvent evt)`, qui doit effectuer les tâches suivantes :
  - identifier le bouton ayant produit l’événement (pensez à la méthode `getSource()` de l’objet `evt`),
  - changer l’aspect de ce bouton (méthode `setIcon` du bouton) : il était blanc, il doit prendre une
des deux icônes précédemment chargées, selon le joueur dont ça a été le tour de jouer,
  - désactiver le bouton (méthode `setEnabled`) afin qu’il ne puisse plus être cliqué,
  - enregistrer le coup joué dans la représentation interne de la partie (méthode `enregistrerCoupJoueur` de l’objet `jeu`),
  - déterminer si le joueur qui vient de jouer a gagné (méthode `aGagne`) et, si c’est le cas, afficher dans la barre d’état un texte l’annonçant puis quitter la méthode `actionPerformed`,
  - déterminer si la partie est finie (méthode `grilleEstRemplie`) et, si c’est le cas, afficher dans la barre d’état un texte l’annonçant puis quitter la méthode `actionPerformed`,
  - changer le joueur (méthode `autreJoueur`) dont c’est le tour et mettre à jour le message dans la barre d’état en fonction.
On rappelle que les méthodes d’une classe interne ont accès à tous les membres de la classe englobante.
En toute rigueur, lorsque la partie est terminée par la victoire de l’un ou l’autre joueur, ou devrait figer
l’interface afin qu’on ne puisse plus continuer à jouer. Vous pouvez cependant ignorer ici cette partie du
travail.
12. Ecrivez une méthode `private JPanel creerGrille(ActionListener auditeur)` qui
crée et renvoie un panneau avec les neuf boutons. Plus précisément, cette méthode
  - crée un panneau (objet `JPanel`) en lui donnant pour gestionnaire de disposition un
`GridLayout` à 3 lignes et 3 colonnes,
  - crée les 9 boutons (de type `BoutonJeu`) et les ajoute au panneau précédent,
  - à chaque bouton elle donne comme taille « préférée » 80 pixels sur 80 pixels,
  - à chaque bouton elle associe comme auditeur d’événements actions l’auditeur passé en argument de la méthode,
  - la fonction rend comme résultat le panneau qu’elle a créé.
13. Ecrivez le constructeur de la classe `MorpionIHM`, dont on rappelle qu’il s’agit d’une sous-classe de
`JFrame`. Ce constructeur doit se charger de :
  - afficher le texte du bandeau (par exemple « Jeu de Morpion ») et prendre les dispositions utiles
pour que le clic sur la case de fermeture du cadre termine effectivement le programme,
  - provoquer le chargement des icones,
  - provoquer la création d’un objet `MorpionJeu` qui sera la valeur de la variable jeu et d’un objet
`AuditeurJeu` qui sera la valeur d’une variable locale à introduire,
  - créer le panneau central par un appel de la méthode ad hoc, créer la barre d’état, par exemple de
type `JLabel`, et placer ces deux composants dans le cadre, l’un au centre et l’autre en bas.
  - enfin, « tasser » (`pack`) le cadre autour des composants qu’il contient, définir le joueur courant
(par exemple 0) et afficher dans la barre d’état un message l’invitant à jouer.
14. Ecrivez une méthode `main` aussi réduite que possible pour lancer l’exécution de tout cela.
