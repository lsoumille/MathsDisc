package math;

/**
 * @author PTung
 */
public class MotBienParenthese {
    public static final char MOT_VIDE = 'ε';

    /**
     * Affiche tous les mots bien parenthésés de longueur 2n
     *
     * @param n
     */
    public static String enumMotsBP(int n) {
        if (n == 0) {
            return new String();
        }

        n = (n%2==0) ? n : 2*n;

        String motsBP = "()"+ enumMotsBP(n-2) +" ; ";
        motsBP += enumMotsBP(n-2)+"() ; ";
        motsBP += "("+ enumMotsBP(n-2) +") ; ";

        return motsBP;
    }
}
