package main.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.square.Square;
import main.square.SquareType;

import main.board.*;
import main.pieces.*;

/**
 * Classe représentant le jeu
 * @author Sogolon88
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
     * @author Sogolon88
     */
    public Game() {
        turn = "WHITE";
        winner = null;
        pieces = new Pieces();
        boardSquares = new Board();
        scanner = new Scanner(System.in);
    }

    /**
     * Démarre le jeu
     * @author Sogolon88
     */
    public void run() {
        int nb_turns = 0;
        while(!isGameOver()) {   
            boardSquares.printBoard();
            System.out.println("Tour " + nb_turns + " : " + turn + " joue.");
            
            List<Square> playerMove = getPlayerMove();
            if ( validateMove(playerMove)){
                makeMove( playerMove );
                nb_turns++;
            } else {
                System.out.println("Mouvement invalide. Veuillez réessayer.");
            }
        }
        
        System.out.println("Le jeu est terminé!");
        System.out.println("VICTOIRE DES " + winner.toString().toUpperCase() + " !");
    }

    /**
     * Vérifie si le mouvement est légal ou non
     * @param playerMove le mouvement du joueur
     * @return true s'il est légal, false sinon
     * @author Sogolon88
     */
    public boolean validateMove(List<Square> playerMove) {

        if (playerMove == null || playerMove.size() != 2)
            return false;

        Square sc = playerMove.get(0);
        Square dt   = playerMove.get(1);

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
     * Modifie le plateau en validant l'action
     * @param playerMove le mouvement du joueur
     * @author Sogolon88
     */
    public void makeMove(List<Square> playerMove) {
        Square fromSquare = playerMove.get(0);
        Square toSquare = playerMove.get(1);

        if(toSquare.getType() == SquareType.EMPTY) {
            toSquare.setType(fromSquare.getType());
            fromSquare.setType(SquareType.EMPTY);
        } else {
            toSquare.setType(fromSquare.getType());
            fromSquare.setType(SquareType.EMPTY);
            if(turn.equals("WHITE")) {
                pieces.setNbBlackPieces(pieces.getNbBlackPieces() - 1);
            } else {
                pieces.setNbWhitePieces(pieces.getNbWhitePieces() - 1);
            }
        }

        turn = turn.equals("WHITE") ? "BLACK" : "WHITE";
        
    }

    /**
     * getter du mouvement du joueur
     * @return la case du mouvement
     * @author Sogolon88
     */
    private List<Square> getPlayerMove() {
        List<Square> playerMove = new ArrayList<>();
        System.out.print("Entrez votre mouvement (ex: a2 a3) : ");
        
        String[] positions = scanner.nextLine().trim().split("\\s+");

        try{
            int from_J = positions[0].charAt(0) - 'a';
            int from_I = Integer.parseInt(positions[0].substring(1)) - 1;
            int to_J = positions[1].charAt(0) - 'a';
            int to_I = Integer.parseInt(positions[1].substring(1)) - 1;
            
            Square fromSquare = boardSquares.get(from_I * boardSquares.getCol() + from_J);
            Square toSquare = boardSquares.get(to_I * boardSquares.getCol() + to_J);
            playerMove.add(fromSquare);
            playerMove.addLast(toSquare);
            

        } catch (Exception e){
            System.out.println("Entrée invalide. Veuillez réessayer.");
        }
        
        return playerMove;
    }

    /**
     * Vérifie si la partie est finie
     * @return true si la partie est finie, false sinon
     * @author Sogolon88
     */
    private boolean isGameOver() {
        if(pieces.getNbBlackPieces() == 0 || pieces.getNbWhitePieces() == 0)
            return true;

        for ( int j = 0; j < boardSquares.getCol(); j++ ) {
            if ( boardSquares.get(0 * boardSquares.getCol() + j).getType() == SquareType.WHITE ){
                winner = SquareType.WHITE;
                return true;
            }

            else if ( boardSquares.get(7 * boardSquares.getCol() + j).getType() == SquareType.BLACK ){
                winner = SquareType.BLACK;
                return true;
            }
        }

        return false;
    }

    /**
     * getter du tour actuel
     * @return 
     * @author Sogolon88
     */
    public String getTurn() {
        return turn;
    }

    /**
     * setter du tour actuel
     * @return 
     * @author Sogolon88
     */
    public void setTurn(String turn) {
        this.turn = turn;
    }

    /**
     * getter du gagnant
     * @return
     * @author Sogolon88
     */
    public SquareType getWinner() {
        return winner;
    }

    /**
     * setter du gagnant
     * @return
     * @author Sogolon88
     */
    public void setWinner(SquareType winner) {
        this.winner = winner;
    }
}