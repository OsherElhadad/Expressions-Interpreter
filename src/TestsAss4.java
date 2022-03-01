import java.util.Map;
import java.util.TreeMap;

/**
 * This class tests ass4.
 */
public class TestsAss4 {

    /**
     * Test for Var class.
     *
     * @throws Exception from evaluate
     */
    public void testForVar() throws Exception {
        Var v1 = new Var("x");
        Var v2 = new Var("y");
        Var v3 = new Var("");

        Map<String, Boolean> assign = new TreeMap<>();
        assign.put("x", true);
        assign.put("y", false);

        /*test evaluate*/
        assert (v1.evaluate(assign));
        assert (!v2.evaluate(assign));


        /*test getVariables*/
        assert (v1.getVariables().size() == 1);
        assert (v2.getVariables().size() == 1);
        assert (v3.getVariables().size() == 1);

        assert (v1.getVariables().get(0).equals("x"));
        assert (v2.getVariables().get(0).equals("y"));
        assert (v3.getVariables().get(0).equals(""));

        /*test toString*/
        assert (v1.toString().equals("x"));
        assert (v2.toString().equals("y"));
        assert (v3.toString().equals(""));

        /*test assign*/
        assert (v1.assign("x", new Var("z")).toString().equals("z"));
        assert (v1.assign("x", new Var("b")).toString().equals("b"));
        assert (v1.assign("z", new Var("s")).toString().equals("x"));
        assert (v3.assign("", new Var("b")).toString().equals("b"));

        /*test nandify*/
        assert (v2.nandify().toString().equals("y"));

        /*test norify*/
        assert (v2.norify().toString().equals("y"));

        /*test simplify*/
        assert (v2.simplify().toString().equals("y"));

        System.out.println("Passed tests for Var class (1/11)");

    }

    /**
     * Test for Val class.
     *
     * @throws Exception from evaluate
     */
    public void testForVal() throws Exception {
        Val va1 = new Val(true);
        Val va2 = new Val(false);

        /*test for evaluate*/
        assert (va1.evaluate());
        assert (!va2.evaluate());

        /*test for toString*/
        assert (va1.toString().equals("T"));
        assert (va2.toString().equals("F"));

        /*test for getVariable*/
        assert (va1.getVariables().size() == 0);

        /*test for assign*/
        assert (va1.assign("T", new Var("b")).toString().equals("T"));

        /*test for nandify*/
        assert (va2.nandify().toString().equals("F"));

        /*test for norify*/
        assert (!va2.norify().evaluate());

        /*test for simplify*/
        assert (va2.simplify().toString().equals("F"));

        System.out.println("Passed tests for Val class (2/11)");
    }

    /**
     * Test for Nand class.
     *
     * @throws Exception from evaluate
     */
    public void testForNand() throws Exception {
        Nand nan1 = new Nand(new Var("x"), new Var("x"));
        Nand nan2 = new Nand(new Var("x"), new Var("y"));
        Nand nan3 = new Nand(new Val(true), new Var("y"));
        Nand nan4 = new Nand(new Val(false), new Var("y"));
        Nand nan5 = new Nand(new Val(false), new Val(false));
        Nand nan6 = new Nand(new Val(false), new Val(true));
        Nand nan7 = new Nand(new Val(true), new Val(true));
        Nand nan8 = new Nand(nan1, nan1);
        Nand nan9 = new Nand(nan1, nan2);
        Nand nan10 = new Nand(nan2, nan3);
        Nand nan11 = new Nand(nan2, nan6);

        Map<String, Boolean> assign = new TreeMap<>();
        assign.put("x", true);
        assign.put("y", false);

        /*tests for evaluate*/
        assert (!nan1.evaluate(assign));
        assert (nan2.evaluate(assign));
        assert (nan3.evaluate(assign));
        assert (nan4.evaluate(assign));
        assert (nan5.evaluate(assign));
        assert (nan6.evaluate(assign));
        assert (!nan7.evaluate(assign));
        assert (nan8.evaluate(assign));
        assert (nan9.evaluate(assign));
        assert (!nan10.evaluate(assign));
        assert (!nan11.evaluate(assign));

        /*tests for toString*/
        assert (nan1.toString().equals("(x A x)"));
        assert (nan2.toString().equals("(x A y)"));
        assert (nan3.toString().equals("(T A y)"));
        assert (nan4.toString().equals("(F A y)"));
        assert (nan5.toString().equals("(F A F)"));
        assert (nan6.toString().equals("(F A T)"));
        assert (nan7.toString().equals("(T A T)"));
        assert (nan8.toString().equals("((x A x) A (x A x))"));
        assert (nan9.toString().equals("((x A x) A (x A y))"));
        assert (nan10.toString().equals("((x A y) A (T A y))"));
        assert (nan11.toString().equals("((x A y) A (F A T))"));

        /*test getVariables*/
        assert (nan1.getVariables().size() == 1);
        assert (nan2.getVariables().size() == 2);
        assert (nan11.getVariables().size() == 2);
        assert (nan11.getVariables().contains("x") && nan11.getVariables().contains("y"));

        /*tests for assign*/
        assert (nan1.assign("y", new Var("s")).toString().equals("(x A x)"));
        assert (nan1.assign("x", new Var("s")).toString().equals("(s A s)"));
        assert (nan1.toString().equals("(x A x)"));
        assert (nan2.assign("x", new Var("s")).toString().equals("(s A y)"));
        assert (nan8.assign("x", new Var("s")).toString().equals("((s A s) A (s A s))"));
        assert (nan9.assign("x", new Var("s")).toString().equals("((s A s) A (s A y))"));
        assert (nan7.assign("T", new Var("s")).toString().equals("(T A T)"));

        /*tests for nandify*/
        assert (nan1.nandify().toString().equals("(x A x)"));
        assert (nan2.nandify().toString().equals(nan2.toString()));
        assert (nan3.nandify().toString().equals(nan3.toString()));
        assert (nan4.nandify().toString().equals(nan4.toString()));
        assert (nan5.nandify().toString().equals(nan5.toString()));
        assert (nan6.nandify().toString().equals(nan6.toString()));
        assert (nan7.nandify().toString().equals(nan7.toString()));
        assert (nan8.nandify().toString().equals(nan8.toString()));
        assert (nan9.nandify().toString().equals(nan9.toString()));
        assert (nan10.nandify().toString().equals(nan10.toString()));
        assert (nan11.nandify().toString().equals(nan11.toString()));

        /*test for norify*/
        Expression nano1 = nan1.norify();
        assert (nano1.toString().equals("(((x V x) V (x V x)) V ((x V x) V (x V x)))"));

        Expression nano2 = nan2.norify();
        assert (nano2.toString().equals("(((x V x) V (y V y)) V ((x V x) V (y V y)))"));

        Expression nano3 = nan3.norify();
        assert (nano3.toString().equals("(((T V T) V (y V y)) V ((T V T) V (y V y)))"));

        Expression nano4 = nan4.norify();
        assert (nano4.toString().equals("(((F V F) V (y V y)) V ((F V F) V (y V y)))"));

        Expression nano5 = nan5.norify();
        assert (nano5.toString().equals("(((F V F) V (F V F)) V ((F V F) V (F V F)))"));

        Expression nano6 = nan6.norify();
        assert (nano6.toString().equals("(((F V F) V (T V T)) V ((F V F) V (T V T)))"));

        Expression nano7 = nan7.norify();
        assert (nano7.toString().equals("(((T V T) V (T V T)) V ((T V T) V (T V T)))"));

        Expression nano8 = nan8.norify();
        assert (nano8.toString().equals("((((((x V x) V (x V x)) V ((x V x) V"
                + " (x V x))) V (((x V x) V (x V x)) V ((x V x) V (x V x)))) V"
                + " ((((x V x) V (x V x)) V ((x V x) V (x V x))) V (((x V x) V"
                + " (x V x)) V ((x V x) V (x V x))))) V (((((x V x) V (x V x)) "
                + "V ((x V x) V (x V x))) V (((x V x) V (x V x)) V ((x V x) V "
                + "(x V x)))) V ((((x V x) V (x V x)) V ((x V x) V (x V x))) "
                + "V (((x V x) V (x V x)) V ((x V x) V (x V x))))))"));

        Expression nano9 = nan9.norify();
        assert (nano9.toString().equals("((((((x V x) V (x V x)) V"
                + " ((x V x) V (x V x))) V (((x V x) V (x V x)) V "
                + "((x V x) V (x V x)))) V ((((x V x) V (y V y)) V "
                + "((x V x) V (y V y))) V (((x V x) V (y V y)) V "
                + "((x V x) V (y V y))))) V (((((x V x) V (x V x)) V "
                + "((x V x) V (x V x))) V (((x V x) V (x V x)) V "
                + "((x V x) V (x V x)))) V ((((x V x) V (y V y)) "
                + "V ((x V x) V (y V y))) V (((x V x) V (y V y)) "
                + "V ((x V x) V (y V y))))))"));

        Expression nano10 = nan10.norify();
        assert (nano10.toString().equals("((((((x V x) V (y V y)) V "
                + "((x V x) V (y V y))) V (((x V x) V (y V y)) "
                + "V ((x V x) V (y V y)))) V ((((T V T) V (y V y)) "
                + "V ((T V T) V (y V y))) V (((T V T) V (y V y)) "
                + "V ((T V T) V (y V y))))) V (((((x V x) V "
                + "(y V y)) V ((x V x) V (y V y))) V (((x V x) "
                + "V (y V y)) V ((x V x) V (y V y)))) V ((((T V T) "
                + "V (y V y)) V ((T V T) V (y V y))) V (((T V T) "
                + "V (y V y)) V ((T V T) V (y V y))))))"));

        Expression nano11 = nan11.norify();
        assert (nano11.toString().equals("((((((x V x) V (y V y)) V ((x V x) V"
                + " (y V y))) V (((x V x) V (y V y)) V ((x V x) V (y V y)))) "
                + "V ((((F V F) V (T V T)) V ((F V F) V (T V T))) V (((F V F) "
                + "V (T V T)) V ((F V F) V (T V T))))) V (((((x V x) V (y V y)) "
                + "V ((x V x) V (y V y))) V (((x V x) V (y V y)) V ((x V x) "
                + "V (y V y)))) V ((((F V F) V (T V T)) V ((F V F) V (T V T))) "
                + "V (((F V F) V (T V T)) V ((F V F) V (T V T))))))"));

        /*test for simplify*/
        assert (nan1.simplify().toString().equals("~(x)"));
        assert (nan2.simplify().toString().equals("(x A y)"));
        assert (nan3.simplify().toString().equals("~(y)"));
        assert (nan4.simplify().toString().equals("T"));
        assert (nan5.simplify().toString().equals("T"));
        assert (nan6.simplify().toString().equals("T"));
        assert (nan7.simplify().toString().equals("F"));
        assert (nan8.simplify().toString().equals("~(~(x))"));
        assert (nan9.simplify().toString().equals("(~(x) A (x A y))"));
        assert (nan10.simplify().toString().equals("((x A y) A ~(y))"));
        assert (nan11.simplify().toString().equals("~((x A y))"));

        System.out.println("Passed test for Nand class (3/11)");
    }

