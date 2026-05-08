package algorithmes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Board;
import main.Move;
import main.Square;
import main.SquareType;

/**
 * Algorithme Minmax pour le jeu La Percée.
 * @author keita
 */
public class MinMax {

    private static final int ROW = 8;
    private static final int COL = 8;

    // Attributs de classe : initialisés une fois dans run()
    private static int    niveau;      // niveau de difficulté (1, 2 ou 3)
    private static int    profondeur;  // profondeur max de recherche
    private static String maxPlayer;   // joueur IA (celui qui maximise)
    private static String minPlayer;   // adversaire (celui qui minimise)

    /**
     * Retourne la profondeur de recherche selon le niveau.
     *   niveau 1:  profondeur 2  (facile)
     *   niveau 2: profondeur 4  (intermédiaire)
     *   niveau 3: profondeur 6  (difficile)
     */
    private static int getProfondeur(int niv) {
        switch (niv) {
            case 1:  return 2;
            case 2:  return 4;
            case 3:  return 6;
            default: return 2;
        }
    }

    /**
     * Appelle la bonne fonction d'évaluation selon le niveau.
     *   niveau 1  evalMateriel
     *   niveau 2  evalPositionnelle
     *   niveau 3  evalStrategique
     */
    private static double evaluer(List<Square> stat) {
        switch (niveau) {
            case 1:  return Evaluation.evalMateriel(stat, maxPlayer);
            case 2:  return Evaluation.evalPositionnelle(stat, maxPlayer);
            case 3:  return Evaluation.evalStrategique(stat, maxPlayer);
            default: return Evaluation.evalMateriel(stat, maxPlayer);
        }
    }

    /**
     * Point d'entrée : retourne le meilleur coup pour le joueur courant.
     * @param board  le plateau actuel
     * @param player le joueur courant ("BLACK" ou "WHITE")
     * @param niv    niveau de difficulté (1, 2 ou 3)
     * @return le meilleur Move trouvé
     */
    public static Move run(Board board, String player, int niv) {
        // Initialisation des attributs de classe
        niveau     = niv;
        profondeur = getProfondeur(niv);
        maxPlayer  = player;
        minPlayer  = player.equals("BLACK") ? "WHITE" : "BLACK";

        List<Square> stat = deepCopy(board.getboardSquares());

        Map<Double, Move> actionsMap = new HashMap<>();
        List<Move> actions = getActions(stat, maxPlayer);

        for (Move a : actions) {
            List<Square> statCopy = deepCopy(stat);
            resulatAction(statCopy, a);
            double score = min_value(statCopy, 1);
            actionsMap.put(score + actionsMap.size() * 1e-9, a);
        }

        if (actionsMap.isEmpty()) return null;

        double max = Collections.max(actionsMap.keySet());
        return actionsMap.get(max);
    }

    /**
     * Noeud MIN : l'adversaire cherche à minimiser le score de MAX.
     */
    private static double min_value(List<Square> stat, int depth) {
        if (isCutOff(stat, depth))
            return evaluer(stat);

        double v = Double.POSITIVE_INFINITY;
        for (Move action : getActions(stat, minPlayer)) {
            List<Square> statCopy = deepCopy(stat);
            resulatAction(statCopy, action);
            v = Math.min(v, max_value(statCopy, depth + 1));
        }

        if (v == Double.POSITIVE_INFINITY)
            return evaluer(stat);

        return v;
    }

    /**
     * Noeud MAX : le joueur courant cherche à maximiser son score.
     */
    private static double max_value(List<Square> stat, int depth) {
        if (isCutOff(stat, depth))
            return evaluer(stat);

        double v = Double.NEGATIVE_INFINITY;
        for (Move action : getActions(stat, maxPlayer)) {
            List<Square> statCopy = deepCopy(stat);
            resulatAction(statCopy, action);
            v = Math.max(v, min_value(statCopy, depth + 1));
        }

        if (v == Double.NEGATIVE_INFINITY)
            return evaluer(stat);

        return v;
    }

    /**
     * Retourne la liste des coups possibles pour un joueur donné.
     */
    public static List<Move> getActions(List<Square> stat, String player) {
        List<Move> actions = new ArrayList<>();

        for (Square fromSquare : stat) {
            if (fromSquare.getType() == SquareType.valueOf(player)) {
                int dir   = player.equals("WHITE") ? -1 : 1;
                int fromX = fromSquare.getX();
                int fromY = fromSquare.getY();
                int toX   = fromX + dir;

                if (toX >= 0 && toX < ROW) {
                    // Avance tout droit
                    Square toSquare = stat.get(toX * COL + fromY);
                    if (toSquare.getType() == SquareType.EMPTY)
                        actions.add(new Move(fromSquare, toSquare));

                    // Capture en diagonale
                    for (int dy = -1; dy <= 1; dy += 2) {
                        int toY = fromY + dy;
                        if (toY >= 0 && toY < COL) {
                            Square diagSquare = stat.get(toX * COL + toY);
                            if (diagSquare.getType() != SquareType.EMPTY &&
                                diagSquare.getType() != fromSquare.getType())
                                actions.add(new Move(fromSquare, diagSquare));
                        }
                    }
                }
            }
        }

        return actions;
    }

    /**
     * Applique un Move sur le plateau (modifie stat en place).
     */
    public static void resulatAction(List<Square> stat, Move action) {
        int fromIdx = action.getFromCase().getX() * COL + action.getFromCase().getY();
        int toIdx   = action.getToCase().getX()   * COL + action.getToCase().getY();
        stat.get(toIdx).setType(stat.get(fromIdx).getType());
        stat.get(fromIdx).setType(SquareType.EMPTY);
    }

    /**
     * Condition d'arrêt : profondeur max atteinte ou état terminal.
     */
    private static boolean isCutOff(List<Square> stat, int depth) {
        if (depth >= profondeur) return true;

        int nbBlack = 0, nbWhite = 0;
        for (Square c : stat) {
            if (c.getType() == SquareType.BLACK)      nbBlack++;
            else if (c.getType() == SquareType.WHITE) nbWhite++;
        }

        if (nbBlack == 0 || nbWhite == 0) return true;

        for (int j = 0; j < COL; j++) {
            if (stat.get(0 * COL + j).getType() == SquareType.WHITE) return true;
            if (stat.get(7 * COL + j).getType() == SquareType.BLACK) return true;
        }

        return false;
    }

    /**
     * Copie profonde du plateau.
     */
    public static List<Square> deepCopy(List<Square> board) {
        List<Square> copy = new ArrayList<>();
        for (Square c : board)
            copy.add(new Square(c.getX(), c.getY(), c.getType()));
        return copy;
    }
}
