
/*
 * Osher Elhadad
 *
 * This file defines a And Class that has 2 Expression value.
 */

import java.util.Map;

/**
 * This And class has 2 Expression value,
 * extents the BinaryExpression abstract.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public class And extends BinaryExpression {

    // The default sign of and.
    public static final String AND = "&";

    /**
     * And is the constructor method.
     * it sets the 2 expression value of this And.
     *
     * @param expression1 is the first expression value of BinaryExpression.
     * @param expression2 is the second expression value of BinaryExpression.
     */
    public And(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return (new And(this.getFirstExpression().assign(var, expression),
                this.getSecondExpression().assign(var, expression)));
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Boolean firstVal = this.getFirstExpression().evaluate(assignment);
        Boolean secondVal = this.getSecondExpression().evaluate(assignment);
        return (firstVal && secondVal);
    }

    @Override
    protected String logicSign() {
        return AND;
    }

    @Override
    public Expression nandify() {
        Expression firstNandify = this.getFirstExpression().nandify();
        Expression secondNandify = this.getSecondExpression().nandify();

        // x & y = ((x A y) A (x A y))
        return (new Nand(new Nand(firstNandify, secondNandify),
                new Nand(firstNandify, secondNandify)));
    }

    @Override
    public Expression norify() {
        Expression firstNorify = this.getFirstExpression().norify();
        Expression secondNorify = this.getSecondExpression().norify();

        // x & y = ((x V x) V (y V y))
        return (new Nor(
                new Nor(firstNorify, firstNorify),
                new Nor(secondNorify, secondNorify)));
    }

    @Override
    public Expression simplify() {

        // Simplify the 2 expressions.
        Expression firstSimplifyExpression = this.getFirstExpression().simplify();
        Expression secondSimplifyExpression = this.getSecondExpression().simplify();

        /*
         * Checks if the first simplified expression is a Val,
         * and returns x & 1 = x, x & 0 = 0.
         */
        if (firstSimplifyExpression.getVal() != null) {
            if (firstSimplifyExpression.getVal()) {
                return secondSimplifyExpression;
            }
            return firstSimplifyExpression;
        }

        /*
         * Checks if the second simplified expression is a Val,
         * and returns x & 1 = x, x & 0 = 0.
         */
        if (secondSimplifyExpression.getVal() != null) {
            if (secondSimplifyExpression.getVal()) {
                return firstSimplifyExpression;
            }
            return secondSimplifyExpression;
        }

        /*
         * Checks if the 2 simplified expression equal,
         * and returns x & x = x.
         */
        if (firstSimplifyExpression.doesEqual(secondSimplifyExpression)) {
            return firstSimplifyExpression;
        }

        // Returns the 2 simplified expressions with the logic operator- and.
        return (new And(firstSimplifyExpression, secondSimplifyExpression));
    }
}