    /**
     * Test for Nor class.
     *
     * @throws Exception from evaluate
     */
    public void testForNor() throws Exception {
        Nor no1 = new Nor(new Var("x"), new Var("x"));
        Nor no2 = new Nor(new Var("x"), new Var("y"));
        Nor no3 = new Nor(new Val(true), new Var("y"));
        Nor no4 = new Nor(new Val(false), new Var("y"));
        Nor no5 = new Nor(new Val(false), new Val(false));
        Nor no6 = new Nor(new Val(false), new Val(true));
        Nor no7 = new Nor(new Val(true), new Val(true));
        Nor no8 = new Nor(no1, no1);
        Nor no9 = new Nor(no1, no2);
        Nor no10 = new Nor(no2, no3);
        Nor no11 = new Nor(no2, no6);

        Map<String, Boolean> assign = new TreeMap<>();
        assign.put("x", true);
        assign.put("y", false);

        /*test for evaluate*/
        assert (!no1.evaluate(assign));
        assert (!no2.evaluate(assign));
        assert (!no3.evaluate(assign));
        assert (no4.evaluate(assign));
        assert (no5.evaluate(assign));
        assert (!no6.evaluate(assign));
        assert (!no7.evaluate(assign));
        assert (no8.evaluate(assign));
        assert (no9.evaluate(assign));
        assert (no10.evaluate(assign));
        assert (no11.evaluate(assign));


        /*test for toString*/
        assert (no1.toString().equals("(x V x)"));
        assert (no2.toString().equals("(x V y)"));
        assert (no3.toString().equals("(T V y)"));
        assert (no4.toString().equals("(F V y)"));
        assert (no5.toString().equals("(F V F)"));
        assert (no6.toString().equals("(F V T)"));
        assert (no7.toString().equals("(T V T)"));
        assert (no8.toString().equals("((x V x) V (x V x))"));
        assert (no9.toString().equals("((x V x) V (x V y))"));
        assert (no10.toString().equals("((x V y) V (T V y))"));
        assert (no11.toString().equals("((x V y) V (F V T))"));

        /*test getVariables*/
        assert (no1.getVariables().size() == 1);
        assert (no2.getVariables().size() == 2);
        assert (no3.getVariables().size() == 1);
        assert (no3.getVariables().contains("y"));
        assert (no1.getVariables().contains("x"));
        assert (no10.getVariables().contains("x")
                && no10.getVariables().contains("y"));

        /*test for assign*/
        assert (no1.assign("y", new Var("s")).toString().equals("(x V x)"));
        assert (no1.assign("x", new Var("s")).toString().equals("(s V s)"));
        assert (no1.toString().equals("(x V x)"));
        assert (no2.assign("x", new Var("s")).toString().equals("(s V y)"));
        assert (no8.assign("x", new Var("s")).toString().equals("((s V s) V (s V s))"));
        assert (no9.assign("x", new Var("s")).toString().equals("((s V s) V (s V y))"));
        assert (no7.assign("T", new Var("s")).toString().equals("(T V T)"));


        /*test for nandify*/

        Expression noa1 = no1.nandify();
        assert (noa1.toString().equals("(((x A x) A (x A x)) A ((x A x) A (x A x)))"));

        Expression noa2 = no2.nandify();
        assert (noa2.toString().equals("(((x A x) A (y A y)) A ((x A x) A (y A y)))"));

        Expression noa3 = no3.nandify();
        assert (noa3.toString().equals("(((T A T) A (y A y)) A ((T A T) A (y A y)))"));

        Expression noa4 = no4.nandify();
        assert (noa4.toString().equals("(((F A F) A (y A y)) A ((F A F) A (y A y)))"));

        Expression noa5 = no5.nandify();
        assert (noa5.toString().equals("(((F A F) A (F A F)) A ((F A F) A (F A F)))"));

        Expression noa6 = no6.nandify();
        assert (noa6.toString().equals("(((F A F) A (T A T)) A ((F A F) A (T A T)))"));

        Expression noa8 = no8.nandify();
        assert (noa8.toString().equals("((((((x A x) A (x A x)) A ((x A x) A"
                + " (x A x))) A (((x A x) A (x A x)) A ((x A x) A (x A x)))) "
                + "A ((((x A x) A (x A x)) A ((x A x) A (x A x))) A "
                + "(((x A x) A (x A x)) A ((x A x) A (x A x))))) A"
                + " (((((x A x) A (x A x)) A ((x A x) A (x A x))) "
                + "A (((x A x) A (x A x)) A ((x A x) A (x A x)))) A "
                + "((((x A x) A (x A x)) A ((x A x) A (x A x))) A (((x A x) "
                + "A (x A x)) A ((x A x) A (x A x))))))"));

        /*test for norify*/
        assert (no1.norify().toString().equals("(x V x)"));
        assert (no2.norify().toString().equals(no2.toString()));
        assert (no3.norify().toString().equals(no3.toString()));
        assert (no4.norify().toString().equals(no4.toString()));
        assert (no5.norify().toString().equals(no5.toString()));
        assert (no10.norify().toString().equals(no10.toString()));
        assert (no11.norify().toString().equals(no11.toString()));

        /*test for simplify*/
        assert (no1.simplify().toString().equals("~(x)"));
        assert (no2.simplify().toString().equals("(x V y)"));
        assert (no3.simplify().toString().equals("F"));
        assert (no4.simplify().toString().equals("~(y)"));
        assert (no5.simplify().toString().equals("T"));
        assert (no6.simplify().toString().equals("F"));
        assert (no7.simplify().toString().equals("F"));
        assert (no8.simplify().toString().equals("~(~(x))"));
        assert (no9.simplify().toString().equals("(~(x) V (x V y))"));
        assert (no10.simplify().toString().equals("~((x V y))"));
        assert (no11.simplify().toString().equals("~((x V y))"));

        System.out.println("Passed tests for Nor class (4/11)");

    }

