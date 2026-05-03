package src.algorithmes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Action;
import main.Case;
import main.CaseType;
import main.Game;

public class MiniMax {
    private static int mxPdr = 2; // profondeur de recherche

    /**
     * L'algorithme Minimax
     * @param board etat courant du jeu.
     * @return l'action optimale pour BLACK.
     */
    public static Action run(List<Case> board, String player) {
        // Copie profonde du plateau pour ne pas modifier l'original
        ArrayList<Case> stat = deepCopy(board);

        String opponent = player.equals("BLACK") ? "WHITE" : "BLACK";

        Map<Double, Action> actionsMap = new HashMap<>();
        List<Action> actions = Game.getActions(stat, player);

        for (Action a : actions) {
            ArrayList<Case> statCopy = deepCopy(stat);
            Game.resulatAction(statCopy, a);
            double score = min_value(statCopy, 1, player, opponent);
            actionsMap.put(score + actionsMap.size() * 1e-9, a);
        }

        if (actionsMap.isEmpty()) return null;

        double max = Collections.max(actionsMap.keySet());
        return actionsMap.get(max);
    }

    /**
     * Noeud MIN : c'est WHITE qui joue, il cherche à minimiser le score de BLACK
     */
    private static double min_value(List<Case> stat, int depth, String maxPlayer, String minPlayer) {
        if (isCutOff(stat, depth))
            return eval(stat, maxPlayer);

        double v = Double.POSITIVE_INFINITY;
        for (Action action : Game.getActions(stat, minPlayer)) {
            ArrayList<Case> statCopy = deepCopy(stat);
            Game.resulatAction(statCopy, action);
            v = Math.min(v, max_value(statCopy, depth + 1, maxPlayer, minPlayer));
        }

        if (v == Double.POSITIVE_INFINITY)
            return eval(stat, maxPlayer);

        return v;
    }

    /**
     * Noeud MAX : le joueur courant cherche à maximiser son score
     */
    private static double max_value(List<Case> stat, int depth, String maxPlayer, String minPlayer) {
        if (isCutOff(stat, depth))
            return eval(stat, maxPlayer);

        double v = Double.NEGATIVE_INFINITY;
        for (Action action : Game.getActions(stat, maxPlayer)) {
            ArrayList<Case> statCopy = deepCopy(stat);
            Game.resulatAction(statCopy, action);
            v = Math.max(v, min_value(statCopy, depth + 1, maxPlayer, minPlayer));
        }

        if (v == Double.NEGATIVE_INFINITY)
            return eval(stat, maxPlayer);

        return v;
    }

    /**
     * Fonction d'évaluation d'un état du jeu du point de vue de BLACK (joueur MAX).
     * Score positif = favorable à BLACK, négatif = favorable à WHITE.
     */
    /**
     * Evalue le plateau du point de vue du joueur MAX (maxPlayer).
     * Score positif = favorable à maxPlayer, négatif = favorable à l'adversaire.
     */
    private static double eval(List<Case> stat, String maxPlayer) {
        String minPlayer  = maxPlayer.equals("BLACK") ? "WHITE" : "BLACK";
        CaseType maxType  = CaseType.valueOf(maxPlayer);
        CaseType minType  = CaseType.valueOf(minPlayer);

        int nbMax = 0, nbMin = 0;
        int scoreMax = 0, scoreMin = 0;

        for (Case c : stat) {
            if (c.getType() == maxType) {
                nbMax++;
                int pos = c.getX();
                int avancement = maxPlayer.equals("BLACK") ? pos : (7 - pos);
                int dir = maxPlayer.equals("BLACK") ? 1 : -1;
                int nombreCaseVide = 0;
                for (int k = pos + dir; k >= 0 && k < 8; k += dir) {
                    if (stat.get(k * 8 + c.getY()).getType() == CaseType.EMPTY)
                        nombreCaseVide++;
                    else
                        break;
                }
                scoreMax += (avancement + nombreCaseVide - getNBMenace(c, stat, minType));

            } else if (c.getType() == minType) {
                nbMin++;
                int pos = c.getX();
                int avancement = minPlayer.equals("BLACK") ? pos : (7 - pos);
                int dir = minPlayer.equals("BLACK") ? 1 : -1;
                int nombreCaseVide = 0;
                for (int k = pos + dir; k >= 0 && k < 8; k += dir) {
                    if (stat.get(k * 8 + c.getY()).getType() == CaseType.EMPTY)
                        nombreCaseVide++;
                    else
                        break;
                }
                scoreMin += (avancement + nombreCaseVide - getNBMenace(c, stat, maxType));
            }
        }

        return (scoreMax - scoreMin) * 5 + (nbMax - nbMin);
    }

    /**
     * Condition d'arrêt : profondeur max atteinte ou état terminal.
     */
    private static boolean isCutOff(List<Case> stat, int depth) {
        if (depth >= mxPdr)
            return true;

        int nbBlackPiece = 0;
        int nbWhitePiece = 0;

        for (Case c : stat) {
            if (c.getType() == CaseType.BLACK) nbBlackPiece++;
            else if (c.getType() == CaseType.WHITE) nbWhitePiece++;
        }

        if (nbBlackPiece == 0 || nbWhitePiece == 0)
            return true;

        for (int j = 0; j < 8; j++) {
            if (stat.get(0 * 8 + j).getType() == CaseType.WHITE)
                return true;
            if (stat.get(7 * 8 + j).getType() == CaseType.BLACK)
                return true;
        }

        return false;
    }

    /**
     * Compte le nombre de pions adverses qui menacent le pion c en diagonale.
     * Pour BLACK (avance vers le bas) : les menaces viennent de la rangée suivante (nextX = x+1).
     * Pour WHITE (avance vers le haut) : les menaces viennent de la rangée précédente (nextX = x-1).
     * Vérifie les bornes pour éviter ArrayIndexOutOfBoundsException.
     */
    private static int getNBMenace(Case c, List<Case> stat, CaseType adversaire) {
        int n = 0;
        int x = c.getX();
        int y = c.getY();

        // La menace vient du côté vers lequel l'adversaire avance
        int nextX = (adversaire == CaseType.WHITE) ? x + 1 : x - 1;

        if (nextX < 0 || nextX >= 8) return 0;

        // Diagonale gauche
        if (y - 1 >= 0) {
            if (stat.get(nextX * 8 + (y - 1)).getType() == adversaire)
                n++;
        }
        // Diagonale droite
        if (y + 1 < 8) {
            if (stat.get(nextX * 8 + (y + 1)).getType() == adversaire)
                n++;
        }

        return n;
    }

    /**
     * Copie profonde du plateau de jeu.
     */
    public static ArrayList<Case> deepCopy(List<Case> board) {
        ArrayList<Case> copy = new ArrayList<>();
        for (Case c : board) {
            copy.add(new Case(c.getX(), c.getY(), c.getType()));
        }
        return copy;
    }
}
