package math;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import javax.naming.spi.DirStateFactory.Result;

/**
 * @author PTung
 */
public class GrandEntier {
    public static final int B = 16;
    public static final int MAXBITLENGTH = 10000000;

    private ArrayList<Integer> listeEntier;

    /**
     * Constructeur de GrandEntier à partir d'une liste d'entiers écrit en base B
     *
     * @param ge
     * @throws IllegalArgumentException
     */
    public GrandEntier(ArrayList<Integer> ge) throws IllegalArgumentException {
        if (ge == null || ge.size() == 0) {
            throw  new IllegalArgumentException("La liste est vide");
        }

        if (ge.size() > 1 && ge.get(ge.size()-1) == 0) {
            throw new IllegalArgumentException("Le dernier chiffre est un 0");
        }

        for (Integer chiffre : ge) {
            if (!(0 <= chiffre && chiffre <= (B-1))) {
                throw new IllegalArgumentException("La liste n'est pas une écriture de la base "+ B);
            }
        }

        listeEntier = ge;
    }

    /**
     * Construit un GrandEntier aléatoire entre les bits 0 jusqu'à (B^numBits-1)
     *
     * @param numBits
     * @param rnd
     * @throws IllegalArgumentException
     */
    public GrandEntier(int numBits, Random rnd) throws IllegalArgumentException {
        if (numBits < 0) {
            throw new IllegalArgumentException("numBits est négatif");
        }

        ArrayList<Integer> values = new ArrayList<Integer>();

        for (int i = 0; i < numBits; ++i) {
            values.add(rnd.nextInt(B));
        }

        if (values.get(values.size()-1) == 0) {
            values.remove(values.size()-1);
        }

        listeEntier = values;
        //System.out.println(this);
    }

    /**
     * Compare ce GrandEntier avec un autre objet
     *
     * @param o
     * @return true si c'est égal, sinon false
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof GrandEntier)) {
            return false;
        }
        if (o == this || (compareTo((GrandEntier)o) == 0)) {
            return true;
        }

        return false;
    }

    /**
     * Retourne la représentation du GrandEntier en chaîne de caractères
     *
     * @return
     */
    @Override
    public String toString() {
        String grandEntierStr = listeEntier.get(0) +" x "+ B +"^0";


        for (int i = 1; i < listeEntier.size(); ++i) {
            grandEntierStr += " + "+ listeEntier.get(i) +" x "+ B +"^"+ i;
        }

        return grandEntierStr;
    }

    public ArrayList<Integer> getListeEntier() {
        return listeEntier;
    }

    /**
     * Compare ce GrandEntier avec un GrandEntier
     *
     * @param e
     * @return -1, 0, 1 si ce GrandEntier est plus petit, égal, ou plus grand que le GrandEntier e
     */
    public int compareTo(GrandEntier e) {
        if (this.getDecimalValue() < e.getDecimalValue()) {
            return -1;
        }
        if (this.getDecimalValue() > e.getDecimalValue()) {
            return 1;
        }

        return 0;
    }

    /**
     * Retourne la longueur de l'écriture
     *
     * @return
     */
    public int length() {
        return listeEntier.size();
    }

    /**
     * Retourne la valeur en base 10 de ce GrandEntier
     *
     * @return
     */
    private int getDecimalValue() {
        int result = (int) (listeEntier.get(0) * (Math.pow(B, 0)));

        for (int i = 1; i < listeEntier.size(); ++i) {
            result += (listeEntier.get(i) * (Math.pow(B, i)));
        }

        return result;
    }

    /**
     * Calcule le produit de this * B^n
     *
     * @param n
     * @return Retourne le résultat du produit
     * @throws IllegalArgumentException
     */
    public GrandEntier shiftLeft(int n) throws IllegalArgumentException {
        if (n < 0) {
            throw new IllegalArgumentException("n doit être positif");
        }

        ArrayList<Integer> values = new ArrayList<Integer>();
        for (int i = 0; i < n; ++i) {
            values.add(0);
        }
        values.addAll(listeEntier);

        cleanList(values);
        return new GrandEntier(values);
    }

