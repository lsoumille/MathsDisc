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
    public static double catalan (int n){
        if(n == 1) return 1;
        n -= 1;
        double calcul = ((double)2*(2*n+1)/(n+2)) * catalan(n);
        return calcul;
    }
    
    /*
     * catalan2 methode itérative utilisant un tableau de long
     */
    public static long[] catalan2 (int n){
        long[] nbCat = new long[n+1];
        nbCat[0] = nbCat[1] = 1;
        for(int i = 2 ; i <= n ; ++i){
            nbCat[i] = Math.round(((double) 2*(2*(i-1)+1)/((i-1)+2)) * nbCat[i - 1]);
        }
        return nbCat;
    }
    
    /*
     * test si un mot est bien parenthésé
     */
    public static boolean testParenthese(String mot){
       int kLength = mot.length();
       int cptParent = 0;
       for(int i = 0 ; i < kLength ; ++i){
           if(mot.charAt(i)== '('){
               ++cptParent;
           } else {
               --cptParent;
           }
           if(cptParent < 0){
               return false;
           }
       }
       return (cptParent == 0) ? true : false;
    }
    
    /*
     * affiche un mot bien parenthésé comme "il le faut"
     */
    public static void afficheMotBP(String mot){
        int kLength = mot.length();
        char prec = 0;
        int nbIndent = 0;
        char current = 0;
        for(int i = 0 ; i < kLength ; ++i){
            current = mot.charAt(i);
            if(current == '(' && prec == '('){
                ++nbIndent;
            } else if (current == ')' && prec == ')'){
                --nbIndent;
            }
            String indentation = "";
            for(int j = 0 ; j < nbIndent ; ++j){
                indentation += " ";
            }
            System.out.println(indentation + current); 
            prec = mot.charAt(i);
        }
    }
    
    /*
     * retourne la profondeur d'un mot
     */
    public static int profondeur(String mot){
        int kLength = mot.length();
        char prec = '(';
        int profondeur = 0;
        int max = 0;
        for(int i = 0 ; i < kLength ; ++i){
            if(prec == '(' && mot.charAt(i) == '('){
                ++profondeur;
            } else {
                if(profondeur > max){
                    max = profondeur;
                }
                profondeur = 0;
            }
        }
        return max;
    }
}