    /**
     * Test for Not class.
     *
     * @throws Exception from evaluate
     */
    public void testForNot() throws Exception {
        Not n1 = new Not(new Var("x"));
        Not n2 = new Not(new Val(true));
        Not n3 = new Not((new Val(false)));
        Not n4 = new Not(n1);
        Not n5 = new Not(n2);

        Map<String, Boolean> assign = new TreeMap<>();
        assign.put("x", true);
        assign.put("y", false);

        /*tests for evaluate*/
        assert (!n1.evaluate(assign));
        assert (!n2.evaluate(assign));
        assert (n3.evaluate(assign));
        assert (n4.evaluate(assign));
        assert (n5.evaluate(assign));

        /*tests for toString*/
        assert (n1.toString().equals("~(x)"));
        assert (n2.toString().equals("~(T)"));
        assert (n3.toString().equals("~(F)"));
        assert (n4.toString().equals("~(~(x))"));
        assert (n5.toString().equals("~(~(T))"));

        /*test getVariables*/
        assert (n1.getVariables().size() == 1);
        assert (n2.getVariables().size() == 0);
        assert (n4.getVariables().size() == 1);
        assert (n4.getVariables().get(0).equals("x"));

        /*tests for assign*/
        assert (n1.assign("z", new Var("s")).toString().equals("~(x)"));
        assert (n1.assign("x", new Var("s")).toString().equals("~(s)"));
        assert (n1.toString().equals("~(x)"));
        assert (n1.assign("x", new Val(false)).toString().equals("~(F)"));
        assert (n2.assign("T", new Var("s")).toString().equals("~(T)"));
        assert (n4.assign("x", new Var("s")).toString().equals("~(~(s))"));
        assert (n4.assign("x", new Val(true)).toString().equals("~(~(T))"));
        assert (n4.toString().equals("~(~(x))"));

        /*tests for nandify*/
        Expression na1 = n1.nandify();
        assert (na1.toString().equals("(x A x)"));

        Expression na2 = n2.nandify();
        assert (na2.toString().equals("(T A T)"));

        Expression na3 = n3.nandify();
        assert (na3.toString().equals("(F A F)"));

        Expression na4 = n4.nandify();
        assert (na4.toString().equals("((x A x) A (x A x))"));

        Expression na5 = n5.nandify();
        assert (na5.toString().equals("((T A T) A (T A T))"));

        /*tests for norify*/
        Expression no1 = n1.norify();
        assert (no1.toString().equals("(x V x)"));

        Expression no2 = n2.norify();
        assert (no2.toString().equals("(T V T)"));

        Expression no3 = n3.norify();
        assert (no3.toString().equals("(F V F)"));

        Expression no4 = n4.norify();
        assert (no4.toString().equals("((x V x) V (x V x))"));

        Expression no5 = n5.norify();
        assert (no5.toString().equals("((T V T) V (T V T))"));

        /*tests for simplify*/
        assert (n1.simplify().toString().equals("~(x)"));
        assert (n2.simplify().toString().equals("F"));
        assert (n3.simplify().toString().equals("T"));
        assert (n4.simplify().toString().equals("~(~(x))"));
        assert (n5.simplify().toString().equals("T"));

        assert (na1.simplify().toString().equals("~(x)"));
        assert (na2.simplify().toString().equals("F"));
        assert (na3.simplify().toString().equals("T"));
        assert (na4.simplify().toString().equals("~(~(x))"));
        assert (na5.simplify().toString().equals("T"));

        assert (no1.simplify().toString().equals("~(x)"));
        assert (no2.simplify().toString().equals("F"));
        assert (no3.simplify().toString().equals("T"));
        assert (no4.simplify().toString().equals("~(~(x))"));
        assert (no5.simplify().toString().equals("T"));

        System.out.println("Passed tests for Not class (5/11)");
    }

    /**
     * Test for Or class.
     *
     * @throws Exception from evaluate
     */
    public void testForOr() throws Exception {
        Or or1 = new Or(new Var("x"), new Var("x"));
        Or or2 = new Or(new Var("x"), new Var("y"));
        Or or3 = new Or(new Val(true), new Var("y"));
        Or or4 = new Or(new Val(false), new Var("y"));
        Or or5 = new Or(new Val(false), new Val(false));
        Or or6 = new Or(new Val(false), new Val(true));
        Or or7 = new Or(new Val(true), new Val(true));
        Or or8 = new Or(or1, or1);
        Or or9 = new Or(or1, or2);
        Or or10 = new Or(or2, or3);
        Or or11 = new Or(or2, or6);

        Map<String, Boolean> assign = new TreeMap<>();
        assign.put("x", true);
        assign.put("y", false);

        /*test for evaluate*/
        assert (or1.evaluate(assign));
        assert (or2.evaluate(assign));
        assert (or3.evaluate(assign));
        assert (!or4.evaluate(assign));
        assert (!or5.evaluate(assign));
        assert (or6.evaluate(assign));
        assert (or7.evaluate(assign));
        assert (or8.evaluate(assign));
        assert (or9.evaluate(assign));
        assert (or10.evaluate(assign));
        assert (or11.evaluate(assign));

        /*test for toString*/
        assert (or1.toString().equals("(x | x)"));
        assert (or2.toString().equals("(x | y)"));
        assert (or3.toString().equals("(T | y)"));
        assert (or4.toString().equals("(F | y)"));
        assert (or5.toString().equals("(F | F)"));
        assert (or6.toString().equals("(F | T)"));
        assert (or7.toString().equals("(T | T)"));
        assert (or8.toString().equals("((x | x) | (x | x))"));
        assert (or9.toString().equals("((x | x) | (x | y))"));
        assert (or10.toString().equals("((x | y) | (T | y))"));
        assert (or11.toString().equals("((x | y) | (F | T))"));

        /*test getVariables*/
        assert (or1.getVariables().size() == 1);
        assert (or2.getVariables().size() == 2);
        assert (or3.getVariables().size() == 1);
        assert (or3.getVariables().contains("y"));
        assert (or1.getVariables().contains("x"));
        assert (or10.getVariables().contains("x")
                && or10.getVariables().contains("y"));

        /*test for assign*/
        assert (or1.assign("y", new Var("s")).toString().equals("(x | x)"));
        assert (or1.assign("x", new Var("s")).toString().equals("(s | s)"));
        assert (or1.toString().equals("(x | x)"));
        assert (or2.assign("x", new Var("s")).toString().equals("(s | y)"));
        assert (or8.assign("x", new Var("s")).toString().equals("((s | s) | (s | s))"));
        assert (or9.assign("x", new Var("s")).toString().equals("((s | s) | (s | y))"));
        assert (or7.assign("T", new Var("s")).toString().equals("(T | T)"));

        /*test for nandify*/
        assert (or1.nandify().toString().equals("((x A x) A (x A x))"));
        assert (or2.nandify().toString().equals("((x A x) A (y A y))"));
        assert (or3.nandify().toString().equals("((T A T) A (y A y))"));
        assert (or4.nandify().toString().equals("((F A F) A (y A y))"));
        assert (or5.nandify().toString().equals("((F A F) A (F A F))"));
        assert (or6.nandify().toString().equals("((F A F) A (T A T))"));
        assert (or7.nandify().toString().equals("((T A T) A (T A T))"));
        assert (or8.nandify().toString().equals("((((x A x) A (x A x)) "
                + "A ((x A x) A (x A x))) A (((x A x) A (x A x)) A ((x A x)"
                + " A (x A x))))"));
        assert (or9.nandify().toString().equals("((((x A x) A (x A x)) "
                + "A ((x A x) A (x A x))) A (((x A x) A (y A y)) A ((x A x) A"
                + " (y A y))))"));

        assert (or10.nandify().toString().equals("((((x A x) A (y A y)) A "
                + "((x A x) A (y A y))) A (((T A T) A (y A y)) A ((T A T) A"
                + " (y A y))))"));
        assert (or11.nandify().toString().equals("((((x A x) A (y A y)) A"
                + " ((x A x) A (y A y))) A (((F A F) A (T A T)) A ((F A F) A"
                + " (T A T))))"));

        /*test for norify*/
        assert (or1.norify().toString().equals("((x V x) V (x V x))"));
        assert (or2.norify().toString().equals("((x V y) V (x V y))"));
        assert (or3.norify().toString().equals("((T V y) V (T V y))"));
        assert (or4.norify().toString().equals("((F V y) V (F V y))"));
        assert (or5.norify().toString().equals("((F V F) V (F V F))"));
        assert (or6.norify().toString().equals("((F V T) V (F V T))"));
        assert (or7.norify().toString().equals("((T V T) V (T V T))"));
        assert (or8.norify().toString().equals("((((x V x) V (x V x)) V"
                + " ((x V x) V (x V x))) V (((x V x) V (x V x)) V ((x V x) V"
                + " (x V x))))"));
        assert (or9.norify().toString().equals("((((x V x) V (x V x)) V"
                + " ((x V y) V (x V y))) V (((x V x) V (x V x)) V"
                + " ((x V y) V (x V y))))"));
        assert (or10.norify().toString().equals("((((x V y) V (x V y)) V "
                + "((T V y) V (T V y))) V (((x V y) V (x V y)) "
                + "V ((T V y) V (T V y))))"));
        assert (or11.norify().toString().equals("((((x V y) V (x V y)) V"
                + " ((F V T) V (F V T))) V (((x V y) V (x V y)) V"
                + " ((F V T) V (F V T))))"));

        /*test for simplify*/
        assert (or1.simplify().toString().equals("x"));
        assert (or2.simplify().toString().equals("(x | y)"));
        assert (or3.simplify().toString().equals("T"));
        assert (or4.simplify().toString().equals("y"));
        assert (or5.simplify().toString().equals("F"));
        assert (or6.simplify().toString().equals("T"));
        assert (or7.simplify().toString().equals("T"));
        assert (or8.simplify().toString().equals("x"));
        assert (or9.simplify().toString().equals("(x | (x | y))"));
        assert (or10.simplify().toString().equals("T"));
        assert (or11.simplify().toString().equals("T"));

        System.out.println("Passed tests for Or class (6/11)");

    }

