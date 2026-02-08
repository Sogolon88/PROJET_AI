package main.board;

import java.util.ArrayList;
import java.util.List;

import main.square.*;

public class Board {

    private final int ROW = 8;
    private final int COL = 8;
    private List<Square> boardSquares;

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

    public List<Square> getBoard() {
        return this.boardSquares;
    }

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

    public Square get(int index){
        return boardSquares.get(index);
    }
    public int getRow() {
        return ROW;
    }

    public int getCol() {
        return COL;
    }

    public List<Square> getboardSquares() {
        return boardSquares;
    }

    public void setboardSquares(List<Square> boardSquares) {
        this.boardSquares = boardSquares;
    }
}


