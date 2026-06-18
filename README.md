# testIHM - Archive des tests d'IHM (M2105 / R2.02)

[![CI](https://github.com/IUTInfoAix-R202/testIHM/actions/workflows/ci.yml/badge.svg)](https://github.com/IUTInfoAix-R202/testIHM/actions/workflows/ci.yml)

Ce dépôt regroupe les sujets des **tests terminaux d'IHM Java** donnés entre **2012 et 2022** dans le département informatique de l'IUT d'Aix-Marseille. Il consolide en un seul lieu des sujets historiquement éclatés entre les organisations [`IUTInfoAix-M2105`](https://github.com/IUTInfoAix-M2105) (module pré-BUT) et [`IUTInfoAix-R202-archive`](https://github.com/IUTInfoAix-R202-archive) (transition vers le BUT).

L'archivage a été réalisé en mai 2026 dans le cadre de la refonte du module **R2.02 - Développement d'applications avec IHM** (voir [`IUTInfoAix-R202`](https://github.com/IUTInfoAix-R202) - cours, TPs, syllabus).

Depuis juin 2026, le dépôt héberge aussi le **sujet du CC3 2026** (`TestIHM2026`, jeu *Quantik*) : ce n'est pas un sujet historique mais la **correction de référence** du test terminal de l'année en cours, sur la stack la plus récente (JavaFX 26 + Headless Platform). Il a rejoint le dépôt en conservant son historique git propre (l'ancien dépôt autonome `IUTInfoAix-R202/TestIHM2026` est désormais archivé en lecture seule).

## Sujets archivés

| Dossier | Date | GUI | Thème | Notes |
|---|---|---|---|---|
| [`TestIHM2012`](TestIHM2012/) | mai 2012 | Swing/AWT | Démineur | **Sujet perdu** - aucune archive retrouvée lors de la consolidation 2026 |
| [`TestIHM2013`](TestIHM2013/) | 04/05/2013 | Swing/AWT | Morpion (Tic-tac-toe) | Implémentation reconstituée à partir du sujet (les 14 questions) |
| [`TestIHM2014`](TestIHM2014/) | 02/06/2014 | Swing/AWT | Othello | |
| [`TestIHM2015`](TestIHM2015/) | 06/06/2015 | Swing/AWT | Mastermind | |
| [`TestIHM2016`](TestIHM2016/) | 04/06/2016 | JavaFX | Tic-tac-toe | Première édition JavaFX, sujet 2013 re-skeletté  |
| [`TestIHM2017`](TestIHM2017/) | 16/06/2017 | JavaFX | Taquin | |
| [`TestIHM2018`](TestIHM2018/) | 09/06/2018 | JavaFX | Mastermind | |
| [`TestIHM2019`](TestIHM2019/) | 08/06/2019 | JavaFX | Lights Out | |
| [`TestIHM2020`](TestIHM2020/) | 12/06/2020 | JavaFX | Tracé de fonction sur intervalle | IHM reconstituée à partir du sujet (analyse + tracé + quadrillage + axes) |
| [`TestIHM2021`](TestIHM2021/) | 12/06/2021 | JavaFX | Problèmes arithmétiques + dessin de rectangles | 3 exercices |
| [`TestIHM2022`](TestIHM2022/) | juin 2022 | JavaFX | Wordle | Premier test du BUT (R2.02 + R2.03), 2 sous-sujets dans le même repo |
| [`TestIHM2026`](TestIHM2026/) | 18/06/2026 | JavaFX 26 | Quantik (MVVM) | **Sujet CC3 2026 actif**, pas un sujet historique : correction de référence R2.02 + R2.03, 2 sous-sujets (`README.R202.md` / `README.R203.md`). Pom autonome JavaFX 26 + Headless |

La bascule Swing → JavaFX dans le module s'est faite entre 2015 et 2016.

## Tests fonctionnels

Chaque dossier embarque ses propres tests JUnit Jupiter + AssertJ (versions définies dans le `pom.xml` racine via `<dependencyManagement>`, sauf `TestIHM2026` qui est autonome). Vérifiés en CI sous Xvfb (cf. badge ci-dessus) ; `TestIHM2026` tourne via la *Headless Platform* Gluon de JavaFX 26 (sans serveur d'affichage), Xvfb ne lui sert que de filet.

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
| [`TestIHM2026`](TestIHM2026/) | 58 | modèle Quantik (`Plateau`, `Partie`, `Piece`, `Reserve`) + `QuantikViewModel` + `PieceRenderer` + smoke `QuantikAppTest` |
| **Total** | **174** | tous verts en CI |

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

Le dépôt est un **projet Maven multi-module** depuis 2026-05 : le `pom.xml` racine est à la fois agrégateur (`<modules>`) et parent (`<dependencyManagement>` + `<pluginManagement>`). Les **10 modules d'archive** `TestIHM2013`→`TestIHM2022` héritent du parent et n'ont à déclarer ni versions, ni configurations de plugins. Stack homogène centralisée :

- **Java 25** (`maven.compiler.release=25`)
- **JavaFX 25** pour les apps JavaFX (`javafx-controls` + `javafx-fxml` quand FXML utilisé)
- **JUnit Jupiter 6** + **AssertJ 3.27** + **TestFX 4.0.18** pour les tests
- **`module-info.java`** dans chaque module (`fr.univ_amu.iut.TestIHM<année>`)
- **Maven Wrapper 3.9.14** : `./mvnw` à la racine pour tout faire d'un coup, et dans chaque module pour le travailler isolément (pas besoin de Maven installé localement)

Compilation et lancement :

```bash
./mvnw test                       # à la racine : teste les 11 modules d'un coup
./mvnw -pl TestIHM2014 test       # à la racine : teste un seul module
cd TestIHM2014 && ./mvnw test     # depuis un module : équivalent autonome
cd TestIHM2016 && ./mvnw javafx:run   # lance l'app JavaFX (apps 2016+)
cd TestIHM2014 && ./mvnw exec:java    # lance l'app Swing (2013, 2014, 2015)
```

Quelques particularités :

- `TestIHM2013`, `TestIHM2014` et `TestIHM2015` (Swing) utilisent `exec-maven-plugin` au lieu de `javafx-maven-plugin`.
- `TestIHM2022` requiert Ikonli (icônes FontAwesome + Typicons), déclaré aussi dans le `dependencyManagement` racine.
- `TestIHM2026` est le **11e module, agrégé mais NON héritant** : il garde son propre pom autonome (**JavaFX 26 + Headless Platform Gluon**, Mockito, ApprovalTests, Spotless), car la Headless Platform n'existe qu'en JavaFX 26 et serait cassée par le JavaFX 25.0.3 du parent. Ses versions ne sont donc pas centralisées dans le `pom.xml` racine.
- Le bump d'une dépendance (Java, JavaFX, JUnit...) des modules d'archive se fait dans **un seul endroit** : le `<properties>` du `pom.xml` racine (`TestIHM2026` se met à jour séparément, dans son propre pom).

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