    /**
     * Test for And class.
     *
     * @throws Exception from evaluate
     */
    public void testForAnd() throws Exception {
        And a1 = new And(new Var("x"), new Var("x"));
        And a2 = new And(new Var("x"), new Var("y"));
        And a3 = new And(new Val(true), new Var("y"));
        And a4 = new And(new Val(false), new Var("y"));
        And a5 = new And(new Val(false), new Val(false));
        And a6 = new And(new Val(false), new Val(true));
        And a7 = new And(new Val(true), new Val(true));
        And a8 = new And(a1, a1);
        And a9 = new And(a1, a2);
        And a10 = new And(a2, a3);
        And a11 = new And(a2, a6);

        Map<String, Boolean> assign = new TreeMap<>();
        assign.put("x", true);
        assign.put("y", false);

        /*test for evaluate*/
        assert (a1.evaluate(assign));
        assert (!a2.evaluate(assign));
        assert (!a3.evaluate(assign));
        assert (!a4.evaluate(assign));
        assert (!a5.evaluate(assign));
        assert (!a6.evaluate(assign));
        assert (a7.evaluate(assign));
        assert (a8.evaluate(assign));
        assert (!a9.evaluate(assign));
        assert (!a10.evaluate(assign));
        assert (!a11.evaluate(assign));

        /*test for toString*/
        assert (a1.toString().equals("(x & x)"));
        assert (a2.toString().equals("(x & y)"));
        assert (a3.toString().equals("(T & y)"));
        assert (a4.toString().equals("(F & y)"));
        assert (a5.toString().equals("(F & F)"));
        assert (a6.toString().equals("(F & T)"));
        assert (a7.toString().equals("(T & T)"));
        assert (a8.toString().equals("((x & x) & (x & x))"));
        assert (a9.toString().equals("((x & x) & (x & y))"));
        assert (a10.toString().equals("((x & y) & (T & y))"));
        assert (a11.toString().equals("((x & y) & (F & T))"));

        /*test getVariables*/
        assert (a1.getVariables().size() == 1);
        assert (a2.getVariables().size() == 2);
        assert (a3.getVariables().size() == 1);
        assert (a3.getVariables().contains("y"));
        assert (a1.getVariables().contains("x"));
        assert (a10.getVariables().contains("x")
                && a10.getVariables().contains("y"));

        /*test for assign*/
        assert (a1.assign("y", new Var("s")).toString().equals("(x & x)"));
        assert (a1.assign("x", new Var("s")).toString().equals("(s & s)"));
        assert (a1.toString().equals("(x & x)"));
        assert (a2.assign("x", new Var("s")).toString().equals("(s & y)"));
        assert (a8.assign("x", new Var("s")).toString().equals("((s & s) & (s & s))"));
        assert (a9.assign("x", new Var("s")).toString().equals("((s & s) & (s & y))"));
        assert (a7.assign("T", new Var("s")).toString().equals("(T & T)"));

        /*test for nandify*/
        assert (a1.nandify().toString().equals("((x A x) A (x A x))"));
        assert (a2.nandify().toString().equals("((x A y) A (x A y))"));
        assert (a8.nandify().toString().equals("((((x A x) A (x A x)) A"
                + " ((x A x) A (x A x))) A (((x A x) A (x A x)) A"
                + " ((x A x) A (x A x))))"));
        assert (a9.nandify().toString().equals("((((x A x) A (x A x)) A"
                + " ((x A y) A (x A y))) A (((x A x) A (x A x)) A"
                + " ((x A y) A (x A y))))"));
        assert (a10.nandify().toString().equals("((((x A y) A (x A y)) A"
                + " ((T A y) A (T A y))) A (((x A y) A (x A y)) A"
                + " ((T A y) A (T A y))))"));
        assert (a11.nandify().toString().equals("((((x A y) A (x A y)) A"
                + " ((F A T) A (F A T))) A (((x A y) A (x A y)) A"
                + " ((F A T) A (F A T))))"));


        /*test for norify*/
        assert (a1.norify().toString().equals("((x V x) V (x V x))"));
        assert (a2.norify().toString().equals("((x V x) V (y V y))"));
        assert (a3.norify().toString().equals("((T V T) V (y V y))"));
        assert (a4.norify().toString().equals("((F V F) V (y V y))"));
        assert (a5.norify().toString().equals("((F V F) V (F V F))"));
        assert (a6.norify().toString().equals("((F V F) V (T V T))"));
        assert (a7.norify().toString().equals("((T V T) V (T V T))"));
        assert (a8.norify().toString().equals("((((x V x) V (x V x)) V"
                + " ((x V x) V (x V x))) V (((x V x) V (x V x)) V"
                + " ((x V x) V (x V x))))"));
        assert (a9.norify().toString().equals("((((x V x) V (x V x)) V "
                + "((x V x) V (x V x))) V (((x V x) V (y V y)) V"
                + " ((x V x) V (y V y))))"));
        assert (a10.norify().toString().equals("((((x V x) V (y V y)) V "
                + "((x V x) V (y V y))) V (((T V T) V (y V y)) V"
                + " ((T V T) V (y V y))))"));
        assert (a11.norify().toString().equals("((((x V x) V (y V y)) V "
                + "((x V x) V (y V y))) V (((F V F) V (T V T)) V ((F V F)"
                + " V (T V T))))"));



        /*test for simplify*/
        assert (a1.simplify().toString().equals("x"));
        assert (a2.simplify().toString().equals("(x & y)"));
        assert (a3.simplify().toString().equals("y"));
        assert (a4.simplify().toString().equals("F"));
        assert (a5.simplify().toString().equals("F"));
        assert (a6.simplify().toString().equals("F"));
        assert (a7.simplify().toString().equals("T"));
        assert (a8.simplify().toString().equals("x"));
        assert (a9.simplify().toString().equals("(x & (x & y))"));
        assert (a10.simplify().toString().equals("((x & y) & y)"));
        assert (a11.simplify().toString().equals("F"));

        System.out.println("Passed tests for And class (7/11)");

    }

