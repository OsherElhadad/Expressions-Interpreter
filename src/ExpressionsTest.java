
/*
 * Osher Elhadad
 *
 * This file-
 * Create an expression with at least three variables.
 * Print the expression.
 * Print the value of the expression with an assignment to every variable.
 * Print the Nandified version of the expression.
 * Print the Norified version of the expression.
 * Print the simplified version of the expression.
 */

import java.util.Map;
import java.util.TreeMap;

/**
 * This ExpressionsTest class has main function that-
 * Create an expression with at least three variables.
 * Print the expression.
 * Print the value of the expression with an assignment to every variable.
 * Print the Nandified version of the expression.
 * Print the Norified version of the expression.
 * Print the simplified version of the expression.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public class ExpressionsTest {

    /**
     * This is the main function.
     * it creates expressions and prints them.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {

        // 1. Create an expression with three variables- ((x | T) & ((x & y) ^ (z | y))).
        Expression expression = new And(
                                       new Or(new Var("x"), new Val(true)),
                                       new Xor(
                                               new And(new Var("x"), new Var("y")),
                                               new Or(new Var("z"), new Var("y"))));

        // 2. Print the expression- ((x | T) & ((x & y) ^ (z | y))).
        System.out.println(expression);

        // 3. Print the value of the expression with an assignment to every variable- x:true, y:false, z:true.
        Map<String, Boolean> map = new TreeMap<>();
        map.put("x", true);
        map.put("y", false);
        map.put("z", true);
        try {
            System.out.println(expression.evaluate(map));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 4. Print the Nandified version of the expression.
        System.out.println(expression.nandify());

        // 5. Print the Norified version of the expression.
        System.out.println(expression.norify());

        // 6. Print the simplified version of the expression.
        System.out.println(expression.simplify());
    }
}