    /**
     * Calcule la division de this / B^n
     *
     * @param n
     * @return Retourne le résultat de la division
     * @throws IllegalArgumentException
     */
    public GrandEntier shiftRight(int n) throws IllegalArgumentException {
        if (n < 0) {
            throw new IllegalArgumentException("n doit être positif");
        }

        ArrayList<Integer> values = new ArrayList<Integer>();
        for (int i = (n-1); i < listeEntier.size(); i++) {
            values.add(listeEntier.get(i));
        }

        if (values.isEmpty()) {
            values.add(1);
        }

        cleanList(values);
        return new GrandEntier(values);
    }

    /**
     * Supprime les zéros en fin de liste
     *
     * @param list
     */
    private void cleanList(ArrayList<Integer> list) {
        for (int i = list.size()-1; i > 0; --i) {
            if (list.get(i) <= 0) {
                list.remove(i);
            }
        }
    }

    /**
     * Calcule la somme this + ge
     *
     * @param ge
     * @return Retourne le résultat de la somme
     */
    public GrandEntier add(GrandEntier ge) {
        if (ge == null || ge.length() <= 0) {
            return this;
        }

        ArrayList<Integer> values1 = listeEntier;
        ArrayList<Integer> values2 = ge.getListeEntier();
        ArrayList<Integer> result = new ArrayList<Integer>(values1.size() + values2.size());

        // Ajout de zéros en fin de liste
        values1.add(0);
        values2.add(0);
        ArrayList<Integer> zeroList = new ArrayList<Integer>();
        for (int i = 0; i < Math.abs(values1.size() - values2.size()); i++) {
            zeroList.add(0);
        }
        if (values1.size() < values2.size()) {
            values1.addAll(zeroList);
        } else if (values2.size() < values1.size()) {
            values2.addAll(zeroList);
        }

        for (int i = 0; i < values1.size(); ++i) {
            result.add(0);
        }

        int carry = 0;
        int i = 0;
        for (; i < values1.size()-1; ++i) {
            int value = values1.get(i) + values2.get(i) + carry;
            carry = 0;

            if (value >= B) {
                carry = value/B;
                value %= B;
            }
            result.set(i, value);
        }

        if (carry > 0) {
            if (carry >= B) {
                int car = carry/B;
                carry %= B;
                result.set(i++, result.get(i)+carry);
                result.set(i, result.get(i)+car);
            }
            result.set(i, result.get(i)+carry);
        }
        // Suppression des zéros inutiles
        cleanList(values1);
        cleanList(values2);
        cleanList(result);
        return new GrandEntier(result);
    }

    public GrandEntier sub(GrandEntier ge) {
        if (ge.length() > this.length() || ge.getDecimalValue() >= this.getDecimalValue()) {
            return new GrandEntier(new ArrayList<Integer>(){{add(0);}});
        }

        ArrayList<Integer> result = new ArrayList<Integer>();
        ArrayList<Integer> values1 = (ArrayList<Integer>) listeEntier.clone();
        ArrayList<Integer> values2 = (ArrayList<Integer>) ge.getListeEntier().clone();
        int carry = 0;

        for (int i = 0; i < values1.size(); i++) {
            if (values2.size() > i) {
                values2.set(i, values2.get(i)+carry);
            } else {
                values2.add(carry);
            }
            carry = 0;

            if (values1.get(i) < values2.get(i)) {
                values1.set(i, values1.get(i)+B);
                carry = 1;
            }

            result.add(values1.get(i)-values2.get(i));
        }

        cleanList(result);
        return new GrandEntier(result);
    }
    