    /**
     * Test for Xnor class.
     *
     * @throws Exception from evaluate
     */
    public void testForXnor() throws Exception {
        Xnor xn1 = new Xnor(new Var("x"), new Var("x"));
        Xnor xn2 = new Xnor(new Var("x"), new Var("y"));
        Xnor xn3 = new Xnor(new Val(true), new Var("y"));
        Xnor xn4 = new Xnor(new Val(false), new Var("y"));
        Xnor xn5 = new Xnor(new Val(false), new Val(false));
        Xnor xn6 = new Xnor(new Val(false), new Val(true));
        Xnor xn7 = new Xnor(new Val(true), new Val(true));
        Xnor xn8 = new Xnor(xn1, xn1);
        Xnor xn9 = new Xnor(xn1, xn2);
        Xnor xn10 = new Xnor(xn2, xn3);
        Xnor xn11 = new Xnor(xn2, xn6);

        Map<String, Boolean> assign = new TreeMap<>();
        assign.put("x", true);
        assign.put("y", false);

        /*test for evaluate*/
        assert (xn1.evaluate(assign));
        assert (!xn2.evaluate(assign));
        assert (!xn3.evaluate(assign));
        assert (xn4.evaluate(assign));
        assert (xn5.evaluate(assign));
        assert (!xn6.evaluate(assign));
        assert (xn7.evaluate(assign));
        assert (xn8.evaluate(assign));
        assert (!xn9.evaluate(assign));
        assert (xn10.evaluate(assign));
        assert (xn11.evaluate(assign));

        /*test for toString*/
        assert (xn1.toString().equals("(x # x)"));
        assert (xn2.toString().equals("(x # y)"));
        assert (xn3.toString().equals("(T # y)"));
        assert (xn4.toString().equals("(F # y)"));
        assert (xn5.toString().equals("(F # F)"));
        assert (xn6.toString().equals("(F # T)"));
        assert (xn7.toString().equals("(T # T)"));
        assert (xn8.toString().equals("((x # x) # (x # x))"));
        assert (xn9.toString().equals("((x # x) # (x # y))"));
        assert (xn10.toString().equals("((x # y) # (T # y))"));
        assert (xn11.toString().equals("((x # y) # (F # T))"));

        /*test getVariables*/
        assert (xn1.getVariables().size() == 1);
        assert (xn2.getVariables().size() == 2);
        assert (xn3.getVariables().size() == 1);
        assert (xn3.getVariables().contains("y"));
        assert (xn1.getVariables().contains("x"));
        assert (xn10.getVariables().contains("x")
                && xn10.getVariables().contains("y"));

        /*test for assign*/
        assert (xn1.assign("y", new Var("s")).toString().equals("(x # x)"));
        assert (xn1.assign("x", new Var("s")).toString().equals("(s # s)"));
        assert (xn1.toString().equals("(x # x)"));
        assert (xn2.assign("x", new Var("s")).toString().equals("(s # y)"));
        assert (xn8.assign("x", new Var("s")).toString().equals("((s # s) # (s # s))"));
        assert (xn9.assign("x", new Var("s")).toString().equals("((s # s) # (s # y))"));
        assert (xn7.assign("T", new Var("s")).toString().equals("(T # T)"));

        /*test for nandify*/
        assert (xn1.nandify().toString().equals("(((x A x) A (x A x)) A (x A x))"));
        assert (xn2.nandify().toString().equals("(((x A x) A (y A y)) A (x A y))"));
        assert (xn4.nandify().toString().equals("(((F A F) A (y A y)) A (F A y))"));
        assert (xn7.nandify().toString().equals("(((T A T) A (T A T)) A (T A T))"));
        assert (xn8.nandify().toString().equals("((((((x A x) A (x A x)) A (x A x)) A"
                + " (((x A x) A (x A x)) A (x A x))) A ((((x A x) A (x A x)) A (x A x)) "
                + "A (((x A x) A (x A x)) A (x A x)))) A ((((x A x) A (x A x)) A (x A x)) "
                + "A (((x A x) A (x A x)) A (x A x))))"));
        assert (xn9.nandify().toString().equals("((((((x A x) A (x A x)) A (x A x)) "
                + "A (((x A x) A (x A x)) A (x A x))) A ((((x A x) A (y A y)) A "
                + "(x A y)) A (((x A x) A (y A y)) A (x A y)))) A ((((x A x) A"
                + " (x A x)) A (x A x)) A (((x A x) A (y A y)) A (x A y))))"));
        assert (xn10.nandify().toString().equals("((((((x A x) A (y A y)) A"
                + " (x A y)) A (((x A x) A (y A y)) A (x A y))) A ((((T A T) A"
                + " (y A y)) A (T A y)) A (((T A T) A (y A y)) A (T A y)))) A"
                + " ((((x A x) A (y A y)) A (x A y)) A (((T A T) A (y A y)) A"
                + " (T A y))))"));
        assert (xn11.nandify().toString().equals("((((((x A x) A (y A y)) A"
                + " (x A y)) A (((x A x) A (y A y)) A (x A y))) A ((((F A F) A "
                + "(T A T)) A (F A T)) A (((F A F) A (T A T)) A (F A T)))) A "
                + "((((x A x) A (y A y)) A (x A y)) A (((F A F) A (T A T)) A"
                + " (F A T))))"));

        /*test for norify*/
        assert (xn1.norify().toString().equals("((x V (x V x)) V (x V (x V x)))"));
        assert (xn2.norify().toString().equals("((x V (x V y)) V (y V (x V y)))"));
        assert (xn4.norify().toString().equals("((F V (F V y)) V (y V (F V y)))"));
        assert (xn8.norify().toString().equals("((((x V (x V x)) V (x V (x V x)))"
                + " V (((x V (x V x)) V (x V (x V x))) V ((x V (x V x)) V (x V "
                + "(x V x))))) V (((x V (x V x)) V (x V (x V x))) V (((x V (x V x))"
                + " V (x V (x V x))) V ((x V (x V x)) V (x V (x V x))))))"));

        /*test for simplify*/
        assert (xn1.simplify().toString().equals("T"));
        assert (xn2.simplify().toString().equals("(x # y)"));
        assert (xn3.simplify().toString().equals("(T # y)"));
        assert (xn4.simplify().toString().equals("(F # y)"));
        assert (xn5.simplify().toString().equals("T"));
        assert (xn6.simplify().toString().equals("F"));
        assert (xn7.simplify().toString().equals("T"));
        assert (xn8.simplify().toString().equals("T"));
        assert (xn9.simplify().toString().equals("(T # (x # y))"));
        assert (xn10.simplify().toString().equals("((x # y) # (T # y))"));
        assert (xn11.simplify().toString().equals("((x # y) # F)"));

        System.out.println("Passed tests for Xnor class (8/11)");
    }

