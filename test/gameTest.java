package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import main.Game;
import main.Board;
import main.Square;
import main.SquareType;

/**
 * Tests unitaires de la classe Game.
 * Couvre : validation des coups légaux/illégaux, makeMove, détection de fin de partie.
 * @author keita
 */
public class gameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    // ─── Validation des coups ───────────────────────────────────────────────

    /** Un coup vers l'avant sur une case vide est légal (WHITE avance de rangée 7→6). */
    @Test
    void testCoupAvantValide() {
        Board board = game.getBoard();
        Square from = board.get(6 * 8 + 0); // rangée 6, colonne 0 — WHITE
        Square to   = board.get(5 * 8 + 0); // rangée 5, colonne 0 — EMPTY
        List<Square> move = new ArrayList<>();
        move.add(from);
        move.add(to);
        assertTrue(game.validateMove(move), "Avancer sur une case vide doit être valide");
    }

    /** Un coup vers l'arrière est illégal. */
    @Test
    void testCoupArrierInvalide() {
        Board board = game.getBoard();
        Square from = board.get(6 * 8 + 0); // WHITE
        Square to   = board.get(7 * 8 + 0); // rangée derrière
        List<Square> move = new ArrayList<>();
        move.add(from);
        move.add(to);
        assertFalse(game.validateMove(move), "Reculer doit être invalide");
    }

    /** Avancer sur une case occupée par un allié est illégal. */
    @Test
    void testCoupSurAllie() {
        Board board = game.getBoard();
        Square from = board.get(6 * 8 + 0); // WHITE rangée 6
        Square to   = board.get(7 * 8 + 0); // WHITE rangée 7
        List<Square> move = new ArrayList<>();
        move.add(from);
        move.add(to);
        assertFalse(game.validateMove(move), "Aller sur un allié doit être invalide");
    }

    /** Une capture diagonale sur un pion adverse est légale. */
    @Test
    void testCaptureValide() {
        Board board = game.getBoard();
        // On place un pion BLACK en (5,1) pour que WHITE en (6,0) puisse le capturer
        board.get(5 * 8 + 1).setType(SquareType.BLACK);
        Square from = board.get(6 * 8 + 0); // WHITE
        Square to   = board.get(5 * 8 + 1); // BLACK
        List<Square> move = new ArrayList<>();
        move.add(from);
        move.add(to);
        assertTrue(game.validateMove(move), "Capturer en diagonale doit être valide");
    }

    /** Un mouvement avec une liste null est invalide. */
    @Test
    void testCoupNull() {
        assertFalse(game.validateMove(null));
    }

    /** Un mouvement avec une mauvaise pièce (jouer avec la pièce adverse) est invalide. */
    @Test
    void testJouerAvecPieceAdverse() {
        Board board = game.getBoard();
        Square from = board.get(0); // BLACK rangée 0
        Square to   = board.get(1 * 8 + 0);
        List<Square> move = new ArrayList<>();
        move.add(from);
        move.add(to);
        // C'est le tour de WHITE
        assertFalse(game.validateMove(move), "Jouer avec la pièce adverse doit être invalide");
    }

    // ─── makeMove ───────────────────────────────────────────────────────────

    /** Après makeMove, la case de départ est EMPTY et la case d'arrivée prend le type. */
    @Test
    void testMakeMoveDeplace() {
        Board board = game.getBoard();
        Square from = board.get(6 * 8 + 0); // WHITE
        Square to   = board.get(5 * 8 + 0); // EMPTY
        List<Square> move = new ArrayList<>();
        move.add(from);
        move.add(to);
        game.makeMove(move);
        assertEquals(SquareType.EMPTY, from.getType(), "La case de départ doit être vide après le coup");
        assertEquals(SquareType.WHITE, to.getType(),   "La case d'arrivée doit être WHITE après le coup");
    }

    /** Après makeMove, le tour passe à l'autre joueur. */
    @Test
    void testMakeMoveTourChange() {
        Board board = game.getBoard();
        Square from = board.get(6 * 8 + 0);
        Square to   = board.get(5 * 8 + 0);
        List<Square> move = new ArrayList<>();
        move.add(from);
        move.add(to);
        assertEquals("WHITE", game.getTurn());
        game.makeMove(move);
        assertEquals("BLACK", game.getTurn());
    }
}
