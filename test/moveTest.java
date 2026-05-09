package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import main.Move;
import main.Square;
import main.SquareType;

/**
 * Tests unitaires de la classe Move.
 * Couvre : création d'un coup, getters, setters.
 * @author keita
 */
public class moveTest {

    private Square from;
    private Square to;
    private Move move;

    @BeforeEach
    void setUp() {
        from = new Square(6, 3, SquareType.WHITE);
        to   = new Square(5, 3, SquareType.EMPTY);
        move = new Move(from, to);
    }

    /** Vérifie que la case de départ est bien enregistrée. */
    @Test
    void testGetFromCase() {
        assertEquals(from, move.getFromCase());
    }

    /** Vérifie que la case d'arrivée est bien enregistrée. */
    @Test
    void testGetToCase() {
        assertEquals(to, move.getToCase());
    }

    /** Vérifie que setFromCase modifie la case de départ. */
    @Test
    void testSetFromCase() {
        Square newFrom = new Square(7, 0, SquareType.WHITE);
        move.setFromCase(newFrom);
        assertEquals(newFrom, move.getFromCase());
    }

    /** Vérifie que setToCase modifie la case d'arrivée. */
    @Test
    void testSetToCase() {
        Square newTo = new Square(6, 0, SquareType.EMPTY);
        move.setToCase(newTo);
        assertEquals(newTo, move.getToCase());
    }

    /** Vérifie les coordonnées de la case de départ. */
    @Test
    void testCoordonneesDeplacement() {
        assertEquals(6, move.getFromCase().getX());
        assertEquals(3, move.getFromCase().getY());
        assertEquals(5, move.getToCase().getX());
        assertEquals(3, move.getToCase().getY());
    }

    /** Vérifie un coup de capture (case d'arrivée occupée par l'adversaire). */
    @Test
    void testCoupCapture() {
        Square cible = new Square(5, 4, SquareType.BLACK);
        Move capture = new Move(from, cible);
        assertEquals(SquareType.BLACK, capture.getToCase().getType());
    }
}
