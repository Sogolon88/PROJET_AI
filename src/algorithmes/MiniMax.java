package algorithmes;
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
    private static int mxPdr = 2;
    private static int iter = 0;
    /**
     * L'algorithme minimaw
     * @param stat etat courant du jeu.
     * @return l'action optimale.
     */
    public static Action run(List<Case> board){
        ArrayList<Case> stat = new ArrayList<>();
        for(Case c : board){
            stat.add(new Case(c.getX(), c.getY(), c.getType()));
        }
        Map<Double, Action> actions  = new HashMap<>();

        for(Action a : Game.getActions(stat, "BLACK")){
            actions.put(min_value(Game.resulatAction(stat, a)),  a);  
        }
        
        double max = Collections.max(actions.keySet());
        System.out.println(actions.get(max).getFromCase() + " "+ actions.get(max).getToCase());
        
        return actions.get(max);
    }

    private static double min_value(List<Case> stat){
        if(isCutOff(stat) )
            return eval(stat, CaseType.BLACK.toString());

        double v = Double.POSITIVE_INFINITY;
        for( Action action : Game.getActions(stat, "BLACK")){
            v = Math.min(v, max_value(Game.resulatAction(stat, action)));
        }

        return v;
    }

    private static double max_value(List<Case> stat){
        if(isCutOff(stat))
            return eval(stat, CaseType.BLACK.toString());

        double v = Double.NEGATIVE_INFINITY;
        for( Action action : Game.getActions(stat, "BLACK")){
            v = Math.max(v, min_value(Game.resulatAction(stat, action)));
        }

        return v;
    }

    /**
     * La fonction d'évalution d'un état du jeu
     * @param stat état à evaluer
     * @return l'estimation de l'utilité
     */
    private static double eval(List<Case> stat, String turn){
        int nbPieces = 0;
        int sommeValuePions = 0;
        for(Case c : stat){
            if(c.getType().equals(turn)){
                nbPieces++;
                int pos = c.getX();
                int nombreCaseVide = 0;
                for(int k = pos; k <8; k++){
                    if( stat.get(k*8+ c.getY()).getType().equals(CaseType.EMPTY))
                        nombreCaseVide ++;
                    else
                        break;
                }
                sommeValuePions += (pos + nombreCaseVide - getNBMenace(c, stat));
            }
            
        }

        return (nbPieces + sommeValuePions);
    }


    /**
     * La fonction de test de condition d'arret.
     * @param stat
     * @return
     */
    private static boolean isCutOff(List<Case> stat){
        int nbBlackPiece = 0;
        int nbWhitePiece = 0;

        if(iter == mxPdr)
            return true;

        for (Case c : stat) {
            if (c.getType() == CaseType.BLACK) {
                nbBlackPiece++;
            } else if (c.getType() == CaseType.WHITE) {
                nbWhitePiece++;
            }
        }

        if (nbBlackPiece == 0) 
            return true;
        if (nbWhitePiece == 0) 
            return true;

        for ( int j = 0; j < 8; j++ ) {
            if ( stat.get(0 * 8 + j).getType() == CaseType.WHITE )
                return true;
            else if ( stat.get(7 * 8 + j).getType() == CaseType.BLACK )
                return true;
        }

        return false;
    }

    private static int getNBMenace(Case c, List<Case> stat){
        int n = 0;
        if(c.getY()== 7 && stat.get(c.getX()*8 + c.getY()-1).getType().equals(CaseType.WHITE) )
            n++;
        else if(c.getY() == 0 && stat.get(c.getX()*8 + c.getY()+1).getType().equals(CaseType.WHITE))
            n++;
        else if(stat.get((c.getX()+1)*8 + c.getY()+1).getType().equals(CaseType.WHITE) ||
                (stat.get((c.getX()+1)*8 + c.getY()-1).getType().equals(CaseType.WHITE)) )
            n++;
        if(stat.get((c.getX()+1)*8 + c.getY()+1).getType().equals(CaseType.WHITE) &&
                (stat.get((c.getX()+1)*8 + c.getY()-1).getType().equals(CaseType.WHITE)) )
            n =2;

        return n;
    }
}
