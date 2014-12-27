package test;

import java.util.ArrayList;

import math.MotBienParenthese;

/**
 * @author PTung
 */
public class TestMotBienParenthese {
    public static void main(String[] args) {
        int n = 3;
        ArrayList<String> allBP = MotBienParenthese.enumMotsBP(n); 
        for(String str : allBP){
            System.out.println(str);
        }
    }
}
