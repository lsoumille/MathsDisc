package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import math.MotBienParenthese;

/**
 * @author PTung
 */
public class TestMotBienParenthese {
    
    public static void testCatalan(int n){
        long catalani;
        long t0;
        long temps;
        for(int i=10;i<=n;i++){
           t0 = System.nanoTime();//currentTimeMillis();
          // catalani=MotBienParenthese.catalan(i);
           temps= System.nanoTime() - t0;//currentTimeMillis()-t0;
           System.out.println("Temps de calcul du "+i+"ème nombre de Catalan : "+temps);
        }
    }//testCatalan
    
    public static void testCatalan2(int n){
        long[] catalani;
        long t0;
        long temps;
        for(int i=10;i<=n;i++){
           t0 = System.nanoTime();//currentTimeMillis();
           catalani=MotBienParenthese.catalan2(i);
           temps= System.nanoTime() - t0;//currentTimeMillis()-t0;
           System.out.println("Temps de calcul du "+i+"ème nombre de Catalan : "+temps);
        }
    }//testCatalan2
        
    public static void testMotsParenthese(){
        int n = 3;
        Set<String> allBP = MotBienParenthese.enumMotsBP(n); 
        for(String str : allBP){
            System.out.println(str);
        }
    }
    
    public static void testParenthesage(){
        String motBP = "(())";
        String motMP = "())(";
        System.out.println(motBP + " parenthesage ? " 
                                 + MotBienParenthese.testParenthese(motBP));
        System.out.println(motMP + " parenthesage ? " 
                                 + MotBienParenthese.testParenthese(motMP));
    }
        
    public static void main(String[] args) {
        System.out.println(MotBienParenthese.profondeur("(((())))"));
    }
   
}