    public GrandEntier multiplyLucas(GrandEntier ge){
        ArrayList<Integer> result = new ArrayList<Integer>();
        ArrayList<Integer> values1 = listeEntier;
        ArrayList<Integer> values2 = ge.getListeEntier();
        int carryMul = 0;
        int resTemp = 0;
        int resultIn = 0;
        for(int i = 0 ; i < values1.size()  ; ++i){
            for(int j = 0 ; j < values2.size() ; ++j){
                //System.out.println(values1.get(i) + " * " + values2.get(j) + " + " + carryMul);
                resTemp = values1.get(i) * values2.get(j) + carryMul;
                carryMul = resTemp / B;
                resTemp %= B;
               // System.out.println(carryMul);
               // System.out.println(result.size());
                if(i + j < result.size()){
                    //System.out.println("salut");
                    resultIn = result.get(i + j);
                    //System.out.println(i + j);
                }
                if(resultIn != 0){
                    resTemp += resultIn;
                    //System.out.println(result.get(2));
                    //System.out.println(result);
                    if(i + j + 1 >= result.size()){
                        result.add(i + j + 1,  resTemp / B);
                        //System.out.println("add" + result.get(i + j +1) + " ind " + i + j + 1);
                    } else {
                        int val = result.get(i + j + 1);
                        result.set(i + j + 1, val + resTemp / B);
                        //System.out.println("set" + result.get(i + j +1) + " ind " + i + j + 1);
                    }
                    resTemp %= B;
                    result.set(i + j, resTemp);
                } else {
                    result.add(i + j, resTemp);
                }
                //System.out.println(result.get(i + j) + " indice = " + i + j);
                //System.out.println(carryMul);
                //System.out.println(values2.size());
                if(j == values2.size() - 1){
                    result.add(i + j + 1, carryMul);
                    carryMul = 0;
                    //System.out.println("carry " + result.get(2) + " ind " + i + j + 1);
                }
                resultIn = 0;
            }
        }
        /*
        System.out.println(values1.size());
        String strNb2 = "";
        for(Integer entier : values2){
            strNb2 += entier;
        }
        int nb2 = Integer.parseInt(strNb2);
        int nbDecalages = 0;
        int resTemp = 0;
        System.out.println(values1.size());
        for(int i = values1.size(); i > 0 ; --i, ++nbDecalages){
            int multiDecal = 1;
            for(int j = 0 ; j < nbDecalages ; ++j){
                multiDecal *= B;
                System.out.println(multiDecal);
            }
            resTemp += values1.get(i - 1) * multiDecal * nb2;
            System.out.println(resTemp);
        }
        String strResult = Integer.toString(resTemp,B);
        System.out.println(strResult);
        for(int i = 0 ; i < strResult.length() ; ++i){
            result.add((int)strResult.charAt(i));
        }*/
        cleanList(result);
        return new GrandEntier(result);
    }
    
    /**
     * Calcule la multiplication this * ge
     *
     * @param ge
     * @return Retourne le résultat de la multiplication
     */
    public GrandEntier multiply(GrandEntier ge) {
        if (ge == null || ge.length() <= 0) {
            return this;
        }

        ArrayList<Integer> result = new ArrayList<Integer>();
        ArrayList<Integer> values1 = listeEntier;
        ArrayList<Integer> values2 = ge.getListeEntier();
        int nMax = Math.max(values1.size(), values2.size())+2;
        while (nMax-- > 0) {
            result.add(0);
        }

        // Ajout de -1 en fin de liste
        ArrayList<Integer> negativeList = new ArrayList<Integer>();
        for (int i = 0; i < Math.abs(values1.size() - values2.size()); i++) {
            negativeList.add(-1);
        }
        if (values1.size() < values2.size()) {
            values1.addAll(negativeList);
        } else if (values2.size() < values1.size()) {
            values2.addAll(negativeList);
        }
        int carry = 0;

        for (int i = 0; i < values1.size(); i++) {
            for (int j = 0; j < values2.size(); j++) {
                int val1;
                int val2;

                if (values1.get(i) != -1 && values2.get(j) != -1) {
                    val1 = values1.get(i);
                } else if (values1.get(i) == -1 && values2.get(j) != -1) {
                    val1 = 1;
                } else {
                    val1 = 0;
                }
                if (values1.get(i) != -1 && values2.get(j) != -1) {
                    val2 = values2.get(j);
                } else if (values1.get(i) != -1 && values2.get(j) == -1) {
                    val2 = 1;
                } else {
                    val2 = 0;
                }
                val1 += carry;
                carry = 0;

                int value = val1*val2;
                value += result.get(i+j);

                if (value >= B) {
                    carry += (value / B);
                    value %= B;

                    if (result.size() > (i+j+1)) {
                        result.set(i+j+1, result.get(i+j+1)+carry);
                    } else {
                        result.add(carry);
                    }
                    carry = 0;
                }
                result.set(i+j, value);

                result.add(carry);
                carry = 0;
            }
        }

        cleanList(values1);
        cleanList(values2);
        cleanList(result);
        if (carry > 0) {
            if (carry >= B) {
                int car = carry/B;
                carry %= B;
                result.add(carry);
                result.add(car);
            } else {
                result.add(carry);
            }
        }
        return new GrandEntier(result);
    }

