package main;

<<<<<<< HEAD:src/main/Action.java
public class Action {
    private Case fromCase;
    private Case toCase;

    public Action(Case fromCase, Case toCase) {
=======

/**
 * Classe représentant le move du joueur
 * @author keita
 * @version 1.0
 */
public class Move {
    /** La case de départ */
    private Square fromCase;
    /** La case d'arrivée */
    private Square toCase;

    /**
     * Crée un mouvement
     * @param fromCase la case de départ
     * @param toCase la case d'arrivée
     * @author keita
     */
    public Move(Square fromCase, Square toCase) {
>>>>>>> jordan:src/main/Move.java
        this.fromCase = fromCase;
        this.toCase = toCase;
    }

<<<<<<< HEAD:src/main/Action.java


    public Case getFromCase() {
            return fromCase;
        }
    public Case getToCase() {
=======
    /**
     * getter de la case de départ
     * @return la case de départ
     * @author keita
     */
    public Square getFromCase() {
        return fromCase;
    }

    /**
     * getter de la case d'arrivée
     * @return la case d'arrivée
     * @author keita
     */
    public Square getToCase() {
>>>>>>> jordan:src/main/Move.java
        return toCase;
    }

    /**
     * setter de la case de départ
     * @return la case de départ
     * @author keita
     */
    public void setFromCase(Square fromCase) {
        this.fromCase = fromCase;
    }

    /**
     * setter de la case d'arrivée
     * @return la case d'arrivée
     * @author keita
     */
    public void setToCase(Square toCase) {
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
