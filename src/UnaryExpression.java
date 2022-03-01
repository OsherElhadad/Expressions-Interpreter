// ID - 318969748

/*
 * Osher Elhadad
 *
 * This file defines a UnaryExpression that has an Expression value.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * This UnaryExpression class has an Expression value,
 * extents the BaseExpression abstract class.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public abstract class UnaryExpression extends BaseExpression {

    /**
     * UnaryExpression is the constructor method.
     * it sets the expression value of this UnaryExpression.
     *
     * @param expression is the expression value of UnaryExpression.
     */
    public UnaryExpression(Expression expression) {
        super(null);
        List<Expression> expressionList = new ArrayList<>();
        expressionList.add(expression);
        this.setExpressions(expressionList);
    }

    /**
     * getExpression is method that returns the expression of this UnaryExpression.
     *
     * @return the expression of the UnaryExpression.
     */
    protected Expression getExpression() {
        return this.getExpressions().get(FIRST_EXPRESSION);
    }

    @Override
    public String toString() {
        return this.logicSign() + LEFT_BRACKET + this.getExpression() + RIGHT_BRACKET;
    }
}
