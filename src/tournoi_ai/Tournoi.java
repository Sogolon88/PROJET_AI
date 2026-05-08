package tournoi_ai;

import java.util.ArrayList;
import java.util.List;

import algorithmes.AlphaBeta;
import main.Board;
import main.Game;
import main.Move;
import main.Pieces;
import main.Square;
import main.SquareType;

/**
 * Classe gérant le tournoi entre les IA.
 * Lance N parties entre deux IA et collecte les statistiques.
 * @author keita
 */
public class Tournoi {

    private static final int NB_PARTIES_DEFAULT = 50;

    /**
     * Lance un tournoi complet entre deux configurations d'IA.
     * Les couleurs alternent : IA1=WHITE les parties paires, IA1=BLACK les impaires.
     *
     * @param ia1       configuration de l'IA 1
     * @param ia2       configuration de l'IA 2
     * @param nbParties nombre de parties à jouer
     */
    public static void jouer(IAConfig ia1, IAConfig ia2, int nbParties) {
        int victoiresIA1 = 0;
        int victoiresIA2 = 0;
        long totalTempsIA1 = 0;
        long totalTempsIA2 = 0;
        int nbCoupsIA1 = 0;
        int nbCoupsIA2 = 0;

        System.out.println("\n========================================");
        System.out.println("  TOURNOI : " + ia1.nom + " vs " + ia2.nom);
        System.out.println("  " + nbParties + " parties");
        System.out.println("========================================");

        for (int i = 0; i < nbParties; i++) {
            // Alternance des couleurs
            String couleurIA1 = (i % 2 == 0) ? "WHITE" : "BLACK";
            String couleurIA2 = couleurIA1.equals("WHITE") ? "BLACK" : "WHITE";

            // Résultat : [victoireIA1, victoireIA2, tempsIA1, tempsIA2, coupsIA1, coupsIA2]
            long[] res = jouerUnePartie(ia1, couleurIA1, ia2, couleurIA2);

            if (res[0] == 1) victoiresIA1++;
            else             victoiresIA2++;

            totalTempsIA1 += res[2];
            totalTempsIA2 += res[3];
            nbCoupsIA1    += res[4];
            nbCoupsIA2    += res[5];

            // Affichage progression
            if ((i + 1) % 10 == 0)
                System.out.println("  Parties jouées : " + (i + 1) + "/" + nbParties);
        }

        // Affichage des résultats finaux
        afficherResultats(ia1, ia2, nbParties, victoiresIA1, victoiresIA2,
                          totalTempsIA1, totalTempsIA2, nbCoupsIA1, nbCoupsIA2);
    }

    /**
     * Lance une partie entre deux IA et retourne les statistiques.
     * @return tableau [victoireIA1, victoireIA2, tempsIA1ms, tempsIA2ms, coupsIA1, coupsIA2]
     */
    private static long[] jouerUnePartie(IAConfig ia1, String couleurIA1,
                                          IAConfig ia2, String couleurIA2) {
        Board board   = new Board();
        Pieces pieces = new Pieces();
        String turn   = "WHITE";

        long tempsIA1 = 0, tempsIA2 = 0;
        int  coupsIA1 = 0, coupsIA2 = 0;
        int  nbTours  = 0;
        int  maxTours = 300; // sécurité anti-boucle infinie

        while (!isGameOver(board, pieces, turn) && nbTours < maxTours) {
            IAConfig iaActive  = turn.equals(couleurIA1) ? ia1 : ia2;
            boolean  estIA1    = turn.equals(couleurIA1);

            long debut = System.currentTimeMillis();
            Move move  = AlphaBeta.run(board, turn, iaActive.profondeur, iaActive.evalFonction);
            long duree = System.currentTimeMillis() - debut;

            if (move == null) break;

            // Appliquer le coup sur le vrai plateau
            appliquerCoup(board, pieces, move, turn);

            if (estIA1) { tempsIA1 += duree; coupsIA1++; }
            else        { tempsIA2 += duree; coupsIA2++; }

            turn = turn.equals("WHITE") ? "BLACK" : "WHITE";
            nbTours++;
        }

        // Déterminer le vainqueur
        SquareType winner = getWinner(board, pieces, turn);
        int victoireIA1 = 0, victoireIA2 = 0;
        if (winner != null) {
            if (winner.toString().equals(couleurIA1)) victoireIA1 = 1;
            else                                      victoireIA2 = 1;
        }

        return new long[]{ victoireIA1, victoireIA2, tempsIA1, tempsIA2, coupsIA1, coupsIA2 };
    }

