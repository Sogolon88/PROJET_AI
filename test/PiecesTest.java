package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import main.Pieces;

/**
 * Tests unitaires de la classe Pieces.
 * Couvre : initialisation des compteurs, setters/getters.
 * @author keita
 */
public class PiecesTest {

    private Pieces pieces;

    @BeforeEach
    void setUp() {
        pieces = new Pieces();
    }

    /** Vérifie que les Noirs commencent avec 16 pions. */
    @Test
    void testInitialisationPionsNoirs() {
        assertEquals(16, pieces.getNbBlackPieces());
    }

    /** Vérifie que les Blancs commencent avec 16 pions. */
    @Test
    void testInitialisationPionsBlancs() {
        assertEquals(16, pieces.getNbWhitePieces());
    }

    /** Vérifie que le setter des Noirs fonctionne. */
    @Test
    void testSetNbBlackPieces() {
        pieces.setNbBlackPieces(10);
        assertEquals(10, pieces.getNbBlackPieces());
    }

    /** Vérifie que le setter des Blancs fonctionne. */
    @Test
    void testSetNbWhitePieces() {
        pieces.setNbWhitePieces(7);
        assertEquals(7, pieces.getNbWhitePieces());
    }

    /** Vérifie la décrémentation simulant une capture. */
    @Test
    void testCapturePionNoir() {
        pieces.setNbBlackPieces(pieces.getNbBlackPieces() - 1);
        assertEquals(15, pieces.getNbBlackPieces());
    }

    /** Vérifie la décrémentation simulant une capture blanche. */
    @Test
    void testCapturePionBlanc() {
        pieces.setNbWhitePieces(pieces.getNbWhitePieces() - 1);
        assertEquals(15, pieces.getNbWhitePieces());
    }

    /** Vérifie qu'on peut mettre le compteur à 0 (fin de partie). */
    @Test
    void testPlusAucunPion() {
        pieces.setNbBlackPieces(0);
        assertEquals(0, pieces.getNbBlackPieces());
    }
}
