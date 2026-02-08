package main.move;

import main.square.Square;

public class Move {
    private Square fromCase;
    private Square toCase;

    public Move(Square fromCase, Square toCase) {
        this.fromCase = fromCase;
        this.toCase = toCase;
    }

    

    public Square getFromCase() {
        return fromCase;
    }
    public Square getToCase() {
        return toCase;
    }
    public void setFromCase(Square fromCase) {
        this.fromCase = fromCase;
    }
    public void setToCase(Square toCase) {
        this.toCase = toCase;
    }
    
}
