package tournoi_ai;

/**
 * Configuration d'une IA pour le tournoi.
 * Regroupe la profondeur de recherche et la fonction d'évaluation.
 * @author keita
 */
public class IAConfig {

    /** Nom lisible de l'IA, ex: "AlphaBeta-prof4-evalStrategique" */
    public final String nom;

    /** Profondeur de recherche (2, 4 ou 6) */
    public final int profondeur;

    /**
     * Fonction d'évaluation :
     *   1 = evalMateriel
     *   2 = evalPositionnelle
     *   3 = evalStrategique
     */
    public final int evalFonction;

    /**
     * Crée une configuration d'IA.
     * @param profondeur   profondeur de recherche
     * @param evalFonction fonction d'évaluation (1, 2 ou 3)
     */
    public IAConfig(int profondeur, int evalFonction) {
        this.profondeur   = profondeur;
        this.evalFonction = evalFonction;
        this.nom          = "AlphaBeta-prof" + profondeur + "-eval" + nomEval(evalFonction);
    }

    /**
     * Retourne le nom lisible de la fonction d'évaluation.
     */
    public static String nomEval(int evalFonction) {
        switch (evalFonction) {
            case 1:  return "Materiel";
            case 2:  return "Positionnel";
            case 3:  return "Strategique";
            default: return "Inconnu";
        }
    }

    @Override
    public String toString() {
        return nom;
    }
}
