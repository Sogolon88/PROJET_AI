package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import main.Board;
import main.Square;
import main.SquareType;

/**
 * Tests unitaires de la classe Board.
 * Couvre : taille du plateau, type des cases, initialisation des pions.
 * @author keita
 */
public class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    /** Le plateau contient exactement 64 cases. */
    @Test
    void testTaillePlateau() {
        assertEquals(64, board.getboardSquares().size(), "Le plateau doit avoir 64 cases");
    }

    /** Le plateau a 8 lignes et 8 colonnes. */
    @Test
    void testDimensionsPlateau() {
        assertEquals(8, board.getRow());
        assertEquals(8, board.getCol());
    }

    /** Toutes les cases du plateau sont des instances de Square. */
    @Test
    void testCasesSontDesSquares() {
        for (int i = 0; i < 64; i++) {
            assertInstanceOf(Square.class, board.get(i));
        }
    }

    /** Les 2 premières rangées (indices 0 et 1) sont occupées par des pions BLACK. */
    @Test
    void testInitialisationPionsNoirs() {
        for (int i = 0; i < 16; i++) {
            assertEquals(SquareType.BLACK, board.get(i).getType(),
                "La case " + i + " devrait être BLACK");
        }
    }

    /** Les 2 dernières rangées (indices 6 et 7) sont occupées par des pions WHITE. */
    @Test
    void testInitialisationPionsBlancs() {
        for (int i = 48; i < 64; i++) {
            assertEquals(SquareType.WHITE, board.get(i).getType(),
                "La case " + i + " devrait être WHITE");
        }
    }

    /** Les rangées du milieu (indices 2 à 5) sont vides. */
    @Test
    void testInitialisationCasesVides() {
        for (int i = 16; i < 48; i++) {
            assertEquals(SquareType.EMPTY, board.get(i).getType(),
                "La case " + i + " devrait être EMPTY");
        }
    }

    /** L'accès par indice retourne la bonne case (coordonnées cohérentes). */
    @Test
    void testAccesParIndice() {
        Square c = board.get(0);
        assertEquals(0, c.getX());
        assertEquals(0, c.getY());

        Square c2 = board.get(9); // rangée 1, colonne 1
        assertEquals(1, c2.getX());
        assertEquals(1, c2.getY());
    }
}
