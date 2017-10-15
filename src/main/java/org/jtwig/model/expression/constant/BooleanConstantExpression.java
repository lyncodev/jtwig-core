package org.jtwig.model.expression.constant;

import org.jtwig.model.position.Position;

public class BooleanConstantExpression extends ConstantExpression<Boolean> {
    public BooleanConstantExpression(Position position, Boolean value) {
        super(position, value);
    }
}
