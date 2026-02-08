package main.square;

public class Square{
    private int x;
    private int y;
    private SquareType type;
    private boolean canMove;

    public Square(int x, int y, SquareType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.canMove = false;
    }

    public void setType(SquareType type) {
        this.type = type;
    }

    public SquareType getType() {
        return type;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
    public boolean getCanMove() {
        return canMove;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}