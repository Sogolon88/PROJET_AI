package main;

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
        this.fromCase = fromCase;
        this.toCase = toCase;
    }

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
        return toCase;
    }

    /**
     * setter de la case de départ
     * @param fromCase la case de départ
     * @author keita
     */
    public void setFromCase(Square fromCase) {
        this.fromCase = fromCase;
    }

    /**
     * setter de la case d'arrivée
     * @param toCase la case d'arrivée
     * @author keita
     */
    public void setToCase(Square toCase) {
        this.toCase = toCase;
    }
}
