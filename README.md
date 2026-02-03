# PROJET_AI
Ce projet implémente le jeu à somme nulle Breakthrough (La Percée) avec une intelligence artificielle capable d’affronter un joueur humain. L’IA repose sur des algorithmes de décision comme Minimax, avec plusieurs niveaux de difficulté basés sur la profondeur de recherche et la stratégie.

## Les Règles du jeu la percée:
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
- Un joueur gagne si l’adversaire n’a plus de pion ou a atteint la dernière ligne de la case de l'adversaire.0
- Le jeu est à information parfaite (aucun hasard).
- Le jeu est à somme nulle (le gain de l’un est la perte de l’autre).
- La partie ne peut pas se terminer par une égalité dans les règles standards.
- Le jeu est bien adapté aux algorithmes Minimax et Alpha-Beta. 

## Objectifs du projet

- Implémenter le jeu La Percée (Breakthrough) avec une interface permettant de jouer contre une intelligence artificielle.

- Proposer trois algorithmes d’IA représentant des niveaux de difficulté différents.

- Réaliser une analyse comparative à travers un tournoi opposant chaque paire d’IA, afin d’évaluer leurs performances respectives.

## Structure du projet 

```
PROJET_AI/
├── bin                                 # Les fichier binaires de la compilation
├── lib                                 # Les bibliothèques des jar utilisés.
├──src/
    ├── main/
    │    ├── Game.java                  # Gestion du jeu
    │    ├── Case.java                  # Implementation d'une case du jeu
    │    ├── CaseType.java              # Enumeration du type d'une case (vide, blanc, noir)
    │    ├── Move.jave                  # Une action d'un joueur sur le plateau.
    ├── ressources/                     # Dossier des res du projet  
    ├── Gui/                            # Dossier de l'interface graphique 
    ├── algorithmes/                    # Dossier des algorithmes d'IA
        ├──  MiniMax.java
├──test/
    └── AppMain.java                    # Lancement du programme
```

## Comment Exécuter

Depuis dans le dossier `Projet_IA`
1. Exécutez la commande : `javac -d ./bin .\src\main\*.java .\test\AppMain.java`
2. Puis la commande : `java -cp ./bin .\test\AppMain.java`


## binôme du Projet

Projet réalisé par un binôme de L3 :

- Jordan Crisosto

- Laye Fodé Keita