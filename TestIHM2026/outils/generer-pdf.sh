#!/usr/bin/env bash
# Genere les versions PDF des sujets via pandoc + xelatex.
#
# Adaptations pour le rendu PDF (le .md d'origine n'est pas modifie) :
#   - le bloc Mermaid des zones (R203) est remplace par un tableau, car pandoc
#     ne sait pas rendre Mermaid sans filtre dedie ;
#   - les images sont bornees a 70 % de la largeur du texte ;
#   - un \needspace est insere avant le tableau des zones et avant le bloc FXML
#     pour qu'ils ne soient jamais coupes en bas de page (ils basculent entiers
#     sur la page suivante si la place manque).
#
# Lancer depuis n'importe ou. Sortie : build/pdf/.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"
mkdir -p build/pdf

preprocess() { # $1 = md source, $2 = md temporaire
  python3 "$ROOT/outils/preparer-md.py" "$1" "$2"
}

build() { # $1 = md source, $2 = pdf de sortie
  local tmp="build/pdf/.$(basename "$1").tmp.md"
  preprocess "$1" "$tmp"
  pandoc "$tmp" \
    --from gfm+raw_attribute \
    --pdf-engine=xelatex \
    --resource-path=. \
    -V geometry:margin=2cm \
    -V fontsize=11pt \
    -V colorlinks=true -V linkcolor=blue -V urlcolor=blue \
    -V header-includes='\usepackage{graphicx}\usepackage{needspace}\setkeys{Gin}{width=0.7\linewidth,keepaspectratio}' \
    -o "build/pdf/$2"
  rm -f "$tmp"
  echo "OK -> build/pdf/$2"
}

build README.R203.md TestIHM2026-R203.pdf
build README.R202.md TestIHM2026-R202.pdf
build README.md      TestIHM2026.pdf
