
/*
 * Osher Elhadad
 *
 * This file defines a Not Class that has an Expression value.
 */

import java.util.Map;

/**
 * This Not class has an Expression value,
 * extents the UnaryExpression abstract.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public class Not extends UnaryExpression {

    // The default sign of not.
    public static final String NOT = "~";

    /**
     * Not is the constructor method.
     * it sets the expression value of this Not.
     *
     * @param expression is the expression value of Not.
     */
    public Not(Expression expression) {
        super(expression);
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Not(this.getExpression().assign(var, expression));
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !this.getExpression().evaluate(assignment);
    }

    @Override
    protected String logicSign() {
        return NOT;
    }

    @Override
    public Expression nandify() {
        Expression eNandify = this.getExpression().nandify();

        // ~(x) = (x A x)
        return (new Nand(eNandify, eNandify));
    }

    @Override
    public Expression norify() {
        Expression eNorify = this.getExpression().norify();

        // ~(x) = (x V x)
        return (new Nor(eNorify, eNorify));
    }

    @Override
    public Expression simplify() {

        // Simplify the expression.
        Expression simplifyExpression = this.getExpression().simplify();

        /*
         * Checks if the expression is a val,
         * and returns ~(T) = F, ~(F) = T.
         */
        if (simplifyExpression.getVal() != null) {
            return new Val(!simplifyExpression.getVal());
        }

        // Returns the simplified expression with the logic operator- not.
        return (new Not(simplifyExpression));
    }
}