    /**
     * Applique un coup sur le plateau et met à jour les pièces.
     */
    private static void appliquerCoup(Board board, Pieces pieces, Move move, String turn) {
        int fromIdx = move.getFromCase().getX() * board.getCol() + move.getFromCase().getY();
        int toIdx   = move.getToCase().getX()   * board.getCol() + move.getToCase().getY();

        Square from = board.get(fromIdx);
        Square to   = board.get(toIdx);

        if (to.getType() != SquareType.EMPTY) {
            // Capture
            if (turn.equals("WHITE")) pieces.setNbBlackPieces(pieces.getNbBlackPieces() - 1);
            else                      pieces.setNbWhitePieces(pieces.getNbWhitePieces() - 1);
        }

        to.setType(from.getType());
        from.setType(SquareType.EMPTY);
    }

    /**
     * Vérifie si la partie est terminée.
     */
    private static boolean isGameOver(Board board, Pieces pieces, String turn) {
        if (pieces.getNbBlackPieces() == 0 || pieces.getNbWhitePieces() == 0) return true;

        for (int j = 0; j < board.getCol(); j++) {
            if (board.get(j).getType() == SquareType.WHITE) return true;
            if (board.get(7 * board.getCol() + j).getType() == SquareType.BLACK) return true;
        }

        if (AlphaBeta.getActions(board.getboardSquares(), turn).isEmpty()) return true;

        return false;
    }

    /**
     * Retourne le vainqueur de la partie.
     */
    private static SquareType getWinner(Board board, Pieces pieces, String turn) {
        if (pieces.getNbBlackPieces() == 0) return SquareType.WHITE;
        if (pieces.getNbWhitePieces() == 0) return SquareType.BLACK;

        for (int j = 0; j < board.getCol(); j++) {
            if (board.get(j).getType() == SquareType.WHITE) return SquareType.WHITE;
            if (board.get(7 * board.getCol() + j).getType() == SquareType.BLACK) return SquareType.BLACK;
        }

        // Joueur bloqué : l'adversaire gagne
        if (AlphaBeta.getActions(board.getboardSquares(), turn).isEmpty())
            return SquareType.valueOf(turn.equals("WHITE") ? "BLACK" : "WHITE");

        return null;
    }

    /**
     * Affiche le tableau récapitulatif des résultats.
     */
    private static void afficherResultats(IAConfig ia1, IAConfig ia2, int nbParties,
                                           int victoiresIA1, int victoiresIA2,
                                           long tempsIA1, long tempsIA2,
                                           int coupsIA1, int coupsIA2) {
        double pctIA1 = (victoiresIA1 * 100.0) / nbParties;
        double pctIA2 = (victoiresIA2 * 100.0) / nbParties;
        double moyTempsIA1 = coupsIA1 > 0 ? (double) tempsIA1 / coupsIA1 : 0;
        double moyTempsIA2 = coupsIA2 > 0 ? (double) tempsIA2 / coupsIA2 : 0;

        System.out.println("\n--- RESULTATS ---");
        System.out.printf("%-35s : %3d victoires (%5.1f%%)%n", ia1.nom, victoiresIA1, pctIA1);
        System.out.printf("%-35s : %3d victoires (%5.1f%%)%n", ia2.nom, victoiresIA2, pctIA2);
        System.out.printf("Temps moyen/coup %-20s : %.1f ms%n", ia1.nom, moyTempsIA1);
        System.out.printf("Temps moyen/coup %-20s : %.1f ms%n", ia2.nom, moyTempsIA2);
        System.out.println("----------------------------------------\n");
    }

    /**
     * Lance le tournoi complet :
     *   - Tournoi 1 : même profondeur, fonctions d'évaluation différentes
     *   - Tournoi 2 : même fonction d'évaluation, profondeurs différentes
     */
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║         TOURNOI DES IA - LA PERCEE       ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // --- Tournoi 1 : même profondeur (4), fonctions différentes ---
        System.out.println("\n>>> TOURNOI 1 : Impact de la fonction d'evaluation (profondeur=4)");
        IAConfig eval1_prof4 = new IAConfig(4, 1);  // evalMateriel
        IAConfig eval2_prof4 = new IAConfig(4, 2);  // evalPositionnelle
        IAConfig eval3_prof4 = new IAConfig(4, 3);  // evalStrategique

        jouer(eval1_prof4, eval2_prof4, NB_PARTIES_DEFAULT);
        jouer(eval1_prof4, eval3_prof4, NB_PARTIES_DEFAULT);
        jouer(eval2_prof4, eval3_prof4, NB_PARTIES_DEFAULT);

        // --- Tournoi 2 : même fonction (evalStrategique), profondeurs différentes ---
        System.out.println("\n>>> TOURNOI 2 : Impact de la profondeur (eval=Strategique)");
        IAConfig strat_prof2 = new IAConfig(2, 3);
        IAConfig strat_prof4 = new IAConfig(4, 3);
        IAConfig strat_prof6 = new IAConfig(6, 3);

        jouer(strat_prof2, strat_prof4, NB_PARTIES_DEFAULT);
        jouer(strat_prof2, strat_prof6, NB_PARTIES_DEFAULT);
        jouer(strat_prof4, strat_prof6, NB_PARTIES_DEFAULT);
    }
}
