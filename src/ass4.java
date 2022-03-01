import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ass4 {
    public static void main(String[] args) {
        List<Expression> expressions = new ArrayList<>();
        Var x = new Var("x");
        Var y = new Var("y");
        Var z = new Var("z");
        Map<String, Boolean> ass = new TreeMap<>();
        ass.put("y", true);
        ass.put("z", false);
        expressions.add(new And(x, y));
        expressions.add(new Or(x, y));
        expressions.add(new Xor(x, y));
        expressions.add(new Nand(x, y));
        expressions.add(new Nor(x, y));
        expressions.add(new Xnor(x, y));
        expressions.add(new Not(x));
        expressions.add(new Not(new Xor(new And(new Val(true), new Or(new Var("x"), new Var("y"))), new Var("x"))));

        for (int i = 0; i < expressions.size(); i++) {
            Expression e = expressions.get(i);
            if (i != 0) {
                System.out.println();
            }
            System.out.println(i + ":\n");
            System.out.println(e + "\n");
            System.out.println(e.nandify() + "\n");
            System.out.println(e.nandify().simplify() + "\n");
            System.out.println(e.norify() + "\n");
            System.out.println(e.norify().simplify() + "\n");
            Expression tmp = e.assign("x", new Xnor(z, new Val(false)));
            System.out.println(tmp + "\n");
            try {
                System.out.println(tmp.evaluate(ass));
            } catch (Exception ex) {
                System.out.println("exception: can't evaluate");
            }
        }
    }
}

