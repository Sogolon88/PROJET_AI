package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    static final int ROW = 8;
    static final int COL = 8;
    private String turn;
    private int nbBlackPieces;
    private int nbWhitePieces;
    private CaseType winner;
    private List<Case> bordCases;
    private Scanner scanner;

    public Game() {
        bordCases = new ArrayList<Case>();
        turn = "WHITE";
        winner = null;
        nbBlackPieces = 16;
        nbWhitePieces = 16;


        initializeBoard();
        scanner = new Scanner(System.in);
    }



private void initializeBoard() {
    for (int i = 0; i < ROW; i++) {
        for (int j = 0; j < COL; j++) {
            CaseType type;

            if (i < 2) {
                type = CaseType.BLACK;
            } else if (i > 5) {
                type = CaseType.WHITE;
            } else {
                type = CaseType.EMPTY;
            }

            Case c = new Case(i, j, type);
            bordCases.add(c);
        }
    }
}


    public void run() {
        int nb_turns = 0;
        while(!isGameOver()) {   
            printBoard();
            System.out.println("Tour " + nb_turns + " : " + turn + " joue.");
            
            List<Case> playerMove = getPlayerMove();
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

    public boolean validateMove(List<Case> playerMove) {

        if (playerMove == null || playerMove.size() != 2)
            return false;

        Case sc = playerMove.get(0);
        Case dt   = playerMove.get(1);

        if (sc.getType() != CaseType.valueOf(turn))
            return false;

        int dx = dt.getX() - sc.getX();
        int dy = Math.abs(dt.getY() - sc.getY());

        int forward = (turn.equals("WHITE")) ? -1 : 1;

        if (dx != forward)
            return false;

        if (dy == 0)
            return dt.getType() == CaseType.EMPTY;

        if (dy == 1)
            return dt.getType() != CaseType.EMPTY &&
                dt.getType() != sc.getType();

        return false;
    }


    public void makeMove(List<Case> playerMove) {
        Case fromCase = playerMove.get(0);
        Case toCase = playerMove.get(1);

        if(toCase.getType() == CaseType.EMPTY) {
            toCase.setType(fromCase.getType());
            fromCase.setType(CaseType.EMPTY);
        } else {
            toCase.setType(fromCase.getType());
            fromCase.setType(CaseType.EMPTY);
            if(turn.equals("WHITE")) {
                nbBlackPieces--;
            } else {
                nbWhitePieces--;
            }
        }

        turn = turn.equals("WHITE") ? "BLACK" : "WHITE";
        
    }

    private List<Case> getPlayerMove() {
        List<Case> playerMove = new ArrayList<>();
        System.out.print("Entrez votre mouvement (ex: a2 a3) : ");
        
        String[] positions = scanner.nextLine().trim().split("\\s+");

        try{
            int from_J = positions[0].charAt(0) - 'a';
            int from_I = Integer.parseInt(positions[0].substring(1)) - 1;
            int to_J = positions[1].charAt(0) - 'a';
            int to_I = Integer.parseInt(positions[1].substring(1)) - 1;
            
            Case fromCase = bordCases.get(from_I * COL + from_J);
            Case toCase = bordCases.get(to_I * COL + to_J);
            playerMove.add(fromCase);
            playerMove.addLast(toCase);
            

        } catch (Exception e){
            System.out.println("Entrée invalide. Veuillez réessayer.");
        }
        
        return playerMove;
    }

    private boolean isGameOver() {
        if(nbBlackPieces == 0 || nbWhitePieces == 0)
            return true;

        for ( int j = 0; j < COL; j++ ) {
            if ( bordCases.get(0 * COL + j).getType() == CaseType.WHITE ){
                winner = CaseType.WHITE;
                return true;
            }

            else if ( bordCases.get(7 * COL + j).getType() == CaseType.BLACK ){
                winner = CaseType.BLACK;
                return true;
            }
        }

        return false;
    }

    public List<Case> getBoard() {
        return this.bordCases;
    }

    public void printBoard() {

        System.out.println("\n    a     b     c     d     e     f     g     h   \n");

        for (int i = 0; i < ROW; i++) {

            int displayRow = i+1;
            System.out.print(displayRow + "   ");

            for (int j = 0; j < COL; j++) {

                switch (bordCases.get(i * COL + j).getType()) {
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




    public static int getRow() {
        return ROW;
    }



    public static int getCol() {
        return COL;
    }



    public String getTurn() {
        return turn;
    }



    public void setTurn(String turn) {
        this.turn = turn;
    }



    public int getNbBlackPieces() {
        return nbBlackPieces;
    }



    public void setNbBlackPieces(int nbBlackPieces) {
        this.nbBlackPieces = nbBlackPieces;
    }



    public int getNbWhitePieces() {
        return nbWhitePieces;
    }



    public void setNbWhitePieces(int nbWhitePieces) {
        this.nbWhitePieces = nbWhitePieces;
    }



    public CaseType getWinner() {
        return winner;
    }



    public void setWinner(CaseType winner) {
        this.winner = winner;
    }



    public List<Case> getBordCases() {
        return bordCases;
    }



    public void setBordCases(List<Case> bordCases) {
        this.bordCases = bordCases;
    }

}