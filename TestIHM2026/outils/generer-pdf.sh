#!/usr/bin/env bash
# Genere les versions PDF des sujets via pandoc + xelatex.
#
# Deux adaptations pour le rendu PDF (le .md d'origine n'est pas modifie) :
#   - le bloc Mermaid des zones (R203) est remplace par un tableau, car pandoc
#     ne sait pas rendre Mermaid sans filtre dedie ;
#   - les images sont bornees a 70 % de la largeur du texte.
#
# Lancer depuis n'importe ou. Sortie : build/pdf/.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"
mkdir -p build/pdf

preprocess() { # $1 = md source, $2 = md temporaire
  python3 - "$1" "$2" <<'PY'
import sys, re
src, dst = sys.argv[1], sys.argv[2]
texte = open(src, encoding="utf-8").read()
tableau = (
    "| | colonne 0 | colonne 1 | colonne 2 | colonne 3 |\n"
    "|---|:---:|:---:|:---:|:---:|\n"
    "| **ligne 0** | 0 | 0 | 1 | 1 |\n"
    "| **ligne 1** | 0 | 0 | 1 | 1 |\n"
    "| **ligne 2** | 2 | 2 | 3 | 3 |\n"
    "| **ligne 3** | 2 | 2 | 3 | 3 |\n"
)
texte = re.sub(r"```mermaid.*?```", tableau, texte, flags=re.S)
open(dst, "w", encoding="utf-8").write(texte)
PY
}

build() { # $1 = md source, $2 = pdf de sortie
  local tmp="build/pdf/.$(basename "$1").tmp.md"
  preprocess "$1" "$tmp"
  pandoc "$tmp" \
    --from gfm \
    --pdf-engine=xelatex \
    --resource-path=. \
    -V geometry:margin=2cm \
    -V fontsize=11pt \
    -V colorlinks=true -V linkcolor=blue -V urlcolor=blue \
    -V header-includes='\usepackage{graphicx}\setkeys{Gin}{width=0.7\linewidth,keepaspectratio}' \
    -o "build/pdf/$2"
  rm -f "$tmp"
  echo "OK -> build/pdf/$2"
}

build README.R203.md TestIHM2026-R203.pdf
build README.R202.md TestIHM2026-R202.pdf
build README.md      TestIHM2026.pdf
