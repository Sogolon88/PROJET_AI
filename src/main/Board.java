package main;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe représentant le plateau du jeu
 * @author keita
 * @version 1.0
 */
public class Board {
    /** Nombre de lignes */
    private final int ROW = 8;
    /** Nombre de colonnes */
    private final int COL = 8;
    /** Plateau de jeu */
    private List<Square> boardSquares;

    /**
     * Crée un plateau
     * @author keita
     * @version 1.0
     */
    public Board(){
        this.boardSquares = new ArrayList<Square>();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                SquareType type;

                if (i < 2) {
                    type = SquareType.BLACK;
                } else if (i > 5) {
                    type = SquareType.WHITE;
                } else {
                    type = SquareType.EMPTY;
                }

                Square c = new Square(i, j, type);
                boardSquares.add(c);
            }
        }
    }

    /**
     * Retourne le plateau
     * @return le plateau vide
     * @author keita
     */
    public List<Square> getBoard() {
        return this.boardSquares;
    }

    /**
     * Affiche le plateau
     * @author keita
     */
    public void printBoard() {

        System.out.println("\n    a     b     c     d     e     f     g     h   \n");

        for (int i = 0; i < ROW; i++) {

            int displayRow = i+1;
            System.out.print(displayRow + "   ");

            for (int j = 0; j < COL; j++) {

                switch (boardSquares.get(i * COL + j).getType()) {
                    case EMPTY:
                        System.out.print(".     ");
                        break;
                    case BLACK:
                        System.out.print("B     ");
                        break;
                    case WHITE:
                        System.out.print("W     ");
                        break;
                }
            }

            System.out.println(displayRow+"   ");
        }

        System.out.println("\n    a     b     c     d     e     f     g     h   \n");
    }

    /**
     * Retourne la case à l'indice index
     * @param index
     * @return la case
     * @author keita
     */
    public Square get(int index){
        return boardSquares.get(index);
    }
    
    /**
     * Retourne le nombre de lignes
     * @return le nombre de lignes
     * @author keita
     */
    public int getRow() {
        return ROW;
    }

    /**
     * Retourne le nombre de colonnes
     * @return le nombre de colonnes
     * @author keita
     */
    public int getCol() {
        return COL;
    }

    /**
     * Retourne le plateau de jeu
     * @return le plateau de jeu
     * @author keita
     */
    public List<Square> getboardSquares() {
        return boardSquares;
    }

    /**
     * Setter du plateau de jeu
     * @param boardSquares le plateau de jeu
     */
    public void setboardSquares(List<Square> boardSquares) {
        this.boardSquares = boardSquares;
    }
}


