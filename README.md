# PROJET_AI
Ce projet implémente le jeu à somme nulle Breakthrough (La Percée) avec une intelligence artificielle capable d’affronter un joueur humain. L’IA repose sur l’algorithme Alpha-Beta (variante optimisée de Minimax), avec trois niveaux de difficulté basés sur la profondeur de recherche et trois fonctions d’évaluation différentes.

## Les Règles du jeu la percée

- Chaque joueur dispose de 16 pions.
- Les pions sont placés sur les deux premières rangées de chaque côté du plateau.
- Les joueurs jouent à tour de rôle.
- Les Blancs commencent la partie (par convention).
- Un pion peut avancer d’une case vers l’avant, uniquement si la case est libre.
- Un pion ne peut ni reculer ni se déplacer latéralement.
- Un pion capture une pièce adverse en diagonale avant, sur une seule case.
- Les captures ne sont possibles que sur une case occupée par l’adversaire.
- Les pions ne peuvent pas sauter par-dessus d’autres pions.
- Il n’existe aucune promotion lorsqu’un pion atteint la dernière rangée.
- Il n’y a pas de prise en passant.
- Il n’y a pas de coups spéciaux ou de pièces autres que les pions.
- Un joueur gagne s’il atteint la dernière rangée adverse avec un pion.
- Un joueur gagne s’il capture tous les pions adverses.
- Un joueur gagne si l’adversaire n’a plus de coup possible.
- Le jeu est à information parfaite (aucun hasard).
- Le jeu est à somme nulle (le gain de l’un est la perte de l’autre).
- La partie ne peut pas se terminer par une égalité dans les règles standards.
- Le jeu est bien adapté aux algorithmes Minimax et Alpha-Beta.

## Objectifs du projet

- Implémenter le jeu La Percée (Breakthrough) avec une interface en ligne de commande permettant de jouer contre une IA ou de faire s’affronter deux IA.
- Proposer trois niveaux de difficulté basés sur la profondeur de recherche et la fonction d’évaluation.
- Réaliser une analyse comparative à travers un tournoi opposant différentes configurations d’IA.

## Algorithmes et fonctions d’évaluation

### Algorithmes
- **MinMax** : algorithme Minimax classique (implémenté dans `MinMax.java`)
- **Alpha-Beta** : variante optimisée de Minimax avec élagage Alpha-Beta (utilisé en jeu et en tournoi)

### Fonctions d’évaluation (dans `Evaluation.java`)
| Niveau | Fonction | Description |
|--------|----------|-------------|
| 1 - Facile | `evalMateriel` | Différence du nombre de pions entre les deux joueurs |
| 2 - Intermédiaire | `evalPositionnelle` | Avancement des pions, cases libres devant, menaces adverses |
| 3 - Difficile | `evalStrategique` | Positionnelle + pion passé + victoire imminente + contrôle du centre + formation phalanx |

### Détail de evalStrategique
La fonction d’évaluation stratégique intègre 5 heuristiques cumulatives :
1. Avancement + cases libres devant + menaces adverses
2. **Pion passé** : aucun adversaire devant lui dans sa colonne et colonnes adjacentes (+3)
3. **Victoire imminente** : pion à une seule case de la dernière rangée adverse (+50)
4. **Contrôle du centre** : pion sur une des 4 colonnes centrales c, d, e, f (+1)
5. **Formation phalanx** : pion ayant un allié directement à gauche ou à droite (+0.5 par voisin)

## Structure du projet

```
PROJET_AI/
├── bin/                                # Fichiers binaires de la compilation
├── lib/                                # Bibliothèques JAR (JUnit)
├── src/
│   ├── main/
│   │   ├── AppMain.java               # Point d’entrée du programme
│   │   ├── Game.java                  # Gestion du déroulement du jeu
│   │   ├── Board.java                 # Plateau de jeu (grille 8x8)
│   │   ├── Square.java                # Représentation d’une case
│   │   ├── SquareType.java            # Enumération : EMPTY, BLACK, WHITE
│   │   ├── Move.java                  # Représentation d’un coup
│   │   ├── Pieces.java                # Compteur des pièces en jeu
│   │   └── menu/
│   │       └── Menu.java              # Menus de sélection en ligne de commande
│   ├── algorithmes/
│   │   ├── AlphaBeta.java             # Algorithme Alpha-Beta (utilisé en jeu et tournoi)
│   │   ├── MinMax.java                # Algorithme Minimax classique
│   │   └── Evaluation.java            # Fonctions d’évaluation (materiel, positionnel, strategique)
│   └── tournoi_ai/
│       ├── Tournoi.java               # Gestion du tournoi IA vs IA
│       └── IAConfig.java              # Configuration d’une IA (profondeur + fonction d’évaluation)
├── test/
│   ├── AppMain.java                   # Lancement des tests
│   ├── BoardTest.java                 # Tests du plateau
│   ├── PiecesTest.java                # Tests des pièces
│   ├── SquareTest.java                # Tests des cases
│   ├── gameTest.java                  # Tests du jeu
│   └── moveTest.java                  # Tests des coups
└── documentation/                     # Javadoc générée
```

## Modes de jeu

| Mode | Description |
|------|-------------|
| Humain vs Humain | Deux joueurs humains s’affrontent en saisissant leurs coups |
| Humain vs IA | Le joueur humain (WHITE) affronte l’IA (BLACK) avec le niveau choisi |
| IA vs IA | Deux IA s’affrontent avec des niveaux potentiellement différents |

## Comment Exécuter

Depuis le dossier `PROJET_AI` :

1. Compiler :
```bash
javac -d ./bin -sourcepath src src/main/AppMain.java
```

2. Exécuter :
```bash
java -cp ./bin main.AppMain
```

3. Pour lancer le tournoi :
```bash
java -cp ./bin tournoi_ai.Tournoi
```

## Binôme du Projet

Projet réalisé par un binôme de L3 :

- Laye Fodé Keita
- Jordan Crisosto