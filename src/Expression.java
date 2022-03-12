
/*
 * Osher Elhadad
 *
 * This file defines a Expression interface.
 */

import java.util.Map;
import java.util.List;

/**
 * This Expression interface has methods we do on expressions.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public interface Expression {

    /**
     * evaluate is a method from the Expression interface,
     * it evaluates the expression using the variable values provided in the assignment.
     *
     * @param assignment is a map that has the values of variables in the expression.
     * @return the result of the expression.
     * @throws Exception if the expression contains a variable which is not in the assignment.
     */
    Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    /**
     * evaluate is a method from the Expression interface,
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     *
     * @return the result of the expression.
     * @throws Exception if the expression contains a variable.
     */
    Boolean evaluate() throws Exception;

    /**
     * getVariables is a method from the Expression interface,
     * it returns a list of the variables in the expression.
     *
     * @return a list of the variables in the expression.
     */
    List<String> getVariables();

    /**
     * toString is a method from the Expression interface,
     * it returns a nice string representation of the expression.
     *
     * @return a nice string representation of the expression.
     */
    String toString();

    /**
     * assign is a method from the Expression interface,
     * it returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the current expression).
     *
     * @param var the variable we replace with the new expression.
     * @param expression the expression that we replace with the var instances.
     * @return a new expression in which all occurrences of the variable
     * var are replaced with the provided expression.
     */
    Expression assign(String var, Expression expression);

    /**
     * nandify is a method from the Expression interface,
     * it returns the expression tree resulting from converting
     * all the operations to the logical Nand operation.
     *
     * @return the expression tree resulting from converting
     *         all the operations to the logical Nand operation.
     */
    Expression nandify();

    /**
     * norify is a method from the Expression interface,
     * it returns the expression tree resulting from converting
     * all the operations to the logical Nor operation.
     *
     * @return the expression tree resulting from converting
     *         all the operations to the logical Nor operation.
     */
    Expression norify();

    /**
     * simplify is a method from the Expression interface,
     * it returns a simplified version of the current expression.
     *
     * @return a simplified version of the current expression.
     */
    Expression simplify();

    /**
     * getExpressions is method that returns the expressions list of this BaseExpression.
     *
     * @return the list of expressions of this BaseExpression.
     */
    List<Expression> getExpressions();

    /**
     * getVal is a method from the Expression interface,
     * it returns the boolean value if this expression is a val, and null else.
     *
     * @return the boolean value if this expression is a val, and null else.
     */
    Boolean getVal();

    /**
     * isVar is a method from the Expression interface,
     * it returns true if this expression is a var, and false else.
     *
     * @return true if this expression is a var, and false else.
     */
    Boolean isVar();

    /**
     * doesEqual is a method from the Expression interface,
     * it returns true if this expression equals to the other, and false else.
     *
     * @param expression the other expression we compare.
     * @return true if this expression equals to the other, and false else.
     */
    Boolean doesEqual(Expression expression);
}
