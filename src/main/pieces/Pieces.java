package main.pieces;

public class Pieces {

    private int nbBlackPieces;
    private int nbWhitePieces;

    public Pieces(){
        nbBlackPieces = 16;
        nbWhitePieces = 16;
    }

    public int getNbBlackPieces() {
        return nbBlackPieces;
    }

    public void setNbBlackPieces(int nbBlackPieces) {
        this.nbBlackPieces = nbBlackPieces;
    }

    public int getNbWhitePieces() {
        return nbWhitePieces;
    }

    public void setNbWhitePieces(int nbWhitePieces) {
        this.nbWhitePieces = nbWhitePieces;
    }
}
