package main;

/**
 * Classe représentant une case
 * @author keita
 * @version 1.0
 */
public class Square{
    /** abcisse de la case */
    private int x;
    /** ordonnée de la case */
    private int y;
    private SquareType type;
    private boolean canMove;


    /**
     * Crée une case
     * @param x
     * @param y
     * @param type
     */
    public Square(int x, int y, SquareType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.canMove = false;
    }

    /**
     * setter du type de case
     * @param type
     */
    public void setType(SquareType type) {
        this.type = type;
    }

    /**
     * getter du type de case
     * @param type
     */
    public SquareType getType() {
        return type;
    }

    /**
     * setter du mouvement
     * @param canMove
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     * getter du mouvement
     * @param canMove
     */
    public boolean getCanMove() {
        return canMove;
    }

    /**
     * getter de l'abcisse
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * getter de l'ordonnée
     * @return
     */
    public int getY() {
        return y;
    }
}