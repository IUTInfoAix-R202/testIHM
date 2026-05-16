# testIHM - Archive des tests d'IHM (M2105 / R2.02)

Ce dépôt regroupe les sujets des **tests terminaux d'IHM Java** donnés entre **2014 et 2022** dans le département informatique de l'IUT d'Aix-Marseille. Il consolide en un seul lieu des sujets historiquement éclatés entre les organisations [`IUTInfoAix-M2105`](https://github.com/IUTInfoAix-M2105) (module pré-BUT) et [`IUTInfoAix-R202-archive`](https://github.com/IUTInfoAix-R202-archive) (transition vers le BUT).

L'archivage a été réalisé en mai 2026 dans le cadre de la refonte du module **R2.02 - Développement d'applications avec IHM** (voir [`IUTInfoAix-R202`](https://github.com/IUTInfoAix-R202)).

## Sujets archivés

| Dossier | Date | Thème | Notes |
|---|---|---|---|
| [`TestIHM2014`](TestIHM2014/) | 02/06/2014 | Othello | Pas de structure Maven (code historique) |
| [`TestIHM2015`](TestIHM2015/) | 06/05/2015 | Mastermind | |
| [`TestIHM2016`](TestIHM2016/) | 04/06/2016 | (non précisé) | Dépôt sans code Java, sujet PDF seul |
| [`TestIHM2017`](TestIHM2017/) | 16/06/2017 | Taquin | |
| [`TestIHM2018`](TestIHM2018/) | 09/06/2018 | Mastermind | |
| [`TestIHM2019`](TestIHM2019/) | 08/06/2019 | Lights Out | |
| [`RattrapageIHM2019`](RattrapageIHM2019/) | 21/06/2019 | Tic-tac-toe | Rattrapage |
| [`TestIHM2020`](TestIHM2020/) | 12/06/2020 | Tracé de fonction sur intervalle | |
| [`TestIHM2021`](TestIHM2021/) | 12/06/2021 | Problèmes arithmétiques + dessin de rectangles | 3 exercices, **version solution** conservée |
| [`TestIHM2022`](TestIHM2022/) | (BUT 1) | Wordle | Dernier test du module M2105, premier R2.02 |

## Provenance

- 9 sujets proviennent de [`IUTInfoAix-M2105`](https://github.com/IUTInfoAix-M2105) : `TestIHM2014`, `2015`, `2016`, `2017`, `2018`, `2019`, `2020`, `2021` (issu de `TestIHM2021-Solution`, plus complet que le sujet brut), plus le `RattrapageIHM2019`.
- 1 sujet provient de [`IUTInfoAix-R202-archive`](https://github.com/IUTInfoAix-R202-archive) : `TestIHM2022`.

L'historique git individuel de chaque ancien dépôt a été aplati lors de l'archivage : ce dépôt-ci ne contient que le snapshot final. Les dépôts originaux restent disponibles dans leurs organisations d'origine.

## Lien avec la refonte 2026

Ces sujets servent de base d'inspiration pour le **CC3** du module R2.02 (mini-application JavaFX sur feuille, coefficient 40). Ils illustrent la diversité possible des thèmes :

- **Jeux** : Othello, Taquin, Mastermind, Lights Out, Tic-tac-toe, Wordle
- **Application mathématique** : tracé de fonction
- **Application pédagogique** : problèmes arithmétiques

Le format historique - sujet papier + squelette Java avec quelques classes utilitaires + 2h de TP - reste pertinent pour le nouveau CC3, en l'adaptant à la stack 2026 (Java 25, JavaFX 25, Maven Wrapper).
