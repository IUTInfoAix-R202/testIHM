# testIHM - Archive des tests d'IHM (M2105 / R2.02)

Ce dépôt regroupe les sujets des **tests terminaux d'IHM Java** donnés entre **2013 et 2022** dans le département informatique de l'IUT d'Aix-Marseille. Il consolide en un seul lieu des sujets historiquement éclatés entre les organisations [`IUTInfoAix-M2105`](https://github.com/IUTInfoAix-M2105) (module pré-BUT) et [`IUTInfoAix-R202-archive`](https://github.com/IUTInfoAix-R202-archive) (transition vers le BUT).

L'archivage a été réalisé en mai 2026 dans le cadre de la refonte du module **R2.02 - Développement d'applications avec IHM** (voir [`IUTInfoAix-R202`](https://github.com/IUTInfoAix-R202)).

## Sujets archivés

| Dossier | Date | GUI | Thème | Notes |
|---|---|---|---|---|
| [`TestIHM2013`](TestIHM2013/) | 04/05/2013 | Swing/AWT | Morpion (Tic-tac-toe) | Implémentation Maven/Java 25 reconstituée à partir du sujet (les 14 questions) |
| [`TestIHM2014`](TestIHM2014/) | 02/06/2014 | Swing/AWT | Othello | Maven/Java 25, package `fr.univ_amu.iut.othello` |
| [`TestIHM2015`](TestIHM2015/) | 06/06/2015 | Swing/AWT | Mastermind | JUnit 3 |
| [`TestIHM2016`](TestIHM2016/) | 04/06/2016 | JavaFX | Tic-tac-toe | Première édition JavaFX, sujet 2013 re-skeletté ; **réutilisé tel quel comme rattrapage le 21/06/2019** |
| [`TestIHM2017`](TestIHM2017/) | 16/06/2017 | JavaFX | Taquin | |
| [`TestIHM2018`](TestIHM2018/) | 09/06/2018 | JavaFX | Mastermind | |
| [`TestIHM2019`](TestIHM2019/) | 08/06/2019 | JavaFX | Lights Out | |
| [`TestIHM2020`](TestIHM2020/) | 12/06/2020 | JavaFX | Tracé de fonction sur intervalle | Squelette modèle uniquement, GUI à écrire par l'étudiant |
| [`TestIHM2021`](TestIHM2021/) | 12/06/2021 | JavaFX | Problèmes arithmétiques + dessin de rectangles | 3 exercices, **version solution** conservée |
| [`TestIHM2022`](TestIHM2022/) | (BUT 1) | JavaFX | Wordle | Dernier test du module M2105, premier R2.02 |

La bascule Swing → JavaFX dans le module s'est faite entre 2015 et 2016.

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
