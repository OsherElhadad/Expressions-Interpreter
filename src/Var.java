// ID - 318969748

/*
 * Osher Elhadad
 *
 * This file defines a Var that has a string variable value.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This Val class has a Var that has a string variable value,
 * implements the Expression interface.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public class Var implements Expression {

    // The Var has a string variable value.
    private String variable;

    // The default index of the first variable.
    public static final int FIRST_VARIABLE = 0;

    /**
     * Var is the constructor method.
     * it sets the string variable value of this Var.
     *
     * @param variable is the string variable value of Var.
     */
    public Var(String variable) {
        this.variable = variable;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (!assignment.containsKey(this.variable)) {
            throw new Exception("the expression contains a variable which is not in the assignment.");
        }
        return assignment.get(this.variable);
    }

    @Override
    public Boolean evaluate() throws Exception {
        throw new Exception("the expression contains a variable that has no value.");
    }

    @Override
    public Expression assign(String var, Expression expression) {
        if (var.equals(this.getVariables().get(FIRST_VARIABLE))) {
            return expression;
        }
        return this;
    }

    @Override
    public List<String> getVariables() {
        List<String> list = new ArrayList<>();
        list.add(this.variable);
        return list;
    }

    @Override
    public Expression nandify() {
        return this;
    }

    @Override
    public Expression norify() {
        return this;
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public List<Expression> getExpressions() {
        return new ArrayList<>();
    }

    @Override
    public Boolean getVal() {
        return null;
    }

    @Override
    public Boolean isVar() {
        return true;
    }

    @Override
    public Boolean doesEqual(Expression expression) {
        if (!(expression.isVar())) {
            return false;
        }
        return (expression.getVariables().get(FIRST_VARIABLE).equals(this.getVariables().get(FIRST_VARIABLE)));
    }

    @Override
    public String toString() {
        return this.variable;
    }
}