    /**
     * Test for Xor class.
     *
     * @throws Exception from evaluate
     */
    public void testForXor() throws Exception {
        Xor x1 = new Xor(new Var("x"), new Var("x"));
        Xor x2 = new Xor(new Var("x"), new Var("y"));
        Xor x3 = new Xor(new Val(true), new Var("y"));
        Xor x4 = new Xor(new Val(false), new Var("y"));
        Xor x5 = new Xor(new Val(false), new Val(false));
        Xor x6 = new Xor(new Val(false), new Val(true));
        Xor x7 = new Xor(new Val(true), new Val(true));
        Xor x8 = new Xor(x1, x1);
        Xor x9 = new Xor(x1, x2);
        Xor x10 = new Xor(x2, x3);
        Xor x11 = new Xor(x2, x6);

        Map<String, Boolean> assign = new TreeMap<>();
        assign.put("x", true);
        assign.put("y", false);

        /*test for evaluate*/
        assert (!x1.evaluate(assign));
        assert (x2.evaluate(assign));
        assert (x3.evaluate(assign));
        assert (!x4.evaluate(assign));
        assert (!x5.evaluate(assign));
        assert (x6.evaluate(assign));
        assert (!x7.evaluate(assign));
        assert (!x8.evaluate(assign));
        assert (x9.evaluate(assign));
        assert (!x10.evaluate(assign));
        assert (!x11.evaluate(assign));

        /*test for toString*/
        assert (x1.toString().equals("(x ^ x)"));
        assert (x2.toString().equals("(x ^ y)"));
        assert (x3.toString().equals("(T ^ y)"));
        assert (x4.toString().equals("(F ^ y)"));
        assert (x5.toString().equals("(F ^ F)"));
        assert (x6.toString().equals("(F ^ T)"));
        assert (x7.toString().equals("(T ^ T)"));
        assert (x8.toString().equals("((x ^ x) ^ (x ^ x))"));
        assert (x9.toString().equals("((x ^ x) ^ (x ^ y))"));
        assert (x10.toString().equals("((x ^ y) ^ (T ^ y))"));
        assert (x11.toString().equals("((x ^ y) ^ (F ^ T))"));

        /*test getVariables*/
        assert (x1.getVariables().size() == 1);
        assert (x2.getVariables().size() == 2);
        assert (x3.getVariables().size() == 1);
        assert (x3.getVariables().contains("y"));
        assert (x1.getVariables().contains("x"));
        assert (x10.getVariables().contains("x")
                && x10.getVariables().contains("y"));

        /*test for assign*/
        assert (x1.assign("y", new Var("s")).toString().equals("(x ^ x)"));
        assert (x1.assign("x", new Var("s")).toString().equals("(s ^ s)"));
        assert (x1.toString().equals("(x ^ x)"));
        assert (x2.assign("x", new Var("s")).toString().equals("(s ^ y)"));
        assert (x8.assign("x", new Var("s")).toString().equals("((s ^ s) ^ (s ^ s))"));
        assert (x9.assign("x", new Var("s")).toString().equals("((s ^ s) ^ (s ^ y))"));
        assert (x7.assign("T", new Var("s")).toString().equals("(T ^ T)"));

        /*test for nandify*/
        assert (x1.nandify().toString().equals("((x A (x A x)) A (x A (x A x)))"));
        assert (x2.nandify().toString().equals("((x A (x A y)) A (y A (x A y)))"));
        assert (x4.nandify().toString().equals("((F A (F A y)) A (y A (F A y)))"));
        assert (x9.nandify().toString().equals("((((x A (x A x)) A "
                + "(x A (x A x))) A (((x A (x A x)) A (x A (x A x))) A "
                + "((x A (x A y)) A (y A (x A y))))) A (((x A (x A y)) A"
                + " (y A (x A y))) A (((x A (x A x)) A (x A (x A x))) A"
                + " ((x A (x A y)) A (y A (x A y))))))"));

        /*test for norify*/
        assert (x1.norify().toString().equals("(((x V x) V (x V x)) V (x V x))"));
        assert (x2.norify().toString().equals("(((x V x) V (y V y)) V (x V y))"));
        assert (x4.norify().toString().equals("(((F V F) V (y V y)) V (F V y))"));
        assert (x11.norify().toString().equals("((((((x V x) V (y V y)) V"
                + " (x V y)) V (((x V x) V (y V y)) V (x V y))) V ((((F V F) "
                + "V (T V T)) V (F V T)) V (((F V F) V (T V T)) V (F V T)))) V"
                + " ((((x V x) V (y V y)) V (x V y)) V (((F V F) V (T V T)) "
                + "V (F V T))))"));


        /*test for simplify*/
        assert (x1.simplify().toString().equals("F"));
        assert (x2.simplify().toString().equals("(x ^ y)"));
        assert (x3.simplify().toString().equals("~(y)"));
        assert (x4.simplify().toString().equals("y"));
        assert (x5.simplify().toString().equals("F"));
        assert (x6.simplify().toString().equals("T"));
        assert (x7.simplify().toString().equals("F"));
        assert (x8.simplify().toString().equals("F"));
        assert (x9.simplify().toString().equals("(x ^ y)"));
        assert (x10.simplify().toString().equals("((x ^ y) ^ ~(y))"));
        assert (x11.simplify().toString().equals("~((x ^ y))"));

        System.out.println("Passed tests for Xor class (9/11)");
    }

