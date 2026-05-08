package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import algorithmes.AlphaBeta;
import algorithmes.MinMax;

/**
 * Classe représentant le jeu
 * @author keita
 * @version 1.0
 */
public class Game {
    private String turn;
    private SquareType winner;
    private Scanner scanner;
    private Board boardSquares;
    private Pieces pieces;

    /**
     * Initialise le jeu
     * @author keita
     */
    public Game() {
        turn = "WHITE";
        winner = null;
        pieces = new Pieces();
        boardSquares = new Board();
        scanner = new Scanner(System.in);
    }

    /**
     * Démarre le jeu pour deux joueurs humain.
     * @author keita
     */
    public void runHumainVsHumain() {
        int nb_turns = 0;
        while(!isGameOver()) {
            boardSquares.printBoard();
            System.out.println("Tour " + nb_turns + " : " + turn + " joue.");

            List<Square> playerMove = getPlayerMove();
            if (validateMove(playerMove)){
                makeMove(playerMove);
                nb_turns++;
            } else {
                System.out.println("Mouvement invalide. Veuillez réessayer.");
            }
        }

        afficherFinDePartie();
    }

    /**
     * Démarre le jeu pour un joueur humain (WHITE) contre l'IA (BLACK).
     * @param niveauIA niveau de difficulté de l'IA (1, 2 ou 3)
     */
    public void runHumainVsAI(int niveauIA) {
        int nb_tours = 0;
        while (!isGameOver()) {
            boardSquares.printBoard();
            System.out.println("Tour " + nb_tours + " : " + turn + " joue.");

            switch (turn) {
                case "WHITE":
                    List<Square> playerMove = getPlayerMove();
                    if (validateMove(playerMove)) {
                        makeMove(playerMove);
                        nb_tours++;
                    } else {
                        System.out.println("Mouvement invalide. Veuillez réessayer.");
                    }
                    break;

                case "BLACK":
                    if (jouerIA(niveauIA)) nb_tours++;
                    break;

                default:
                    break;
            }
        }

        afficherFinDePartie();
    }

    /**
     * Démarre le jeu pour deux IA qui s'affrontent avec des niveaux potentiellement différents.
     * @param niveauBlanc niveau de difficulté de l'IA WHITE (1, 2 ou 3)
     * @param niveauNoir  niveau de difficulté de l'IA BLACK (1, 2 ou 3)
     */
    public void runAIvsAI(int niveauBlanc, int niveauNoir) {
        int nb_tours = 0;
        while (!isGameOver()) {
            boardSquares.printBoard();
            System.out.println("Tour " + nb_tours + " : " + turn + " joue.");

            int niveau = turn.equals("WHITE") ? niveauBlanc : niveauNoir;
            if (jouerIA(niveau)) {
                nb_tours++;
            } else {
                System.out.println(turn + " n'a plus de coups possibles.");
                break;
            }
        }

        afficherFinDePartie();
    }

    /**
     * Fait jouer l'IA pour le joueur courant (turn) avec le niveau donné.
     * @param niveau niveau de difficulté (1, 2 ou 3)
     * @return true si un coup a été joué, false sinon.
     */
    private boolean jouerIA(int niveau) {
        Move aiMove = AlphaBeta.run(boardSquares, turn, niveau);
        if (aiMove == null) return false;

        int fromIdx = aiMove.getFromCase().getX() * boardSquares.getCol() + aiMove.getFromCase().getY();
        int toIdx   = aiMove.getToCase().getX()   * boardSquares.getCol() + aiMove.getToCase().getY();

        List<Square> aiPlayerMove = new ArrayList<>();
        aiPlayerMove.add(boardSquares.get(fromIdx));
        aiPlayerMove.add(boardSquares.get(toIdx));

        System.out.println(turn + " (IA niv." + niveau + ") joue : "
            + toNotation(boardSquares.get(fromIdx))
            + " -> "
            + toNotation(boardSquares.get(toIdx)));

        makeMove(aiPlayerMove);
        return true;
    }

    /**
     * Affiche le plateau final et le vainqueur.
     */
    private void afficherFinDePartie() {
        boardSquares.printBoard();
        System.out.println("Le jeu est terminé!");
        if (winner != null)
            System.out.println("VICTOIRE DES " + winner.toString().toUpperCase() + " !");
        else
            System.out.println("Match nul !");
    }

