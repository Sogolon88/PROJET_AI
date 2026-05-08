package main;

/**
 * Classe représentant une pièce dans le jeu
 * @author keita
 * @version 1.0
 */
public class Pieces {

    /** Le nombre de pièces blanches*/
    private int nbBlackPieces;
    /** Le nombre de pièces noires*/
    private int nbWhitePieces;

    /**
     * Crée des pièces
     * @author keita
     */
    public Pieces(){
        nbBlackPieces = 16;
        nbWhitePieces = 16;
    }

    /**
     * getter du nombre de pièces noires
     * @return le nombre de pièces noires
     */
    public int getNbBlackPieces() {
        return nbBlackPieces;
    }

    /**
     * setter du nombre de pièces noires
     * @return le nombre de pièces noires
     */
    public void setNbBlackPieces(int nbBlackPieces) {
        this.nbBlackPieces = nbBlackPieces;
    }

    /**
     * getter du nombre de pièces blanches
     * @return le nombre de pièces blanches
     */ 
    public int getNbWhitePieces() {
        return nbWhitePieces;
    }

    /**
     * setter du nombre de pièces blanches
     * @return le nombre de pièces blanches
     */
    public void setNbWhitePieces(int nbWhitePieces) {
        this.nbWhitePieces = nbWhitePieces;
    }
}
