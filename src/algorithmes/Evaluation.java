package algorithmes;

import java.util.List;

import main.Square;
import main.SquareType;

public class Evaluation {

    /**
     * Fonction d'évaluation 1 — Matérielle (niveau facile).
     * Compte uniquement la différence de pions entre les deux joueurs.
     * Score positif = favorable à maxPlayer.
     */
    public static double evalMateriel(List<Square> stat, String maxPlayer) {
        String minPlayer = maxPlayer.equals("BLACK") ? "WHITE" : "BLACK";
        SquareType maxType = SquareType.valueOf(maxPlayer);
        SquareType minType = SquareType.valueOf(minPlayer);

        int nbMax = 0, nbMin = 0;

        for (Square c : stat) {
            if (c.getType() == maxType)      nbMax++;
            else if (c.getType() == minType) nbMin++;
        }

        return nbMax - nbMin;
    }

    /**
     * Fonction d'évaluation 2 — Positionnelle (niveau intermédiaire).
     * Prend en compte l'avancement des pions, les cases libres devant eux
     * et les menaces adverses.
     * Score positif = favorable à maxPlayer.
     */
    public static double evalPositionnelle(List<Square> stat, String maxPlayer) {
        String minPlayer = maxPlayer.equals("BLACK") ? "WHITE" : "BLACK";
        SquareType maxType = SquareType.valueOf(maxPlayer);
        SquareType minType = SquareType.valueOf(minPlayer);

        int nbMax = 0, nbMin = 0;
        int scoreMax = 0, scoreMin = 0;

        for (Square c : stat) {
            if (c.getType() == maxType) {
                nbMax++;
                int pos = c.getX();
                int avancement = maxPlayer.equals("BLACK") ? pos : (7 - pos);
                int dir = maxPlayer.equals("BLACK") ? 1 : -1;
                int nombreCaseVide = 0;
                for (int k = pos + dir; k >= 0 && k < 8; k += dir) {
                    if (stat.get(k * 8 + c.getY()).getType() == SquareType.EMPTY)
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
                    if (stat.get(k * 8 + c.getY()).getType() == SquareType.EMPTY)
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
     * Fonction d'évaluation 3 — Stratégique (niveau difficile).
     * Intègre quatre heuristiques cumulatives :
     *   1. Avancement + cases libres devant + menaces adverses  (héritée de evalPositionnelle)
     *   2. Pion passé : aucun adversaire dans sa colonne et les colonnes adjacentes (+3)
     *   3. Victoire imminente : pion à une seule case de la dernière rangée adverse (+50)
     *   4. Contrôle du centre : pion sur une des 4 colonnes centrales (c,d,e,f = indices 2,3,4,5) (+1)
     *   5. Formation phalanx : pion ayant un allié directement à gauche ou à droite (+0.5 par voisin)
     * Score positif = favorable à maxPlayer.
     */
    public static double evalStrategique(List<Square> stat, String maxPlayer) {
        String minPlayer = maxPlayer.equals("BLACK") ? "WHITE" : "BLACK";
        SquareType maxType = SquareType.valueOf(maxPlayer);
        SquareType minType = SquareType.valueOf(minPlayer);

        int nbMax = 0, nbMin = 0;
        double scoreMax = 0, scoreMin = 0;

        for (Square c : stat) {
            if (c.getType() == maxType) {
                nbMax++;
                int pos = c.getX();
                int col = c.getY();
                int avancement = maxPlayer.equals("BLACK") ? pos : (7 - pos);
                int dir = maxPlayer.equals("BLACK") ? 1 : -1;

                // Cases libres devant le pion
                int nombreCaseVide = 0;
                for (int k = pos + dir; k >= 0 && k < 8; k += dir) {
                    if (stat.get(k * 8 + col).getType() == SquareType.EMPTY)
                        nombreCaseVide++;
                    else
                        break;
                }

                // Heuristique 2 : pion passé
                int bonusPasse = isPionPasse(c, stat, maxPlayer, minPlayer) ? 3 : 0;

                // Heuristique 3 : victoire imminente (à une case de la dernière rangée)
                int bonusImminence = (avancement == 6) ? 50 : 0;

                // Heuristique 4 : contrôle du centre (colonnes 2, 3, 4, 5)
                double bonusCentre = (col >= 2 && col <= 5) ? 1.0 : 0.0;

                // Heuristique 5 : phalanx (allié directement à gauche ou à droite)
                double bonusPhalanx = 0.0;
                if (col - 1 >= 0 && stat.get(pos * 8 + (col - 1)).getType() == maxType) bonusPhalanx += 0.5;
                if (col + 1 <  8 && stat.get(pos * 8 + (col + 1)).getType() == maxType) bonusPhalanx += 0.5;

                scoreMax += avancement + nombreCaseVide
                          - getNBMenace(c, stat, minType)
                          + bonusPasse + bonusImminence + bonusCentre + bonusPhalanx;

            } else if (c.getType() == minType) {
                nbMin++;
                int pos = c.getX();
                int col = c.getY();
                int avancement = minPlayer.equals("BLACK") ? pos : (7 - pos);
                int dir = minPlayer.equals("BLACK") ? 1 : -1;

                // Cases libres devant le pion
                int nombreCaseVide = 0;
                for (int k = pos + dir; k >= 0 && k < 8; k += dir) {
                    if (stat.get(k * 8 + col).getType() == SquareType.EMPTY)
                        nombreCaseVide++;
                    else
                        break;
                }

                // Heuristique 2 : pion passé
                int bonusPasse = isPionPasse(c, stat, minPlayer, maxPlayer) ? 3 : 0;

                // Heuristique 3 : victoire imminente (à une case de la dernière rangée)
                int bonusImminence = (avancement == 6) ? 50 : 0;

                // Heuristique 4 : contrôle du centre (colonnes 2, 3, 4, 5)
                double bonusCentre = (col >= 2 && col <= 5) ? 1.0 : 0.0;

                // Heuristique 5 : phalanx (allié directement à gauche ou à droite)
                double bonusPhalanx = 0.0;
                if (col - 1 >= 0 && stat.get(pos * 8 + (col - 1)).getType() == minType) bonusPhalanx += 0.5;
                if (col + 1 <  8 && stat.get(pos * 8 + (col + 1)).getType() == minType) bonusPhalanx += 0.5;

                scoreMin += avancement + nombreCaseVide
                          - getNBMenace(c, stat, maxType)
                          + bonusPasse + bonusImminence + bonusCentre + bonusPhalanx;
            }
        }

        return (scoreMax - scoreMin) * 5 + (nbMax - nbMin);
    }

    /**
     * Vérifie si un pion est "passé" : aucun pion adverse devant lui
     * dans sa colonne et les colonnes adjacentes.
     */
    private static boolean isPionPasse(Square c, List<Square> stat, String player, String opponent) {
        SquareType oppType = SquareType.valueOf(opponent);
        SquareType ownType = SquareType.valueOf(player);
        int pos = c.getX();
        int col = c.getY();
        int dir = player.equals("BLACK") ? 1 : -1;

        // Un pion n'est passé que s'il est suffisamment avancé (au moins à mi-chemin)
        int avancement = player.equals("BLACK") ? pos : (7 - pos);
        if (avancement < 3) return false;

        for (int k = pos + dir; k >= 0 && k < 8; k += dir) {
            // Colonne du pion
            if (stat.get(k * 8 + col).getType() == oppType) return false;
            // Colonne gauche
            if (col - 1 >= 0 && stat.get(k * 8 + (col - 1)).getType() == oppType) return false;
            // Colonne droite
            if (col + 1 < 8  && stat.get(k * 8 + (col + 1)).getType() == oppType) return false;
        }

        return true;
    }

    /**
     * Compte le nombre de pions adverses qui menacent le pion c en diagonale.
     */
    public static int getNBMenace(Square c, List<Square> stat, SquareType adversaire) {
        int n = 0;
        int x = c.getX();
        int y = c.getY();

        int nextX = (adversaire == SquareType.WHITE) ? x - 1 : x + 1;

        if (nextX < 0 || nextX >= 8) return 0;

        if (y - 1 >= 0 && stat.get(nextX * 8 + (y - 1)).getType() == adversaire) n++;
        if (y + 1 < 8  && stat.get(nextX * 8 + (y + 1)).getType() == adversaire) n++;

        return n;
    }
}
