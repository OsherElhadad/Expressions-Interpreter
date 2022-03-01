// ID - 318969748

/*
 * Osher Elhadad
 *
 * This file defines a BaseExpression that has Expressions list.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Set;

/**
 * This BaseExpression class has Expressions list value,
 * implements the Expression interface.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public abstract class BaseExpression implements Expression {

    // The BaseExpression has list of expressions.
    private List<Expression> expressions;

    // The default sign of left bracket.
    public static final String LEFT_BRACKET = "(";

    // The default sign of right bracket.
    public static final String RIGHT_BRACKET = ")";

    // The default sign of space.
    public static final String SPACE = " ";

    // The default index of the first expression.
    public static final int FIRST_EXPRESSION = 0;

    // The default index of the second expression.
    public static final int SECOND_EXPRESSION = 1;

    /**
     * BaseExpression is the constructor method.
     * it sets the expressions list of this BaseExpression.
     *
     * @param expressions is the list of expressions of the BaseExpression.
     */
    public BaseExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public List<Expression> getExpressions() {
        return this.expressions;
    }

    /**
     * setExpressions is method that sets the expressions list of this BaseExpression.
     *
     * @param expressionList list of expressions of this BaseExpression.
     */
    protected void setExpressions(List<Expression> expressionList) {
        this.expressions = expressionList;
    }

    @Override
    public Boolean evaluate() throws Exception {
        return (this.evaluate(new TreeMap<>()));
    }

    @Override
    public List<String> getVariables() {
        Set<String> set = new TreeSet<>();
        List<Expression> expressionList = this.getExpressions();

        /*
         * A loop that adds the whole variables of the whole expressions to set
         * (to prevent duplicate variables).
         */
        for (Expression expression : expressionList) {
            set.addAll(expression.getVariables());
        }
        return (new ArrayList<>(set));
    }

    @Override
    public Boolean doesEqual(Expression expression) {

        /*
         * Checks if the logical operator is the same (class),
         * and checks if the inside expressions are the same (the order is not important).
         */
        return (this.toString().equals(expression.toString()));
    }

    @Override
    public Boolean getVal() {
        return null;
    }

    @Override
    public Boolean isVar() {
        return false;
    }

    /**
     * logicSign is method that returns the logic sign of the expression.
     *
     * @return the logic sign of the expression.
     */
    protected abstract String logicSign();
}
