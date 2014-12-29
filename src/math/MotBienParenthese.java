package math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author PTung
 */
public class MotBienParenthese {

    /**
     * Affiche tous les mots bien parenthésés de longueur 2n
     *
     * @param n
     */
    public static Set<String> enumMotsBP(int n) {
        if (n == 1) {
        return new HashSet<String>(Arrays.asList("()"));
        }
        Set<String> actuelMotsBP = enumMotsBP(n-1);
        Set<String> nouvMotsBP = new HashSet<String>();
        for (String mot : actuelMotsBP) {
        nouvMotsBP.add("()"+ mot);
        nouvMotsBP.add(mot +"()");
        nouvMotsBP.add("("+ mot +")");
        }
        return nouvMotsBP;
    }
    
    /*
     * Q2 - D'apres nos recherche sur internet, ici les C_n correspondent 
     * aux nombres Catalans, défini par Cn = (2n)!/((n+1)! * n!). Afin de déterminer
     * une formule de recurrence on détermine Cn+1 = (2n+2)!/((n+1)!(n+2)!)
     * Ce qui nous donne le résultat suivant :
     *      C_n+1 = (2(2n + 1)/(n+2)) * C_n  pour n >= 0
     *       
     */
    
    /*
     * calcul le nombre catalan correspondant au nombre passé en paramètre
     */
    public static long catalan (int n){
        if(n == 1) return 1;
        n -= 1;
        long calcul = (2*(2*n+1)/(n+2)) * catalan(n);
        return calcul;
    }
}
