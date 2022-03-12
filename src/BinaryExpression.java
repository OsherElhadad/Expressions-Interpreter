
/*
 * Osher Elhadad
 *
 * This file defines a BinaryExpression that has 2 Expression value.
 */

import java.util.List;
import java.util.ArrayList;

/**
 * This BinaryExpression class has 2 Expression value,
 * extents the BaseExpression abstract class.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public abstract class BinaryExpression extends BaseExpression {

    /**
     * BinaryExpression is the constructor method.
     * it sets the expression values of this BinaryExpression.
     *
     * @param expression1 is the first expression value of BinaryExpression.
     * @param expression2 is the second expression value of BinaryExpression.
     */
    public BinaryExpression(Expression expression1, Expression expression2) {
        super(null);
        List<Expression> expressionList = new ArrayList<>();
        expressionList.add(expression1);
        expressionList.add(expression2);
        this.setExpressions(expressionList);
    }

    /**
     * getFirstExpression is method that returns the first expression of this BinaryExpression.
     *
     * @return the first expression of the BinaryExpression.
     */
    protected Expression getFirstExpression() {
        return this.getExpressions().get(FIRST_EXPRESSION);
    }

    /**
     * getSecondExpression is method that returns the second expression of this BinaryExpression.
     *
     * @return the second expression of the BinaryExpression.
     */
    protected Expression getSecondExpression() {
        return this.getExpressions().get(SECOND_EXPRESSION);
    }

    @Override
    public String toString() {
        return LEFT_BRACKET + this.getFirstExpression().toString() + SPACE
                + this.logicSign() + SPACE + this.getSecondExpression().toString() + RIGHT_BRACKET;
    }
}