    /**
     * Test for Expressions.
     *
     * @throws Exception from evaluate
     */
    public void testForExpressions() throws Exception {
        Map<String, Boolean> assign = new TreeMap<>();
        assign.put("x", true);
        assign.put("y", false);
        assign.put("z", true);
        assign.put("T", false);

        //****************************** test 1 *****************************//
        Expression e1 = new Not(new Not(new And(new Var("x"), new Var("x"))));

        assert (e1.evaluate(assign));
        assert (e1.toString().equals("~(~((x & x)))"));
        assert (e1.getVariables().size() == 1);
        assert (e1.getVariables().contains("x"));
        assert (e1.assign("z", new Var("s")).toString().equals("~(~((x & x)))"));
        assert (e1.assign("x", new Var("s")).toString().equals("~(~((s & s)))"));
        assert (e1.assign("x", new And(new Val(true),
                new Not(new Var("y")))).toString().equals(
                "~(~(((T & ~(y)) & (T & ~(y)))))"));
        assert (e1.nandify().toString().equals("((((x A x) A (x A x)) A"
                + " ((x A x) A (x A x))) A (((x A x) A (x A x)) A ((x A x) A"
                + " (x A x))))"));
        assert (e1.norify().toString().equals("((((x V x) V (x V x)) V"
                + " ((x V x) V (x V x))) V (((x V x) V (x V x)) V"
                + " ((x V x) V (x V x))))"));
        assert (e1.simplify().toString().equals("~(~(x))"));
        assert (e1.assign("x", new And(new Val(true),
                new Not(new Var("y")))).simplify().toString().equals("~(~(~(y)))"));

        assert (e1.assign("x", new And(new Val(true),
                new Not(new Var("y")))).nandify().simplify().toString().equals("~(~(~(~(~(~(~(y)))))))"));

        //****************************** test 2 *****************************//
        Expression e2 = new Not(new Not(new And(new Var("x"), new Var("y"))));
        assert (!e2.evaluate(assign));
        assert (e2.toString().equals("~(~((x & y)))"));
        assert (e2.getVariables().size() == 2);
        assert (e2.getVariables().contains("x"));
        assert (e2.nandify().toString().equals("((((x A y) A (x A y)) A ((x A y) "
                + "A (x A y))) A (((x A y) A (x A y)) A ((x A y) A (x A y))))"));
        assert (e2.norify().toString().equals("((((x V x) V (y V y)) V ((x V x)"
                + " V (y V y))) V (((x V x) V (y V y)) V ((x V x) V (y V y))))"));
        assert (e2.assign("z", new Xor(new Var("x"), new Var("y"))).toString().equals("~(~((x & y)))"));
        assert (e2.assign("y", new Xor(new Var("x"), new Var("y"))).toString().equals("~(~((x & (x ^ y))))"));
        assert (e2.assign("y", new Xor(new Var("x"), new Var("y"))).nandify().simplify().norify().evaluate(assign));
        assert (!e2.simplify().evaluate(assign));
        assert (e2.simplify().toString().equals("~(~((x & y)))"));

        //****************************** test 3 *****************************//
        Expression e3 = new And(new Not(new Var("x")), new Or(new Var("x"), new Var("y")));
        assert (!e3.evaluate(assign));
        assert (e3.toString().equals("(~(x) & (x | y))"));
        assert (e3.getVariables().size() == 2);
        assert (e3.nandify().toString().equals("(((x A x) A ((x A x) A "
                + "(y A y))) A ((x A x) A ((x A x) A (y A y))))"));
        assert (e3.norify().toString().equals("(((x V x) V (x V x)) V"
                + " (((x V y) V (x V y)) V ((x V y) V (x V y))))"));
        assert (e3.simplify().toString().equals("(~(x) & (x | y))"));
        assert (e3.assign("x", new Nor(new Var("z"), new Var("x"))).
                toString().equals("(~((z V x)) & ((z V x) | y))"));
        assert (e3.assign("x", new Nor(new Var("z"), new Var("x"))).
                nandify().toString().equals("((((((z A z) A (x A x)) A "
                + "((z A z) A (x A x))) A (((z A z) A (x A x)) A ((z A z) A"
                + " (x A x)))) A (((((z A z) A (x A x)) A ((z A z) A (x A x))) A "
                + "(((z A z) A (x A x)) A ((z A z) A (x A x)))) A (y A y))) A"
                + " (((((z A z) A (x A x)) A ((z A z) A (x A x))) A (((z A z) A"
                + " (x A x)) A ((z A z) A (x A x)))) A (((((z A z) A (x A x)) A"
                + " ((z A z) A (x A x))) A (((z A z) A (x A x)) A ((z A z) A "
                + "(x A x)))) A (y A y))))"));

        //****************************** test 4 *****************************//
        Expression e4 = new Or(new Xor(new Var("x"), new Var("y")),
                new Not(new And(new Var("z"), new Val(true))));
        assert (e4.evaluate(assign));
        assert (e4.toString().equals(("((x ^ y) | ~((z & T)))")));
        assert (e4.getVariables().size() == 3);
        assert (e4.nandify().toString().equals("((((x A (x A y)) A "
                + "(y A (x A y))) A ((x A (x A y)) A (y A (x A y))))"
                + " A ((((z A T) A (z A T)) A ((z A T) A (z A T))) "
                + "A (((z A T) A (z A T)) A ((z A T) A (z A T)))))"));
        assert (e4.norify().toString().equals("(((((x V x) "
                + "V (y V y)) V (x V y)) V (((z V z) V (T V T)) V"
                + " ((z V z) V (T V T)))) V ((((x V x) V (y V y)) V "
                + "(x V y)) V (((z V z) V (T V T)) V ((z V z) V (T V T)))))"));
        assert (e4.simplify().toString().equals("((x ^ y) | ~(z))"));
        assert (e4.assign("T", new Nand(new Var("x"), new Var("y"))).toString().equals("((x ^ y) | ~((z & T)))"));
        assert (e4.assign("z", new Nand(new Var("x"), new Var("y"))).toString().equals("((x ^ y) | ~(((x A y) & T)))"));
        assert (e4.assign("z", new Nand(new Var("x"), new Var("y"))).simplify().toString().equals("((x ^ y) | ~((x A y)))"));

        //****************************** test 5 *****************************//
        Expression e5 = new And(new Not(new Var("x")), new Not(new Var("x")));
        assert (!e5.evaluate(assign));
        assert (e5.toString().equals(("(~(x) & ~(x))")));
        assert (e5.getVariables().size() == 1);
        assert (e5.getVariables().contains("x"));
        assert (e5.nandify().toString().equals("(((x A x) A (x A x)) A ((x A x) A (x A x)))"));
        assert (e5.norify().toString().equals("(((x V x) V (x V x)) V ((x V x) V (x V x)))"));
        assert (e5.simplify().toString().equals("~(x)"));
        assert (e5.assign("T", new Nand(new Var("x"),
                new Var("y"))).toString().equals("(~(x) & ~(x))"));
        assert (e5.assign("z", new Nand(new Var("x"),
                new Var("y"))).toString().equals("(~(x) & ~(x))"));
        assert (e5.assign("z", new Nand(new Var("x"),
                new Var("y"))).norify().toString().equals("(((x V x) V"
                + " (x V x)) V ((x V x) V (x V x)))"));

        //****************************** test 6 *****************************//
        Expression e6 = new Or(new Not(new And(new Var("y"), new Var("y"))),
                new And(new Var("x"), new Var("x")));
        assert (e6.evaluate(assign));
        assert (e6.toString().equals(("(~((y & y)) | (x & x))")));
        assert (e6.getVariables().size() == 2);
        assert (e6.getVariables().contains("x"));
        assert (e6.nandify().toString().equals("(((((y A y) A (y A y)) A "
                + "((y A y) A (y A y))) A (((y A y) A (y A y)) A ((y A y) A"
                + " (y A y)))) A (((x A x) A (x A x)) A ((x A x) A (x A x))))"));
        assert (e6.norify().toString().equals("(((((y V y) V (y V y)) V"
                + " ((y V y) V (y V y))) V ((x V x) V (x V x))) V"
                + " ((((y V y) V (y V y)) V ((y V y) V (y V y))) V"
                + " ((x V x) V (x V x))))"));
        assert (e6.simplify().toString().equals("(~(y) | x)"));
        assert (e6.assign("T", new Nand(new Var("x"),
                new Var("y"))).toString().equals("(~((y & y)) | (x & x))"));
        assert (e6.assign("z", new Nand(new Var("x"),
                new Var("y"))).toString().equals("(~((y & y)) | (x & x))"));
        assert (e6.assign("z", new Nand(new Var("x"),
                new Var("y"))).norify().simplify().toString().
                equals("~((~(~(~(y))) V ~(~(x))))"));

        //****************************** test 7 *****************************//
        Expression e7 = new Not(new Xor(new Xnor(new Var("x"), new Var("y")),
                new Nor(new Var("z"), new Val(true))));

        assert (e7.evaluate(assign));
        assert (e7.toString().equals(("~(((x # y) ^ (z V T)))")));
        assert (e7.getVariables().size() == 3);
        assert (e7.getVariables().contains("x"));
        assert (e7.nandify().toString().equals("((((((x A x) A (y A y)) A"
                + " (x A y)) A ((((x A x) A (y A y)) A (x A y)) A (((z A z) "
                + "A (T A T)) A ((z A z) A (T A T))))) A ((((z A z) A (T A T)) "
                + "A ((z A z) A (T A T))) A ((((x A x) A (y A y)) A (x A y)) "
                + "A (((z A z) A (T A T)) A ((z A z) A (T A T)))))) "
                + "A (((((x A x) A (y A y)) A (x A y)) A ((((x A x) "
                + "A (y A y)) A (x A y)) A (((z A z) A (T A T)) A ((z A z) A"
                + " (T A T))))) A ((((z A z) A (T A T)) A ((z A z) A (T A T))) "
                + "A ((((x A x) A (y A y)) A (x A y)) A (((z A z) A (T A T)) "
                + "A ((z A z) A (T A T)))))))"));
        assert (e7.norify().toString().equals("((((((x V (x V y)) V"
                + " (y V (x V y))) V ((x V (x V y)) V (y V (x V y)))) "
                + "V ((z V T) V (z V T))) V (((x V (x V y)) V (y V "
                + "(x V y))) V (z V T))) V (((((x V (x V y)) V (y V "
                + "(x V y))) V ((x V (x V y)) V (y V (x V y)))) V "
                + "((z V T) V (z V T))) V (((x V (x V y)) V (y V "
                + "(x V y))) V (z V T))))"));
        assert (e7.simplify().toString().equals("~((x # y))"));
        assert (e7.assign("T", new Nand(new Var("x"),
                new Var("y"))).toString().equals("~(((x # y) ^ (z V T)))"));
        assert (e7.assign("y", new Nand(new Var("x"),
                new Var("y"))).toString().equals("~(((x # (x A y)) ^"
                + " (z V T)))"));

        //****************************** test 8 *****************************//
        Expression e8 = new Not(new Not(new Not(new And(new Var("x"), new Var("x")))));
        assert (!e8.evaluate(assign));
        assert (e8.toString().equals(("~(~(~((x & x))))")));
        assert (e8.getVariables().size() == 1);
        assert (e8.getVariables().contains("x"));
        assert (e8.nandify().toString().equals("(((((x A x) A"
                + " (x A x)) A ((x A x) A (x A x))) A (((x A x) A"
                + " (x A x)) A ((x A x) A (x A x)))) A ((((x A x) "
                + "A (x A x)) A ((x A x) A (x A x))) A (((x A x) A "
                + "(x A x)) A ((x A x) A (x A x)))))"));
        assert (e8.norify().toString().equals("(((((x V x) V"
                + " (x V x)) V ((x V x) V (x V x))) V (((x V x) "
                + "V (x V x)) V ((x V x) V (x V x)))) V ((((x V x) "
                + "V (x V x)) V ((x V x) V (x V x))) V (((x V x) "
                + "V (x V x)) V ((x V x) V (x V x)))))"));
        assert (e8.simplify().toString().equals("~(~(~(x)))"));
        assert (e8.assign("T", new Nand(new Var("x"),
                new Var("y"))).toString().equals("~(~(~((x & x))))"));
        assert (e8.assign("y", new Nand(new Var("x"),
                new Var("y"))).toString().equals("~(~(~((x & x))))"));

        //****************************** test 9 *****************************//
        Expression e9 = new Xnor(new Xor(new Nand(new Var("z"), new Var("z")),
                new Nor(new Var("z"), new Var("z"))), new Not(new Var("z")));
        assert (e9.evaluate(assign));
        assert (e9.toString().equals(("(((z A z) ^ (z V z)) # ~(z))")));
        assert (e9.getVariables().size() == 1);
        assert (e9.getVariables().contains("z"));
        assert (e9.nandify().toString().equals("((((((z A z) A"
                + " ((z A z) A (((z A z) A (z A z)) A ((z A z) A "
                + "(z A z))))) A ((((z A z) A (z A z)) A ((z A z) A "
                + "(z A z))) A ((z A z) A (((z A z) A (z A z)) A"
                + " ((z A z) A (z A z)))))) A (((z A z) A ((z A z) "
                + "A (((z A z) A (z A z)) A ((z A z) A (z A z))))) A"
                + " ((((z A z) A (z A z)) A ((z A z) A (z A z))) A "
                + "((z A z) A (((z A z) A (z A z)) A ((z A z) A (z A z))))))) A"
                + " ((z A z) A (z A z))) A ((((z A z) A ((z A z) A (((z A z) A "
                + "(z A z)) A ((z A z) A (z A z))))) A ((((z A z) A (z A z)) A "
                + "((z A z) A (z A z))) A ((z A z) A (((z A z) A (z A z)) A"
                + " ((z A z) A (z A z)))))) A (z A z)))"));
        assert (e9.norify().toString().equals("((((((((z V z) V"
                + " (z V z)) V ((z V z) V (z V z))) V (((z V z) V"
                + " (z V z)) V ((z V z) V (z V z)))) V ((z V z) V"
                + " (z V z))) V ((((z V z) V (z V z)) V ((z V z) V"
                + " (z V z))) V (z V z))) V (((((((z V z) V (z V z)) "
                + "V ((z V z) V (z V z))) V (((z V z) V (z V z)) V"
                + " ((z V z) V (z V z)))) V ((z V z) V (z V z))) V"
                + " ((((z V z) V (z V z)) V ((z V z) V (z V z))) V"
                + " (z V z))) V (z V z))) V ((z V z) V (((((((z V z) V"
                + " (z V z)) V ((z V z) V (z V z))) V (((z V z) V (z V z)) "
                + "V ((z V z) V (z V z)))) V ((z V z) V (z V z))) V"
                + " ((((z V z) V (z V z)) V ((z V z) V (z V z))) V"
                + " (z V z))) V (z V z))))"));
        assert (e9.simplify().toString().equals("(F # ~(z))"));
        assert (e9.assign("T", new Nand(new Var("x"),
                new Var("y"))).toString().equals("(((z A z) ^ (z V z)) # ~(z))"));
        assert (e9.assign("y", new Nand(new Var("x"),
                new Var("y"))).toString().equals("(((z A z) ^ (z V z)) # ~(z))"));

        //****************************** test 10 *****************************//
        Expression e10 = new Nand(new Xnor(new Val(false), new Val(false)),
                new Xnor(new Val(true), new Val(true)));
        assert (!e10.evaluate(assign));
        assert (e10.toString().equals(("((F # F) A (T # T))")));
        assert (e10.getVariables().size() == 0);
        assert (e10.nandify().toString().equals("((((F A F) A (F A F)) A (F A F)) A (((T A T) A (T A T)) A (T A T)))"));
        assert (e10.norify().toString().equals("(((((F V (F V F)) V"
                + " (F V (F V F))) V ((F V (F V F)) V (F V (F V F)))) V "
                + "(((T V (T V T)) V (T V (T V T))) V ((T V (T V T)) V "
                + "(T V (T V T))))) V ((((F V (F V F)) V (F V (F V F))) V"
                + " ((F V (F V F)) V (F V (F V F)))) V (((T V (T V T)) V "
                + "(T V (T V T))) V ((T V (T V T)) V (T V (T V T))))))"));
        assert (e10.simplify().toString().equals("F"));
        assert (e10.assign("T", new Nand(new Var("x"),
                new Var("y"))).toString().equals("((F # F) A (T # T))"));

        //****************************** test 11 *****************************//
        Expression e11 = new Nor(new Xor(new Var("x"), new Var("y")),
                new Xor(new Var("x"), new Var("z")));
        assert (!e11.evaluate(assign));
        assert (e11.toString().equals(("((x ^ y) V (x ^ z))")));
        assert (e11.getVariables().size() == 3);
        assert (e11.getVariables().contains("x"));
        assert (e11.nandify().toString().equals("(((((x A (x A y)) A "
                + "(y A (x A y))) A ((x A (x A y)) A (y A (x A y)))) A "
                + "(((x A (x A z)) A (z A (x A z))) A ((x A (x A z)) A "
                + "(z A (x A z))))) A ((((x A (x A y)) A (y A (x A y)))"
                + " A ((x A (x A y)) A (y A (x A y)))) A (((x A (x A z)) "
                + "A (z A (x A z))) A ((x A (x A z)) A (z A (x A z))))))"));
        assert (e11.norify().toString().equals("((((x V x) V (y V y)) V "
                + "(x V y)) V (((x V x) V (z V z)) V (x V z)))"));
        assert (e11.simplify().toString().equals("((x ^ y) V (x ^ z))"));
        assert (e11.assign("T", new Nand(new Var("x"),
                new Var("y"))).toString().equals("((x ^ y) V (x ^ z))"));
        assert (e11.assign("y", new Nand(new Var("x"),
                new Var("y"))).toString().equals("((x ^ (x A y)) V (x ^ z))"));

        //****************************** test 12 *****************************//
        Expression e12 = new Xor(new Not(new Nand(new Var("x"), new Var("z"))),
                new Not(new Nand(new Var("x"), new Var("z"))));
        assert (!e12.evaluate(assign));
        assert (e12.toString().equals(("(~((x A z)) ^ ~((x A z)))")));
        assert (e12.getVariables().size() == 2);
        assert (e12.getVariables().contains("x"));
        assert (e12.nandify().toString().equals("((((x A z) A (x A z)) A"
                + " (((x A z) A (x A z)) A ((x A z) A (x A z)))) A (((x A z) "
                + "A (x A z)) A (((x A z) A (x A z)) A ((x A z) A (x A z)))))"));
        assert (e12.norify().toString().equals("(((((((x V x) V (z V z)) V "
                + "((x V x) V (z V z))) V (((x V x) V (z V z)) V ((x V x) V"
                + " (z V z)))) V ((((x V x) V (z V z)) V ((x V x) V (z V z))) "
                + "V (((x V x) V (z V z)) V ((x V x) V (z V z))))) V (((((x V x) "
                + "V (z V z)) V ((x V x) V (z V z))) V (((x V x) V (z V z)) V "
                + "((x V x) V (z V z)))) V ((((x V x) V (z V z)) V ((x V x) V "
                + "(z V z))) V (((x V x) V (z V z)) V ((x V x) V (z V z)))))) "
                + "V (((((x V x) V (z V z)) V ((x V x) V (z V z))) V (((x V x) "
                + "V (z V z)) V ((x V x) V (z V z)))) V ((((x V x) V (z V z)) V"
                + " ((x V x) V (z V z))) V (((x V x) V (z V z)) V ((x V x) V "
                + "(z V z))))))"));
        assert (e12.simplify().toString().equals("F"));
        assert (e12.assign("T", new Nand(new Var("x"),
                new Var("y"))).toString().equals("(~((x A z)) ^ ~((x A z)))"));
        assert (e12.assign("y", new Nand(new Var("x"),
                new Var("y"))).toString().equals("(~((x A z)) ^ ~((x A z)))"));

        //****************************** test 13 *****************************//
        Expression e13 = new And(new Var("T"), new Var("T"));
        assert (!e13.evaluate(assign));
        assert (e13.toString().equals(("(T & T)")));
        assert (e13.getVariables().size() == 1);
        assert (e13.getVariables().contains("T"));
        assert (e13.nandify().toString().equals("((T A T) A (T A T))"));
        assert (e13.norify().toString().equals("((T V T) V (T V T))"));
        assert (e13.simplify().toString().equals("T"));
        assert (e13.assign("T", new Nand(new Var("x"),
                new Var("y"))).toString().equals("((x A y) & (x A y))"));
        assert (e13.assign("y", new Nand(new Var("x"),
                new Var("y"))).toString().equals("(T & T)"));

        System.out.println("Passed tests for expressions (10/11)");
    }

