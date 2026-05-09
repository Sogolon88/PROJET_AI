package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import main.Square;
import main.SquareType;

/**
 * Tests unitaires de la classe Square.
 * Couvre : création, getters, setters, type.
 */
public class SquareTest {

    private Square square;

    @BeforeEach
    void setUp() {
        square = new Square(3, 5, SquareType.BLACK);
    }

    /** Vérifie que les coordonnées sont bien initialisées. */
    @Test
    void testCoordonnees() {
        assertEquals(3, square.getX());
        assertEquals(5, square.getY());
    }

    /** Vérifie que le type est bien initialisé. */
    @Test
    void testTypeInitial() {
        assertEquals(SquareType.BLACK, square.getType());
    }

    /** Vérifie que setType modifie bien le type. */
    @Test
    void testSetType() {
        square.setType(SquareType.WHITE);
        assertEquals(SquareType.WHITE, square.getType());
    }

    /** Vérifie que setType vers EMPTY fonctionne. */
    @Test
    void testSetTypeEmpty() {
        square.setType(SquareType.EMPTY);
        assertEquals(SquareType.EMPTY, square.getType());
    }

    /** Vérifie que canMove est false par défaut. */
    @Test
    void testCanMoveDefaut() {
        assertFalse(square.getCanMove());
    }

    /** Vérifie que setCanMove fonctionne. */
    @Test
    void testSetCanMove() {
        square.setCanMove(true);
        assertTrue(square.getCanMove());
    }

    /** Vérifie la création d'une case EMPTY. */
    @Test
    void testCaseEmpty() {
        Square empty = new Square(0, 0, SquareType.EMPTY);
        assertEquals(SquareType.EMPTY, empty.getType());
    }
}
