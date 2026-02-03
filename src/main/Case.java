package main;

public class Case{
    private int x;
    private int y;
    private CaseType type;
    private boolean canMove;

    public Case(int x, int y, CaseType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.canMove = false;
    }

    public void setType(CaseType type) {
        this.type = type;
    }

    public CaseType getType() {
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