package org.jtwig.model.expression.operations.test;

import org.jtwig.model.expression.operations.InjectableExpression;

public class FunctionTestExpression extends TestExpression {
    private final InjectableExpression injectableExpression;

    public FunctionTestExpression(InjectableExpression injectableExpression) {
        this.injectableExpression = injectableExpression;
    }

    public InjectableExpression getInjectableExpression() {
        return injectableExpression;
    }
}
