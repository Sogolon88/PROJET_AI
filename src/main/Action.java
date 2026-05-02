package main;

public class Action {
    private Case fromCase;
    private Case toCase;

    public Action(Case fromCase, Case toCase) {
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
    
    @Override
    public boolean equals(Object ob){
        if (ob == null)
            return false;
        else {
            Action act = (Action)ob;
            return act.getToCase().equals(toCase) &&
            act.getFromCase().equals(fromCase);
        }
    }
}
