// ID - 318969748

/*
 * Osher Elhadad
 *
 * This file defines a Nor Class that has 2 Expression value.
 */

import java.util.Map;

/**
 * This Nor class has 2 Expression value,
 * extents the BinaryExpression abstract.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public class Nor extends BinaryExpression {

    // The default sign of nor.
    public static final String NOR = "V";

    /**
     * Nor is the constructor method.
     * it sets the 2 expression value of this Nor.
     *
     * @param expression1 is the first expression value of BinaryExpression.
     * @param expression2 is the second expression value of BinaryExpression.
     */
    public Nor(Expression expression1, Expression expression2) {
        super(expression1, expression2);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return (new Nor(this.getFirstExpression().assign(var, expression),
                this.getSecondExpression().assign(var, expression)));
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        Boolean firstVal = this.getFirstExpression().evaluate(assignment);
        Boolean secondVal = this.getSecondExpression().evaluate(assignment);
        return !(firstVal || secondVal);
    }

    @Override
    protected String logicSign() {
        return NOR;
    }

    @Override
    public Expression nandify() {
        Expression firstNandify = this.getFirstExpression().nandify();
        Expression secondNandify = this.getSecondExpression().nandify();

        // x V y = (((x A x) A (y A y)) A ((x A x) A (y A y)))
        return (new Nand(
                new Nand(
                        new Nand(firstNandify, firstNandify),
                        new Nand(secondNandify, secondNandify)),
                new Nand(
                        new Nand(firstNandify, firstNandify),
                        new Nand(secondNandify, secondNandify))));
    }

    @Override
    public Expression norify() {
        return (new Nor(this.getFirstExpression().norify(), this.getSecondExpression().norify()));
    }

    @Override
    public Expression simplify() {

        // Simplify the 2 expressions.
        Expression firstSimplifyExpression = this.getFirstExpression().simplify();
        Expression secondSimplifyExpression = this.getSecondExpression().simplify();

        /*
         * Checks if the first simplified expression is a Val,
         * and returns x ↓ 1 = 0, x ↓ 0 = ~(x).
         */
        if (firstSimplifyExpression.getVal() != null) {
            if (firstSimplifyExpression.getVal()) {
                return (new Val(false));
            }
            return (new Not(secondSimplifyExpression)).simplify();
        }

        /*
         * Checks if the second simplified expression is a Val,
         * and returns x ↓ 1 = 0, x ↓ 0 = ~(x).
         */
        if (secondSimplifyExpression.getVal() != null) {
            if (secondSimplifyExpression.getVal()) {
                return (new Val(false));
            }
            return (new Not(firstSimplifyExpression)).simplify();
        }

        /*
         * Checks if the 2 simplified expression equal,
         * and returns x ↓ x = ~(x).
         */
        if (firstSimplifyExpression.doesEqual(secondSimplifyExpression)) {
            return (new Not(firstSimplifyExpression)).simplify();
        }

        // Returns the 2 simplified expressions with the logic operator- nor.
        return (new Nor(firstSimplifyExpression, secondSimplifyExpression));
    }
}
