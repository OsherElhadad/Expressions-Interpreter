
/*
 * Osher Elhadad
 *
 * This file defines a Val that has a boolean value.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This Val class has a Val that has a boolean value,
 * implements the Expression interface.
 *
 * @version 1.00 5 May 2021
 * @author Osher Elhadad
 */
public class Val implements Expression {

    // The Val has a boolean value.
    private Boolean value;

    /**
     * Val is the constructor method.
     * it sets the boolean value of this Val.
     *
     * @param value is the boolean value of Val.
     */
    public Val(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.value;
    }

    @Override
    public Boolean evaluate() throws Exception {
        return this.value;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return this;
    }

    @Override
    public List<String> getVariables() {
        return (new ArrayList<>());
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
        return this.value;
    }

    @Override
    public Boolean isVar() {
        return false;
    }

    @Override
    public Boolean doesEqual(Expression expression) {
        return (expression.getVal() == this.getVal());
    }

    @Override
    public String toString() {
        if (this.value) {
            return "T";
        }
        return "F";
    }
}
