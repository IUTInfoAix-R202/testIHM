#!/usr/bin/env python3
"""Prepare un README pour la generation PDF (voir outils/generer-pdf.sh).

- remplace le bloc Mermaid des zones par un tableau ;
- insere un \\needspace avant le tableau des zones et avant le bloc FXML pour
  qu'ils ne soient pas coupes en bas de page.

Le fichier source n'est pas modifie : on ecrit une copie temporaire.
"""
import re
import sys


def needspace(lignes):
    """Bloc LaTeX brut reservant `lignes` lignes (sinon saut de page)."""
    return "~~~{=latex}\n" + (r"\needspace{%d\baselineskip}" % lignes) + "\n~~~\n\n"


TABLEAU_ZONES = (
    "| | colonne 0 | colonne 1 | colonne 2 | colonne 3 |\n"
    "|---|:---:|:---:|:---:|:---:|\n"
    "| **ligne 0** | 0 | 0 | 1 | 1 |\n"
    "| **ligne 1** | 0 | 0 | 1 | 1 |\n"
    "| **ligne 2** | 2 | 2 | 3 | 3 |\n"
    "| **ligne 3** | 2 | 2 | 3 | 3 |\n"
)


def main():
    src, dst = sys.argv[1], sys.argv[2]
    texte = open(src, encoding="utf-8").read()
    # NB : remplacements via lambda pour que re.sub ne reinterprete pas les
    # antislashs du LaTeX (\n, \b...) dans la chaine de remplacement.
    # Le tableau des zones est une vraie table (pas un bloc de code) : on le
    # protege d'une coupure avec \needspace. Les blocs de code, eux, sont rendus
    # insecables globalement par le preambule LaTeX (cf. outils/generer-pdf.sh).
    texte = re.sub(r"```mermaid.*?```", lambda m: needspace(12) + TABLEAU_ZONES, texte, flags=re.S)
    open(dst, "w", encoding="utf-8").write(texte)


if __name__ == "__main__":
    main()
