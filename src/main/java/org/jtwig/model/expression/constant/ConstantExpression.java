package org.jtwig.model.expression.constant;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;

public class ConstantExpression<T> extends Expression {
    private final T value;

    public ConstantExpression(Position position, T value) {
        super(position);
        this.value = value;
    }

    public T getConstantValue() {
        return value;
    }
}
