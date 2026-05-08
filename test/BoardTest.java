package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

import main.board.Board;
import main.square.Square;
/**
 * Classe représentant la classe test de Board
 * @author jordaalaicrisosto-cyber
 * @version 1.0
 */
public class BoardTest {

    /** Membre test du Board */
    private Board boardSquares;

    /** Initialisation du plateau à chaque fois */
    @BeforeEach
    void setUp(){
        boardSquares = new Board();
    }

    //Notes tests
    //Si une case du plateau est bien de type Square ---> fait
    //Si le plateau a bien 8 lignes et 8 colonnes ---> ?
    //Vérifier que lors de l'initialisation du plateau les 2 premières lignes sont occupées par les WHITE et les 2 dernières par les BLACK

    /** Vérifie si les cases du plateau sont bien de type square */
    @Test
    void isBoardMadeOfSquares(){
        for(int i = 0; i < boardSquares.getboardSquares().size(); i++){
            assertEquals(boardSquares.get(i) instanceof Square, true, "");
        }
    }

    //boardSquares n'est pas une matrice
    /**  */

    //test d'initialisation à faire
}