    /**
     * Calcule la multiplication this * ge en utilisant l'algorithme de Karatsuba de manière récursive
     *
     * @param ge
     * @return Retourne le résultat de la multiplication
     */
    public GrandEntier multiplyFast(GrandEntier ge) {
        int N = Math.max(this.length(), ge.length());
        N = (N/2)+(N%2); //

        if (N < 2) { // Lorsque la taille de l'écriture max est égale à 1, multiplication normale
            return this.multiply(ge);
        }

        GrandEntier b = this.shiftRight(N); // b = this/B^N
        GrandEntier a = this.sub(b.shiftLeft(N)); // a = this - (b*B^N)
        GrandEntier d = ge.shiftRight(N); // d = ge/B^N
        GrandEntier c = ge.sub(d.shiftLeft(N)); // c = ge - (d*B^N)

        GrandEntier ac = a.multiplyFast(c);
        GrandEntier bd = d.multiplyFast(d);
        GrandEntier abcd = a.add(b).multiplyFast(c.add(d)); // (a-b)(c-d)

        // ac*B^2N + (ac + bd - abcd) * B^N + bd
        return ac.shiftLeft(2*N).add(ac.add(bd).sub(abcd)).shiftLeft(N).add(bd);
    }

    /**
     * Compare experimentalement les temps de calculs moyens de multiply et multiplyFast
     * pour des entiers générés aléatoirement
     * @author Polytech'Nice-Sophia
     *
     * @param args
     * @throws Exception
     */
    public static void compareSimpleWithFast(String[] args) throws Exception {
        int n = Integer.parseInt(args[0]);
        Random r = new Random(); // génère des nombres pseudo aléatoires
        long fixedSeed = r.nextLong();
        long t0; // heure initiale d'une série de tests de multiplication
        long simpleTime; // heure finale d'une série de tests de multiply
        long fastTime; // heure finale d'une série de tests de multiplyFast
        GrandEntier a, b; // les nombres à multiplier

        System.out.println("\n\n\n");
        System.out.println("Comparaison expérimentale de la complexité de multiply et de multiplyFast");
        System.out.println("-------------------------------------------\n");
        System.out.println("Nombre de répétitions pour chaque taille "+ n +"\n");
        System.out.println("       || temps moyen || temps moyen ");
        System.out.println("# bits ||  multiply   || multiplyFast ");
        System.out.println("-------------------------------------------");

        for (int l = 1; l <= MAXBITLENGTH; l*=2) {
            r.setSeed(fixedSeed);
            System.gc(); // Nettoyage pour avoir des résultats plus significatifs
            t0 = System.currentTimeMillis();
            for (int i = 1; i <= n; i++) {
                a = new GrandEntier(l, r);
                b = new GrandEntier(l, r);
                a.multiply(b);
            }
            simpleTime = System.currentTimeMillis()-t0;
            r.setSeed(fixedSeed); // Pour générer les mêmes nombres pseudo-aléatoire
            System.gc(); // Nettoyage pour avoir des résultats plus significatifs
            t0 = System.currentTimeMillis();
            for (int i = 1; i <= n; i++) {
                a = new GrandEntier(l, r);
                b = new GrandEntier(l, r);
                a.multiplyFast(b);
            }
            fastTime = System.currentTimeMillis()-t0;
            System.out.println(l +" bits ||      "+ simpleTime/n +"      ||     "+fastTime/n);
        }
    }

    public static void main(String[] args) {
        try {
            ArrayList<Integer> numbers1 = new ArrayList<Integer>();
            ArrayList<Integer> numbers2 = new ArrayList<Integer>();
            numbers1.add(15);
            numbers1.add(2);
            numbers2.add(2);
            numbers2.add(10);

            GrandEntier grandEntier1 = new GrandEntier(numbers1);
            GrandEntier grandEntier2 = new GrandEntier(numbers2);
            System.out.println(grandEntier1);
            System.out.println(grandEntier2);
            GrandEntier grandEntier3 = grandEntier1.multiplyLucas(grandEntier2);
          //  System.out.println(grandEntier1.getDecimalValue());
          //  System.out.println(grandEntier2.getDecimalValue());
          //  System.out.println(grandEntier3.getDecimalValue());
          //  System.out.println(grandEntier3);

            compareSimpleWithFast(args);
        } catch (Exception e) {
            System.err.println("Le programme a besoin d'un entier en paramètre " +
                "pour spécifier le nombre de tests dans une série de test");
            e.printStackTrace();
        }
    }
}