    /**
     * Vérifie si le mouvement est légal ou non
     * @param playerMove le mouvement du joueur
     * @return true s'il est légal, false sinon
     * @author keita
     */
    public boolean validateMove(List<Square> playerMove) {
        if (playerMove == null || playerMove.size() != 2)
            return false;

        Square sc = playerMove.get(0);
        Square dt = playerMove.get(1);

        if (sc.getType() != SquareType.valueOf(turn))
            return false;

        int dx = dt.getX() - sc.getX();
        int dy = Math.abs(dt.getY() - sc.getY());
        int forward = (turn.equals("WHITE")) ? -1 : 1;

        if (dx != forward)
            return false;

        if (dy == 0)
            return dt.getType() == SquareType.EMPTY;

        if (dy == 1)
            return dt.getType() != SquareType.EMPTY &&
                dt.getType() != sc.getType();

        return false;
    }

    /**
     * Modifie le plateau en appliquant le mouvement
     * @param playerMove le mouvement du joueur
     * @author keita
     */
    public void makeMove(List<Square> playerMove) {
        Square fromSquare = playerMove.get(0);
        Square toSquare = playerMove.get(1);

        if (toSquare.getType() == SquareType.EMPTY) {
            toSquare.setType(fromSquare.getType());
            fromSquare.setType(SquareType.EMPTY);
        } else {
            toSquare.setType(fromSquare.getType());
            fromSquare.setType(SquareType.EMPTY);
            if (turn.equals("WHITE")) {
                pieces.setNbBlackPieces(pieces.getNbBlackPieces() - 1);
            } else {
                pieces.setNbWhitePieces(pieces.getNbWhitePieces() - 1);
            }
        }

        turn = turn.equals("WHITE") ? "BLACK" : "WHITE";
    }

    /**
     * Récupère le mouvement saisi par le joueur humain
     * @return la liste [caseDepart, caseArrivee]
     * @author keita
     */
    private List<Square> getPlayerMove() {
        List<Square> playerMove = new ArrayList<>();
        System.out.print("Entrez votre mouvement (ex: a2 a3) : ");

        String[] positions = scanner.nextLine().trim().split("\\s+");

        try {
            int from_J = positions[0].charAt(0) - 'a';
            int from_I = Integer.parseInt(positions[0].substring(1)) - 1;
            int to_J = positions[1].charAt(0) - 'a';
            int to_I = Integer.parseInt(positions[1].substring(1)) - 1;

            Square fromSquare = boardSquares.get(from_I * boardSquares.getCol() + from_J);
            Square toSquare   = boardSquares.get(to_I   * boardSquares.getCol() + to_J);
            playerMove.add(fromSquare);
            playerMove.add(toSquare);

        } catch (Exception e) {
            System.out.println("Entrée invalide. Veuillez réessayer.");
        }

        return playerMove;
    }

    /**
     * Vérifie si la partie est terminée
     * @return true si la partie est finie, false sinon
     * @author keita
     */
    private boolean isGameOver() {
        // Condition 1 : un joueur n'a plus de pions
        if (pieces.getNbBlackPieces() == 0) {
            winner = SquareType.WHITE;
            return true;
        }
        if (pieces.getNbWhitePieces() == 0) {
            winner = SquareType.BLACK;
            return true;
        }

        // Condition 2 : un joueur a atteint la dernière rangée adverse
        for (int j = 0; j < boardSquares.getCol(); j++) {
            if (boardSquares.get(0 * boardSquares.getCol() + j).getType() == SquareType.WHITE) {
                winner = SquareType.WHITE;
                return true;
            }
            if (boardSquares.get(7 * boardSquares.getCol() + j).getType() == SquareType.BLACK) {
                winner = SquareType.BLACK;
                return true;
            }
        }

        // Condition 3 : le joueur courant n'a plus aucun coup possible
        List<Square> squares = boardSquares.getboardSquares();
        if (AlphaBeta.getActions(squares, turn).isEmpty()) {
            // Le joueur courant est bloqué : l'adversaire gagne
            winner = SquareType.valueOf(turn.equals("WHITE") ? "BLACK" : "WHITE");
            return true;
        }

        return false;
    }

    /**
     * Convertit une case en notation lisible ex: "a1", "b3"
     */
    public static String toNotation(Square c) {
        char col = (char) ('a' + c.getY());
        int row = c.getX() + 1;
        return "" + col + row;
    }

    public String getTurn() { return turn; }
    public void setTurn(String turn) { this.turn = turn; }
    public SquareType getWinner() { return winner; }
    public void setWinner(SquareType winner) { this.winner = winner; }
    public Board getBoard() { return boardSquares; }
}
