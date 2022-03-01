// ID - 318969748

/*
 * Osher Elhadad
 *
 * This file defines a Xor Class that has 2 Expression value.
 */

import java.util.Map;

/**
 * This Xor class has 2 Expression value,
 * extents the BinaryExpression abstract.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public class Xor extends BinaryExpression {

    // The default sign of xor.
    public static final String XOR = "^";

    /**
     * Xor is the constructor method.
     * it sets the 2 expression value of this Xor.
     *
     * @param expression1 is the first expression value of BinaryExpression.
     * @param expression2 is the second expression value of BinaryExpression.
     */
    public Xor(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return (new Xor(this.getFirstExpression().assign(var, expression),
                this.getSecondExpression().assign(var, expression)));
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Boolean firstVal = this.getFirstExpression().evaluate(assignment);
        Boolean secondVal = this.getSecondExpression().evaluate(assignment);
        return (firstVal != secondVal);
    }

    @Override
    protected String logicSign() {
        return XOR;
    }

    @Override
    public Expression nandify() {
        Expression firstNandify = this.getFirstExpression().nandify();
        Expression secondNandify = this.getSecondExpression().nandify();

        // x ^ y = ((x A (x A y)) A (y A (x A y))
        return (new Nand(
                new Nand(firstNandify, new Nand(firstNandify, secondNandify)),
                new Nand(secondNandify, new Nand(firstNandify, secondNandify))));
    }

    @Override
    public Expression norify() {
        Expression firstNorify = this.getFirstExpression().norify();
        Expression secondNorify = this.getSecondExpression().norify();

        // x ^ y = (((x V x) V (y V y)) V (x V y))
        return (new Nor(
                new Nor(new Nor(firstNorify, firstNorify), new Nor(secondNorify, secondNorify)),
                new Nor(firstNorify, secondNorify)));
    }

    @Override
    public Expression simplify() {

        // Simplify the 2 expressions.
        Expression firstSimplifyExpression = this.getFirstExpression().simplify();
        Expression secondSimplifyExpression = this.getSecondExpression().simplify();

        /*
         * Checks if the first simplified expression is a Val,
         * and returns x ^ 1 = ~(x), x ^ 0 = x.
         */
        if (firstSimplifyExpression.getVal() != null) {
            if (firstSimplifyExpression.getVal()) {
                return (new Not(secondSimplifyExpression)).simplify();
            }
            return secondSimplifyExpression;
        }

        /*
         * Checks if the second simplified expression is a Val,
         * and returns x ^ 1 = ~(x), x ^ 0 = x.
         */
        if (secondSimplifyExpression.getVal() != null) {
            if (secondSimplifyExpression.getVal()) {
                return (new Not(firstSimplifyExpression)).simplify();
            }
            return firstSimplifyExpression;
        }

        /*
         * Checks if the 2 simplified expression equal,
         * and returns x ^ x = 0.
         */
        if (firstSimplifyExpression.doesEqual(secondSimplifyExpression)) {
            return (new Val(false));
        }

        // Returns the 2 simplified expressions with the logic operator- xor.
        return (new Xor(firstSimplifyExpression, secondSimplifyExpression));
    }
}
