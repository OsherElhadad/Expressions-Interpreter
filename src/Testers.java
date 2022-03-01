public class Testers {
    public static void main(String args[]) throws Exception {
        Val T = new Val(true);
        Val F = new Val(false);
        Var X = new Var("X");
        Var Y = new Var("Y");
        //----------------------------------------
        //----------PART 1 - TESTERS -------------
        //----------------------------------------
        //try for Nor
        System.out.println("\n----------PART 1 - TESTERS -------------\n");
        try {
            Expression ex = new Nor(F, T);
            Expression ex1 = new Nor(F, F);
            System.out.println("************** Nor **************");
            System.out.println(ex);
            boolean answer = ex.evaluate();
            System.out.println(answer);
            System.out.println("\n");
            System.out.println(ex1);
            boolean answer1 = ex1.evaluate();
            System.out.println(answer1);
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //try for Nand
        try {
            Expression ex = new Nand(F, T);
            Expression ex1 = new Nand(T, T);
            System.out.println("************** Nand **************");
            System.out.println(ex);
            boolean answer = ex.evaluate();
            System.out.println(answer);
            System.out.println("\n");
            System.out.println(ex1);
            boolean answer1 = ex1.evaluate();
            System.out.println(answer1);
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //try for Xnor
        try {
            Expression ex = new Xnor(F, T);
            Expression ex1 = new Xnor(T, T);
            System.out.println("************** Xnor **************");
            System.out.println(ex);
            boolean answer = ex.evaluate();
            System.out.println(answer);
            System.out.println("\n");
            System.out.println(ex1);
            boolean answer1 = ex1.evaluate();
            System.out.println(answer1);
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //try for Xor
        try {
            Expression ex = new Xor(F, T);
            Expression ex1 = new Xor(T, T);
            System.out.println("************** Xor **************");
            System.out.println(ex);
            boolean answer = ex.evaluate();
            System.out.println(answer);
            System.out.println("\n");
            System.out.println(ex1);
            boolean answer1 = ex1.evaluate();
            System.out.println(answer1);
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //try for Or
        try {
            Expression ex = new Or(F, T);
            System.out.println("************** Or **************");
            System.out.println(ex);
            boolean answer = ex.evaluate();
            System.out.println(answer);
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //try for And
        try {
            Expression ex = new And(F, T);
            System.out.println("************** And **************");
            System.out.println(ex);
            boolean answer = ex.evaluate();
            System.out.println(answer);
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //try for Not
        try {
            Expression ex = new Not(F);
            Expression ex1 = new Not(T);
            System.out.println("************** Not **************");
            System.out.println(ex);
            boolean answer = ex.evaluate();
            System.out.println(answer);
            System.out.println("\n");
            System.out.println(ex1);
            boolean answer1 = ex1.evaluate();
            System.out.println(answer1);
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //try to check the basic rules
        try {
            Expression ex = new And(F, new Or(T, new Xor(X, F)));
            System.out.println("************** basic rules **************");
            System.out.println(ex);
            System.out.println(" -----Need to throw Exception-------");
            boolean answer = ex.evaluate();
            System.out.println(answer);
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //tester for getVariables
        try {
            Expression ex = new Xor(new And(F, Y), X);
            System.out.println("************** getVariables **************");
            System.out.println(ex);
            System.out.println(ex.getVariables().toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //try to implement the assign
        try {
            Expression ex = new Xor(F, X);
            System.out.println("************** assign **************");
            System.out.println(ex);
            Expression ex1 = ex.assign("X", new Or(Y, T));
            System.out.println(ex1);
            System.out.println(" -----Need to throw Exception-------");
            boolean answer = ex1.evaluate();
            System.out.println(answer);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //try to change the value of the Var X
        try {
            Expression ex = new Or(F, X);
            System.out.println("\n");
            System.out.println("------ change the value of the Var X = True ------");
            System.out.println(ex);
            Expression ex1 = ex.assign("X", T);
            System.out.println(ex1);
            boolean answer = ex1.evaluate();
            System.out.println(answer);
            System.out.println("------ Need to be True ------");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //----------------------------------------
        //----------PART 2 - TESTERS -------------
        //----------------------------------------
        System.out.println("\n\n\n----------PART 2 - TESTERS -------------\n");
        //try for nandify
        try {
            Expression ex = new Or(T, F);
            Expression ex1 = new And(T, F);
            Expression ex2 = new Not(T);
            Expression ex3 = new Nor(T, F);
            Expression ex4 = new Xor(T, F);
            Expression ex5 = new Xnor(T, F);
            System.out.println("************** nandify **************");
            System.out.println("Or: " + ex);
            Expression answer = ex.nandify();
            System.out.println(answer);
            System.out.println("\n");
            System.out.println("And: " + ex1);
            Expression answer1 = ex1.nandify();
            System.out.println(answer1);
            System.out.println("\n");
            System.out.println("Not: " + ex2);
            Expression answer2 = ex2.nandify();
            System.out.println(answer2);
            System.out.println("\n");
            System.out.println("Nor: " + ex3);
            Expression answer3 = ex3.nandify();
            System.out.println(answer3);
            System.out.println("\n");
            System.out.println("Xor: " + ex4);
            Expression answer4 = ex4.nandify();
            System.out.println(answer4);
            System.out.println("\n");
            System.out.println("Xnor: " + ex5);
            Expression answer5 = ex5.nandify();
            System.out.println(answer5);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //try for norify
        try {
            Expression ex = new Or(T, F);
            Expression ex1 = new And(T, F);
            Expression ex2 = new Not(T);
            Expression ex3 = new Nand(T, F);
            Expression ex4 = new Xor(T, F);
            Expression ex5 = new Xnor(T, F);
            System.out.println("************** norify **************");
            System.out.println("Or: " + ex);
            Expression answer = ex.norify();
            System.out.println(answer);
            System.out.println("\n");
            System.out.println("And: " + ex1);
            Expression answer1 = ex1.norify();
            System.out.println(answer1);
            System.out.println("\n");
            System.out.println("Not: " + ex2);
            Expression answer2 = ex2.norify();
            System.out.println(answer2);
            System.out.println("\n");
            System.out.println("Nand: " + ex3);
            Expression answer3 = ex3.norify();
            System.out.println(answer3);
            System.out.println("\n");
            System.out.println("Xor: " + ex4);
            Expression answer4 = ex4.norify();
            System.out.println(answer4);
            System.out.println("\n");
            System.out.println("Xnor: " + ex5);
            Expression answer5 = ex5.norify();
            System.out.println(answer5);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        //----------------------------------------
        //----------PART 3 - TESTERS -------------
        //----------------------------------------
        System.out.println("\n\n\n----------PART 3 - TESTERS -------------\n");
        //checks simplify
        System.out.println("************** Simplify **************");
        //checks for And
        try {
            Expression ex = new And(F, X);
            Expression ex1 = new And(T, X);
            Expression ex2 = new And(T, T);
            Expression ex3 = new And(F, F);
            Expression ex4 = new And(T, F);
            Expression ex5 = new And(F, T);
            Expression ex6 = new And(X, X);
            Expression ex7 = new And(X, Y);
            System.out.println("\n");
            System.out.println("************** AND - simplify **************");
            System.out.println(ex);
            System.out.println(ex.simplify());
            boolean answer = ex.simplify().evaluate();
            System.out.println(answer);
            System.out.println("------ Need to be False ------");
            System.out.println("\n");
            System.out.println(ex1);
            System.out.println(ex1.simplify());
            System.out.println("------ Need to be the X ------");
            System.out.println("\n");
            System.out.println(ex6);
            System.out.println(ex6.simplify());
            System.out.println("------ Need to be the X ------");
            System.out.println("\n");
            System.out.println(ex2);
            System.out.println(ex2.simplify());
            System.out.println("------ Need to be True ------");
            System.out.println("\n");
            System.out.println(ex4);
            System.out.println(ex4.simplify());
            System.out.println("------ Need to be False ------");
            System.out.println("\n");
            System.out.println(ex5);
            System.out.println(ex5.simplify());
            System.out.println("------ Need to be False ------");
            System.out.println("\n");
            System.out.println(ex3);
            System.out.println(ex3.simplify());
            System.out.println("------ Need to be False ------");
            System.out.println("\n");
            System.out.println(ex7);
            System.out.println(ex7.simplify());
            System.out.println("------ Need to be THE SAME ------");
            System.out.println("\n");
            System.out.println(new And(new Var("x"), new Var("x")));
            System.out.println((new And(new Var("x"), new Var("x"))).simplify());
            System.out.println("------ Need to be the X ------");

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //checks for Or
        try {
            Expression ex = new Or(F, X);
            Expression ex1 = new Or(T, X);
            Expression ex11 = new Or(X, F);
            Expression ex111 = new Or(X, T);
            Expression ex2 = new Or(T, T);
            Expression ex3 = new Or(F, F);
            Expression ex4 = new Or(T, F);
            Expression ex5 = new Or(F, T);
            Expression ex6 = new Or(X, X);
            Expression ex7 = new Or(X, Y);
            System.out.println("\n");
            System.out.println("************** OR - simplify **************");
            System.out.println(ex);
            System.out.println(ex.simplify());
            System.out.println("------ Need to be the X ------");
            System.out.println("\n");
            System.out.println(ex11);
            System.out.println(ex11.simplify());
            System.out.println("------ Need to be the X ------");
            System.out.println("\n");
            System.out.println(ex1);
            System.out.println(ex1.simplify());
            System.out.println("------ Need to be the True ------");
            System.out.println("\n");
            System.out.println(ex111);
            System.out.println(ex111.simplify());
            System.out.println("------ Need to be the True ------");
            System.out.println("\n");
            System.out.println(ex2);
            System.out.println(ex2.simplify());
            System.out.println("------ Need to be True ------");
            System.out.println("\n");
            System.out.println(ex4);
            System.out.println(ex4.simplify());
            System.out.println("------ Need to be True ------");
            System.out.println("\n");
            System.out.println(ex5);
            System.out.println(ex5.simplify());
            System.out.println("------ Need to be True ------");
            System.out.println("\n");
            System.out.println(ex3);
            System.out.println(ex3.simplify());
            System.out.println("------ Need to be False ------");
            System.out.println("\n");
            System.out.println(ex6);
            System.out.println(ex6.simplify());
            System.out.println("------ Need to be the X ------");
            System.out.println("\n");
            System.out.println(ex7);
            System.out.println(ex7.simplify());
            System.out.println("------ Need to be THE SAME ------");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //checks for Xor
        try {
            Expression ex = new Xor(F, X);
            Expression ex1 = new Xor(T, X);
            Expression ex11 = new Xor(X, F);
            Expression ex111 = new Xor(X, T);
            Expression ex6 = new Xor(X, X);
            Expression ex7 = new Xor(X, Y);

            System.out.println("\n");
            System.out.println("************** XOR - simplify **************");
            System.out.println(ex);
            System.out.println(ex.simplify());
            System.out.println("------ Need to be the X ------");
            System.out.println("\n");
            System.out.println(ex11);
            System.out.println(ex11.simplify());
            System.out.println("------ Need to be the X ------");
            System.out.println("\n");
            System.out.println(ex1);
            System.out.println(ex1.simplify());
            System.out.println("------ Need to be the ~(X) ------");
            System.out.println("\n");
            System.out.println(ex111);
            System.out.println(ex111.simplify());
            System.out.println("------ Need to be the ~(X) ------");
            System.out.println("\n");
            System.out.println(ex6);
            System.out.println(ex6.simplify());
            System.out.println("------ Need to be the False ------");
            System.out.println("\n");
            System.out.println(ex7);
            System.out.println(ex7.simplify());
            System.out.println("------ Need to be THE SAME ------");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        //checks for Nand
        try {
            Expression ex = new Nand(F, X);
            Expression ex1 = new Nand(T, X);
            Expression ex11 = new Nand(X, F);
            Expression ex111 = new Nand(X, T);
            Expression ex6 = new Nand(X, X);
            Expression ex7 = new Nand(X, Y);

            System.out.println("\n");
            System.out.println("************** Nand - simplify **************");
            System.out.println(ex);
            System.out.println(ex.simplify());
            System.out.println("------ Need to be the True ------");
            System.out.println("\n");
            System.out.println(ex11);
            System.out.println(ex11.simplify());
            System.out.println("------ Need to be the True ------");
            System.out.println("\n");
            System.out.println(ex1);
            System.out.println(ex1.simplify());
            System.out.println("------ Need to be the ~(X) ------");
            System.out.println("\n");
            System.out.println(ex111);
            System.out.println(ex111.simplify());
            System.out.println("------ Need to be the ~(X) ------");
            System.out.println("\n");
            System.out.println(ex6);
            System.out.println(ex6.simplify());
            System.out.println("------ Need to be the ~(X) ------");
            System.out.println("\n");
            System.out.println(ex7);
            System.out.println(ex7.simplify());
            System.out.println("------ Need to be THE SAME ------");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //checks for Nor
        try {
            Expression ex = new Nor(F, X);
            Expression ex1 = new Nor(T, X);
            Expression ex11 = new Nor(X, F);
            Expression ex111 = new Nor(X, T);
            Expression ex6 = new Nor(X, X);
            Expression ex7 = new Nor(X, Y);

            System.out.println("\n");
            System.out.println("************** Nor - simplify **************");
            System.out.println(ex);
            System.out.println(ex.simplify());
            System.out.println("------ Need to be the ~(X) ------");
            System.out.println("\n");
            System.out.println(ex11);
            System.out.println(ex11.simplify());
            System.out.println("------ Need to be the ~(X) ------");
            System.out.println("\n");
            System.out.println(ex1);
            System.out.println(ex1.simplify());
            System.out.println("------ Need to be the False ------");
            System.out.println("\n");
            System.out.println(ex111);
            System.out.println(ex111.simplify());
            System.out.println("------ Need to be the False ------");
            System.out.println("\n");
            System.out.println(ex6);
            System.out.println(ex6.simplify());
            System.out.println("------ Need to be the ~(X) ------");
            System.out.println("\n");
            System.out.println(ex7);
            System.out.println(ex7.simplify());
            System.out.println("------ Need to be THE SAME ------");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
//        their check
        try {
            System.out.println("*********** THEIR CHECK ! HAS TO WORK! (NEED TO DO EVALUATE) **********");
            Expression e = new Xor(new And(new Var("x"), new Var("x")), new Or(new Var("y"), new Val(false)));
            System.out.println(e);
            // the result is:
            // ((x & x) ^ (y | F))
            System.out.println("------ Need to be (x ^ y) ------");
            System.out.println(e.simplify());
            // the result is:
            // (x ^ y)
            System.out.println("\n");
            Expression e1 = new Xor(new Or(new And(T, T), F), T);
            System.out.println(e1);
            System.out.println("------ Need to be False ------");
            System.out.println(e1.simplify());
            System.out.println("\n");
            Expression e2 = new Nand(new Xor(new Or(new And(T, T), F), T), T);
            System.out.println(e2);
            System.out.println("------ Need to be True ------");
            System.out.println(e2.simplify());
            System.out.println("\n");
            Expression e3 = new Nor(new Xor(new Or(new And(T, T), F), T), T);
            System.out.println(e3);
            System.out.println("------ Need to be False ------");
            System.out.println(e3.simplify());
            System.out.println("\n");
            Expression e4 = new And(new Xnor(X, X), Y);
            System.out.println(e4);
            System.out.println("------ Need to be Y ------");
            System.out.println(e4.simplify());
            System.out.println("\n");

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //testers - for the Piazza tester
        Expression expression = new And(new Var("x"), new Var("x"));
        //not suppose to throw exception
        expression.assign("x", new Val(false)).evaluate();
        expression.assign("x", new Val(true)).evaluate();
        //checks for duplicates in GetVariables
        Expression expression1 = new Nand(new Var("x"), new And(new Var("x"), new Var("y")));
        System.out.println(expression1.getVariables());
        System.out.println("------ Need to be [x, y] ------");
    }
}
