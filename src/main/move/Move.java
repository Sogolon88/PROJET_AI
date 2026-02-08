package main.move;

import main.square.Square;

/**
 * Classe représentant le move du joueur
 * @author Sogolon88
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
     * @author Sogolon88
     */
    public Move(Square fromCase, Square toCase) {
        this.fromCase = fromCase;
        this.toCase = toCase;
    }

    /**
     * getter de la case de départ
     * @return la case de départ
     * @author Sogolon88
     */
    public Square getFromCase() {
        return fromCase;
    }

    /**
     * getter de la case d'arrivée
     * @return la case d'arrivée
     * @author Sogolon88
     */
    public Square getToCase() {
        return toCase;
    }

    /**
     * setter de la case de départ
     * @return la case de départ
     * @author Sogolon88
     */
    public void setFromCase(Square fromCase) {
        this.fromCase = fromCase;
    }

    /**
     * setter de la case d'arrivée
     * @return la case d'arrivée
     * @author Sogolon88
     */
    public void setToCase(Square toCase) {
        this.toCase = toCase;
    }
    
}