    /**
     * This is a method to test functionality of the exceptions.
     */
    public void testIfExceptionWorks() {
        Map<String, Boolean> assign = new TreeMap<>();
        assign.put("x", true);
        assign.put("y", false);
        Var v = new Var("");

        Expression e12 = new Xor(new Not(new Nand(new Var("x"), new Var("z"))),
                new Not(new Nand(new Var("x"), new Var("z"))));
        try {
            System.out.println(e12.evaluate());
        } catch (Exception ex) {
            System.out.println("passed test for exception (1/3)");
        }
        try {
            System.out.println(e12.evaluate(assign));
        } catch (Exception ex) {
            System.out.println("passed test for exception (2/3)");
        }
        try {
            System.out.println(v.evaluate());
        } catch (Exception ex) {
            System.out.println("passed test for exception (2/3)");
        }
        System.out.println("Passed tests for exceptions. (11/11)");
    }


    /**
     * This is the main method.
     *
     * @param args nothing
     * @throws Exception from evaluate method
     */
    public static void main(String[] args) throws Exception {

        TestsAss4 test = new TestsAss4();
        test.testForVar();
        test.testForVal();
        test.testForNand();
        test.testForNor();
        test.testForNot();
        test.testForOr();
        test.testForAnd();
        test.testForXnor();
        test.testForXor();
        test.testForExpressions();
        test.testIfExceptionWorks();

        System.out.println("Passed all tests!!");


    }

}
