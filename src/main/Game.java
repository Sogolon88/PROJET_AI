package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import src.algorithmes.MiniMax;


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
<<<<<<< HEAD
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


    public void runAI() {
            int nb_turns = 0;
            while(!isGameOver(this.bordCases)) {   
            System.out.println(this);
            System.out.println("Tour " + nb_turns + " : " + turn + " joue.");
            Action playerMove = null;

            switch (turn) {
                case "BLACK":
                case "WHITE":
                    Action aiMove = MiniMax.run(bordCases, turn);
                    if (aiMove != null) {
                        int fromIdx = aiMove.getFromCase().getX() * COL + aiMove.getFromCase().getY();
                        int toIdx   = aiMove.getToCase().getX()   * COL + aiMove.getToCase().getY();
                        playerMove  = new Action(bordCases.get(fromIdx), bordCases.get(toIdx));
                        System.out.println(turn + " (IA) joue : "
                            + toNotation(playerMove.getFromCase())
                            + " -> "
                            + toNotation(playerMove.getToCase()));
                    }
                    break;
                default:
                break;
            }

            if (validateMove(playerMove)){
                makeMove( playerMove );
=======
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
>>>>>>> jordan
                nb_turns++;
            }else{
                System.out.println("Mouvement invalide. Veuillez réessayer.");
            }
        }

        afficherFinDePartie();
    }

    /**
<<<<<<< HEAD
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
=======
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
>>>>>>> jordan
            return false;
        }

        int dx = dt.getX() - sc.getX();
        int dy = Math.abs(dt.getY() - sc.getY());
        int forward = (turn.equals("WHITE")) ? -1 : 1;

        if (dx != forward){
            return false;
        }

<<<<<<< HEAD
        if (dy == 0){
            return dt.getType() == CaseType.EMPTY;
        }

        if (dy == 1){
            return dt.getType() != CaseType.EMPTY &&
=======
        if (dy == 0)
            return dt.getType() == SquareType.EMPTY;

        if (dy == 1)
            return dt.getType() != SquareType.EMPTY &&
>>>>>>> jordan
                dt.getType() != sc.getType();
        }

        return false;
    }

<<<<<<< HEAD
    public void makeMove(Action action) {
        Case fromCase = action.getFromCase();
        Case toCase = action.getToCase();

        if(toCase.getType() == CaseType.EMPTY) {
            toCase.setType(fromCase.getType());
            fromCase.setType(CaseType.EMPTY);
=======
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
>>>>>>> jordan
        } else {
            toSquare.setType(fromSquare.getType());
            fromSquare.setType(SquareType.EMPTY);
            if (turn.equals("WHITE")) {
                pieces.setNbBlackPieces(pieces.getNbBlackPieces() - 1);
            } else {
                pieces.setNbWhitePieces(pieces.getNbWhitePieces() - 1);
            }
        }

<<<<<<< HEAD
        turn = turn.equals("WHITE") ? "BLACK" : "WHITE";   
    }

    private Action getPlayerMove() {
=======
        turn = turn.equals("WHITE") ? "BLACK" : "WHITE";
    }

    /**
     * Récupère le mouvement saisi par le joueur humain
     * @return la liste [caseDepart, caseArrivee]
     * @author keita
     */
    private List<Square> getPlayerMove() {
        List<Square> playerMove = new ArrayList<>();
>>>>>>> jordan
        System.out.print("Entrez votre mouvement (ex: a2 a3) : ");

        String[] positions = scanner.nextLine().trim().split("\\s+");

        try {
            int from_J = positions[0].charAt(0) - 'a';
            int from_I = Integer.parseInt(positions[0].substring(1)) - 1;
            int to_J = positions[1].charAt(0) - 'a';
            int to_I = Integer.parseInt(positions[1].substring(1)) - 1;
<<<<<<< HEAD
            
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
=======

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
>>>>>>> jordan
                return true;
            }
        }

<<<<<<< HEAD
        // Un joueur n'a plus aucun coup possible malgré ses pions → il perd
        if (getActions(stat, "BLACK").isEmpty()) {
            winner = CaseType.WHITE;
            return true;
        }
        if (getActions(stat, "WHITE").isEmpty()) {
            winner = CaseType.BLACK;
=======
        // Condition 3 : le joueur courant n'a plus aucun coup possible
        List<Square> squares = boardSquares.getboardSquares();
        if (AlphaBeta.getActions(squares, turn).isEmpty()) {
            // Le joueur courant est bloqué : l'adversaire gagne
            winner = SquareType.valueOf(turn.equals("WHITE") ? "BLACK" : "WHITE");
>>>>>>> jordan
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

<<<<<<< HEAD
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


    /**
     * Convertit une Case en notation lisible type "a1", "b3"...
     * Les colonnes sont a-h (y=0→a), les lignes sont 1-8 (x=0→1)
     */
    public static String toNotation(Case c) {
        char col = (char) ('a' + c.getY());
        int row = c.getX() + 1;
        return "" + col + row;
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
=======
    public String getTurn() { return turn; }
    public void setTurn(String turn) { this.turn = turn; }
    public SquareType getWinner() { return winner; }
    public void setWinner(SquareType winner) { this.winner = winner; }
    public Board getBoard() { return boardSquares; }
}
>>>>>>> jordan
