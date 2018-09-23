package org.jtwig.model.expression.operations;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;

public abstract class InjectableExpression extends Expression {
    protected InjectableExpression(Position position) {
        super(position);
    }

    public abstract Expression inject (Expression expression);
}
