package main;

public class Move {
    private Case fromCase;
    private Case toCase;

    public Move(Case fromCase, Case toCase) {
        this.fromCase = fromCase;
        this.toCase = toCase;
    }

    

    public Case getFromCase() {
        return fromCase;
    }
    public Case getToCase() {
        return toCase;
    }
    public void setFromCase(Case fromCase) {
        this.fromCase = fromCase;
    }
    public void setToCase(Case toCase) {
        this.toCase = toCase;
    }
    
}
