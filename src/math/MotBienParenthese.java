package math;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author PTung
 */
public class MotBienParenthese {
    public static final char MOT_VIDE = 'ε';
    public static final String STR_VIDE = "ε";
    public ArrayList<String> AllMotsBP;

    /**
     * Affiche tous les mots bien parenthésés de longueur 2n
     *
     * @param n
     */
    public static ArrayList<String> enumMotsBP(int n) {
        if ( n == 1 ){
            return new ArrayList<String>(Arrays.asList("()"));
        }
        ArrayList<String> BPrecup = enumMotsBP(n-1);
        ArrayList<String> newBP = new ArrayList<String>();
        for(String str : BPrecup){
            newBP.add("()" + str);
            newBP.add(str + "()");
            newBP.add("(" + str + ")");
        }
        return newBP;
    }
}
