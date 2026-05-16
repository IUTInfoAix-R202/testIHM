# testIHM - Archive des tests d'IHM (M2105 / R2.02)

[![CI](https://github.com/IUTInfoAix-R202/testIHM/actions/workflows/ci.yml/badge.svg)](https://github.com/IUTInfoAix-R202/testIHM/actions/workflows/ci.yml)

Ce dépôt regroupe les sujets des **tests terminaux d'IHM Java** donnés entre **2012 et 2022** dans le département informatique de l'IUT d'Aix-Marseille. Il consolide en un seul lieu des sujets historiquement éclatés entre les organisations [`IUTInfoAix-M2105`](https://github.com/IUTInfoAix-M2105) (module pré-BUT) et [`IUTInfoAix-R202-archive`](https://github.com/IUTInfoAix-R202-archive) (transition vers le BUT).

L'archivage a été réalisé en mai 2026 dans le cadre de la refonte du module **R2.02 - Développement d'applications avec IHM** (voir [`IUTInfoAix-R202`](https://github.com/IUTInfoAix-R202) - cours, TPs, syllabus).

## Sujets archivés

| Dossier | Date | GUI | Thème | Notes |
|---|---|---|---|---|
| [`TestIHM2012`](TestIHM2012/) | mai 2012 | Swing/AWT | Démineur | **Sujet perdu** - aucune archive retrouvée lors de la consolidation 2026 |
| [`TestIHM2013`](TestIHM2013/) | 04/05/2013 | Swing/AWT | Morpion (Tic-tac-toe) | Implémentation Maven/Java 25 reconstituée à partir du sujet (les 14 questions) |
| [`TestIHM2014`](TestIHM2014/) | 02/06/2014 | Swing/AWT | Othello | Maven/Java 25, package `fr.univ_amu.iut.othello` |
| [`TestIHM2015`](TestIHM2015/) | 06/06/2015 | Swing/AWT | Mastermind | JUnit 3 |
| [`TestIHM2016`](TestIHM2016/) | 04/06/2016 | JavaFX | Tic-tac-toe | Première édition JavaFX, sujet 2013 re-skeletté  |
| [`TestIHM2017`](TestIHM2017/) | 16/06/2017 | JavaFX | Taquin | |
| [`TestIHM2018`](TestIHM2018/) | 09/06/2018 | JavaFX | Mastermind | |
| [`TestIHM2019`](TestIHM2019/) | 08/06/2019 | JavaFX | Lights Out | |
| [`TestIHM2020`](TestIHM2020/) | 12/06/2020 | JavaFX | Tracé de fonction sur intervalle | IHM reconstituée à partir du sujet (analyse + tracé + quadrillage + axes) |
| [`TestIHM2021`](TestIHM2021/) | 12/06/2021 | JavaFX | Problèmes arithmétiques + dessin de rectangles | 3 exercices |
| [`TestIHM2022`](TestIHM2022/) | (BUT 1) | JavaFX | Wordle | Dernier test du module M2105, premier R2.02 |

La bascule Swing → JavaFX dans le module s'est faite entre 2015 et 2016.

## Tests fonctionnels

Chaque dossier embarque ses propres tests JUnit 5 + AssertJ. Vérifiés en CI sous Xvfb (cf. badge ci-dessus).

| Dossier | Tests | Couverture |
|---|---:|---|
| [`TestIHM2013`](TestIHM2013/) | 8 | `MorpionJeu` (alignements, grille remplie) |
| [`TestIHM2014`](TestIHM2014/) | 15 | `Othellier` (capture, alternance, scores) + `Joueur` |
| [`TestIHM2015`](TestIHM2015/) | 12 | `Combinaison` + `Plateau` Mastermind (Swing headless) |
| [`TestIHM2016`](TestIHM2016/) | 7 | `Plateau` Tic-tac-toe (alignements, match nul) |
| [`TestIHM2017`](TestIHM2017/) | 12 | `Position` + `TaquinBoard` + smoke `AppTest` |
| [`TestIHM2018`](TestIHM2018/) | 12 | `Combinaison` + `Plateau` Mastermind + smoke `AppTest` |
| [`TestIHM2019`](TestIHM2019/) | 6 | `Plateau` Lights Out (toggle + voisins) |
| [`TestIHM2020`](TestIHM2020/) | 13 | `Analyseur` + `CalculateurPointsFonction` + `TraceurDeFonction` |
| [`TestIHM2021`](TestIHM2021/) | 6 | `Rectangle` (binding périmètre live) |
| [`TestIHM2022`](TestIHM2022/) | 25 | `Dictionary` + `Game` + `Word` (Wordle complet) |
| **Total** | **116** | tous verts en CI |

Pattern adopté pour TestFX (apps JavaFX 2016+) : `Platform.startup` + `Platform.runLater` + `CountDownLatch` (modèle TP3 bonus 10), avec `button.fire()` plutôt que `robot.clickOn()` pour éviter le robot OS (peu fiable sur Wayland et certains environnements headless). Apps Swing (2013-2015) : `-Djava.awt.headless=true` + `JButton.doClick()`.

## Difficulté et inspiration pour le CC3

Échelle subjective ★ (simple) à ★★★★ (capstone), à utiliser pour piocher un sujet pour le **CC3 R2.02** (mini-application JavaFX sur feuille, coeff. 40).

| Sujet | Difficulté | Public 1ère année BUT ? |
|---|---|---|
| Morpion (2013, 2016) | ★ | Idéal pour découverte |
| Lights Out (2019) | ★★ | Mécanique simple, joli rendu |
| Rectangle / Calculatrice (2021) | ★★ | Centré sur les bindings JavaFX |
| Othello (2014) | ★★★ | Logique de capture en directions |
| Taquin (2017) | ★★★ | Gestion d'état + animations possibles |
| Tracé de fonction (2020) | ★★★ | Parser fourni, focus sur la GUI |
| Mastermind (2015, 2018) | ★★★★ | Beaucoup de composants à coordonner |
| Wordle (2022) | ★★★★ | Vue, controller, modèle, dictionnaire |

## Stack technique

Tous les dossiers ont été retrofittés sur une stack homogène :

- **Java 25** (`maven.compiler.release=25`)
- **JavaFX 25** pour les apps JavaFX (`org.openjfx:javafx-controls:25` + `javafx-fxml` quand FXML utilisé)
- **`module-info.java`** dans chaque dossier (`fr.univ_amu.iut.TestIHM<année>`)
- **Maven Wrapper 3.9.14** (`./mvnw` dans chaque dossier - pas besoin de Maven installé localement)

Compilation et lancement (depuis le dossier d'un test) :

```bash
./mvnw clean compile        # compile
./mvnw javafx:run           # lance l'app JavaFX (apps 2016+)
./mvnw exec:java            # lance l'app Swing (2013, 2014, 2015)
./mvnw test                 # exécute les tests JUnit
```

Quelques particularités :

- `TestIHM2013`, `TestIHM2014` et `TestIHM2015` (Swing) utilisent `exec-maven-plugin` au lieu de `javafx-maven-plugin`.
- `TestIHM2022` requiert Ikonli (icônes FontAwesome + Typicons).

## Provenance

- 9 sujets proviennent de [`IUTInfoAix-M2105`](https://github.com/IUTInfoAix-M2105) :
  - `TestIHM2013` (anciennement `M2105/TestIHM2016` - le dossier ne contenait que le sujet 2013 réutilisé),
  - `TestIHM2014`, `TestIHM2015`,
  - `TestIHM2016` (anciennement `M2105/RattrapageIHM2019` - sujet 2013 re-skeletté en JavaFX en 2016, réutilisé en rattrapage en 2019),
  - `TestIHM2017`, `TestIHM2018`, `TestIHM2019`, `TestIHM2020`,
  - `TestIHM2021` (issu de `M2105/TestIHM2021-Solution`, plus complet que le sujet brut).
- 1 sujet provient de [`IUTInfoAix-R202-archive`](https://github.com/IUTInfoAix-R202-archive) : `TestIHM2022`.

L'historique git individuel de chaque ancien dépôt a été aplati lors de l'archivage : ce dépôt-ci ne contient que le snapshot final. Les dépôts originaux restent disponibles dans leurs organisations d'origine.

## Lien avec la refonte 2026

Ces sujets servent de base d'inspiration pour le **CC3** du module R2.02 (mini-application JavaFX sur feuille, coefficient 40). Ils illustrent la diversité possible des thèmes :

- **Jeux** : Othello, Taquin, Mastermind, Lights Out, Tic-tac-toe, Wordle
- **Application mathématique** : tracé de fonction
- **Application pédagogique** : problèmes arithmétiques

Le format historique - sujet papier + squelette Java avec quelques classes utilitaires + 2h sur papier - reste pertinent pour le nouveau CC3, en l'adaptant à la stack 2026 (Java 25, JavaFX 25, Maven Wrapper).
