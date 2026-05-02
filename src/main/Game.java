package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import algorithmes.MiniMax;

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


        this.bordCases = initializeBoard();
        scanner = new Scanner(System.in);
    }


/**
 * la fonction qui initialise le plateau de jeu
 * @return retourne la liste des cases du plateau de jeu
 */
public List<Case> initializeBoard() {
    List<Case> board = new ArrayList<>();
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
            board.add(c);
        }
    }

    return board;
}


    public void run() {
            int nb_turns = 0;
            while(!isGameOver(this.bordCases)) {   
            System.out.println(this);
            System.out.println("Tour " + nb_turns + " : " + turn + " joue.");
            Action playerMove = null;

            switch (turn) {
                case "BLACK":
                    playerMove = MiniMax.run(this.bordCases);
                    System.out.println(playerMove.getFromCase()+ " "+ playerMove.getToCase());
                    break;
                
                case "WHITE":
                    playerMove = getPlayerMove();
                    break;
                default:
                break;
            }
            
            if (validateMove(playerMove)){
                makeMove( playerMove );
                nb_turns++;
            }else{
                System.out.println("Mouvement invalide. Veuillez réessayer.");
            }
        }
        
        System.out.println("Le jeu est terminé!");
        System.out.println("VICTOIRE DES " + winner.toString().toUpperCase() + " !");
    }

    /**
     * la fonctions des actions possibles
     * @param stat l'etat du jeu
     * @param player le joueur courant
     * @return la liste des actions possibles
     */
    public static List<Action> getActions(List<Case> stat, String player) {
        List<Action> actions = new ArrayList<>();

        for (Case fromCase : stat) {
            if (fromCase.getType() == CaseType.valueOf(player)) {
                int dir = (player.equals("WHITE")) ? -1 : 1;
                int fromX = fromCase.getX();
                int fromY = fromCase.getY();

                // direction
                int toX = fromX + dir;
                if (toX >= 0 && toX < ROW) {
                    Case toCase = stat.get(toX * COL + fromY);
                    if (toCase.getType() == CaseType.EMPTY) {
                        actions.add(new Action(fromCase, toCase));
                    }
                }

                // Capture diagonally
                for (int dy = -1; dy <= 1; dy += 2) {
                    int toY = fromY + dy;
                    if (toX >= 0 && toX < ROW && toY >= 0 && toY < COL) {
                        Case toCase = stat.get(toX * COL + toY);
                        if (toCase.getType() != CaseType.EMPTY &&
                            toCase.getType() != fromCase.getType()) {
                            actions.add(new Action(fromCase, toCase));
                        }
                    }
                }
            }
        }

        return actions;
    }

    /**
     * la fonction qui retourne le resultat d'une action
     * @param stat l'etat du jeu
     * @param action l'action a effectuer
     * @return le nouvel etat du jeu apres l'action
     */
    public static List<Case> resulatAction(List<Case> stat, Action action) {

        Case from = action.getFromCase();
        Case to = action.getToCase();

        int i = to.getX() * 8 + to.getY();
        int j = from.getX() * 8 + from.getY();

        stat.get(i).setType(stat.get(j).getType());
        stat.get(j).setType(CaseType.EMPTY);

        return stat;
    }

    public boolean validateMove(Action playerMove) {

        if (playerMove == null)
            return false;

        Case sc = playerMove.getFromCase();
        Case dt   = playerMove.getToCase();

        if(sc.getType() != CaseType.valueOf(turn.trim())){
            System.out.println("`\n1");
            return false;
        }

        int dx = dt.getX() - sc.getX();
        int dy = Math.abs(dt.getY() - sc.getY());

        int forward = (turn.equals("WHITE")) ? -1 : 1;

        if (dx != forward){
            System.out.println("\n 2");
            return false;
        }

        if (dy == 0){
            System.out.println("3");
            return dt.getType() == CaseType.EMPTY;
        }
            

        if (dy == 1){
            System.err.println("4");
            return dt.getType() != CaseType.EMPTY &&
                dt.getType() != sc.getType();
        }

        return false;
    }

    public void makeMove(Action action) {
        Case fromCase = action.getFromCase();
        Case toCase = action.getToCase();

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

    private Action getPlayerMove() {
        System.out.print("Entrez votre mouvement (ex: a2 a3) : ");
        
        String[] positions = scanner.nextLine().trim().split("\\s+");

        try{
            int from_J = positions[0].charAt(0) - 'a';
            int from_I = Integer.parseInt(positions[0].substring(1)) - 1;
            int to_J = positions[1].charAt(0) - 'a';
            int to_I = Integer.parseInt(positions[1].substring(1)) - 1;
            
            Case fromCase = bordCases.get(from_I * COL + from_J);
            Case toCase = bordCases.get(to_I * COL + to_J);

            return new Action(fromCase, toCase);
        } catch (Exception e){
            System.out.println("Entrée invalide. Veuillez réessayer.");
        }
        
        return null;
    }

    /**
     * la fonction qui verifie si le jeu est termine
     * @param stat
     * @return
     */
    private boolean isGameOver(List<Case> stat) {
        int nbBlackPiece = 0;
        int nbWhitePiece = 0;

        for (Case c : stat) {
            if (c.getType() == CaseType.BLACK) {
                nbBlackPiece++;
            } else if (c.getType() == CaseType.WHITE) {
                nbWhitePiece++;
            }
        }

        if (nbBlackPiece == 0) {
            winner = CaseType.WHITE;
            return true;
        }
        if (nbWhitePiece == 0) {
            winner = CaseType.BLACK;
            return true;
        }

        for ( int j = 0; j < COL; j++ ) {
            if ( stat.get(0 * COL + j).getType() == CaseType.WHITE ){
                winner = CaseType.WHITE;
                return true;
            }

            else if ( stat.get(7 * COL + j).getType() == CaseType.BLACK ){
                winner = CaseType.BLACK;
                return true;
            }
        }

        return false;
    }

    public List<Case> getBoard() {
        return this.bordCases;
    }

@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n    a     b     c     d     e     f     g     h   ").append("\n");

        for (int i = 0; i < ROW; i++) {

            int displayRow = i+1;
            sb.append(displayRow + "   ");

            for (int j = 0; j < COL; j++) {

                switch (bordCases.get(i * COL + j).getType()) {
                    case EMPTY:
                        sb.append(".     ");
                        break;
                    case BLACK:
                        sb.append("B     ");
                        break;
                    case WHITE:
                        sb.append("W     ");
                        break;
                }
            }

            sb.append(displayRow).append("\n");
        }

        sb.append("    a     b     c     d     e     f     g     h   \n");

        return sb.toString();
    }


    public static int getRow() {
        return ROW;
    }

    public static int getCol() {
        return COL;
    }
    public String getPlayer() {
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

    public void setBordCases(List<Case> bordCases) {
        this.bordCases = bordCases;
    }

}