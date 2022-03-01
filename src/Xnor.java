// ID - 318969748

/*
 * Osher Elhadad
 *
 * This file defines a Xnor Class that has 2 Expression value.
 */

import java.util.Map;

/**
 * This Xnor class has 2 Expression value,
 * extents the BinaryExpression abstract.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public class Xnor extends BinaryExpression {

    // The default sign of xnor.
    public static final String XNOR = "#";

    /**
     * Xnor is the constructor method.
     * it sets the 2 expression value of this Xnor.
     *
     * @param expression1 is the first expression value of BinaryExpression.
     * @param expression2 is the second expression value of BinaryExpression.
     */
    public Xnor(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return (new Xnor(this.getFirstExpression().assign(var, expression),
                this.getSecondExpression().assign(var, expression)));
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Boolean firstVal = this.getFirstExpression().evaluate(assignment);
        Boolean secondVal = this.getSecondExpression().evaluate(assignment);
        return (firstVal == secondVal);
    }

    @Override
    protected String logicSign() {
        return XNOR;
    }

    @Override
    public Expression nandify() {
        Expression firstNandify = this.getFirstExpression().nandify();
        Expression secondNandify = this.getSecondExpression().nandify();

        // x & y = (((x A x) A (y A y)) A (x A y))
        return (new Nand(
                new Nand(new Nand(firstNandify, firstNandify), new Nand(secondNandify, secondNandify)),
                new Nand(firstNandify, secondNandify)));
    }

    @Override
    public Expression norify() {
        Expression firstNorify = this.getFirstExpression().norify();
        Expression secondNorify = this.getSecondExpression().norify();

        // x & y = ((x V (x V y)) V (y V (x V y)))
        return (new Nor(
                new Nor(firstNorify, new Nor(firstNorify, secondNorify)),
                new Nor(secondNorify, new Nor(firstNorify, secondNorify))));
    }

    @Override
    public Expression simplify() {

        // Simplify the 2 expressions.
        Expression firstSimplifyExpression = this.getFirstExpression().simplify();
        Expression secondSimplifyExpression = this.getSecondExpression().simplify();

        /*
         * Checks if the 2 simplified expressions are Val,
         * and returns T # T = F # F = T, T # F = F # T = F.
         */
        if (firstSimplifyExpression.getVal() != null && secondSimplifyExpression.getVal() != null) {
            if (firstSimplifyExpression.getVal() == secondSimplifyExpression.getVal()) {
                return (new Val(true));
            }
            return (new Val(false));
        }

        /*
         * Checks if the 2 simplified expression equal,
         * and returns x # x = 1.
         */
        if (firstSimplifyExpression.doesEqual(secondSimplifyExpression)) {
            return (new Val(true));
        }

        // Returns the 2 simplified expressions with the logic operator- xnor.
        return (new Xnor(firstSimplifyExpression, secondSimplifyExpression));
    }
}
