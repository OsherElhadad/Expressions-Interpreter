
/*
 * Osher Elhadad
 *
 * This file defines a Nand Class that has 2 Expression value.
 */

import java.util.Map;

/**
 * This Nand class has 2 Expression value,
 * extents the BinaryExpression abstract.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public class Nand extends BinaryExpression {

    // The default sign of nand.
    public static final String NAND = "A";

    /**
     * Nand is the constructor method.
     * it sets the 2 expression value of this Nand.
     *
     * @param expression1 is the first expression value of BinaryExpression.
     * @param expression2 is the second expression value of BinaryExpression.
     */
    public Nand(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return (new Nand(this.getFirstExpression().assign(var, expression),
                this.getSecondExpression().assign(var, expression)));
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Boolean firstVal = this.getFirstExpression().evaluate(assignment);
        Boolean secondVal = this.getSecondExpression().evaluate(assignment);
        return !(firstVal && secondVal);
    }

    @Override
    protected String logicSign() {
        return NAND;
    }

    @Override
    public Expression nandify() {
        return (new Nand(this.getFirstExpression().nandify(), this.getSecondExpression().nandify()));
    }

    @Override
    public Expression norify() {
        Expression firstNorify = this.getFirstExpression().norify();
        Expression secondNorify = this.getSecondExpression().norify();

        // x A y = (((x V x) V (y V y)) V ((x V x) V (y V y)))
        return (new Nor(
                new Nor(
                        new Nor(firstNorify, firstNorify),
                        new Nor(secondNorify, secondNorify)),
                new Nor(
                        new Nor(firstNorify, firstNorify),
                        new Nor(secondNorify, secondNorify))));
    }

    @Override
    public Expression simplify() {

        // Simplify the 2 expressions.
        Expression firstSimplifyExpression = this.getFirstExpression().simplify();
        Expression secondSimplifyExpression = this.getSecondExpression().simplify();

        /*
         * Checks if the first simplified expression is a Val,
         * and returns x ↑ 1 = ~(x), x ↑ 0 = 1.
         */
        if (firstSimplifyExpression.getVal() != null) {
            if (firstSimplifyExpression.getVal()) {
                return (new Not(secondSimplifyExpression)).simplify();
            }
            return (new Val(true));
        }

        /*
         * Checks if the second simplified expression is a Val,
         * and returns x ↑ 1 = ~(x), x ↑ 0 = 1.
         */
        if (secondSimplifyExpression.getVal() != null) {
            if (secondSimplifyExpression.getVal()) {
                return (new Not(firstSimplifyExpression)).simplify();
            }
            return (new Val(true));
        }

        /*
         * Checks if the 2 simplified expression equal,
         * and returns x ↑ x = ~(x).
         */
        if (firstSimplifyExpression.doesEqual(secondSimplifyExpression)) {
            return (new Not(firstSimplifyExpression)).simplify();
        }

        // Returns the 2 simplified expressions with the logic operator- nand.
        return (new Nand(firstSimplifyExpression, secondSimplifyExpression));
    }
}
