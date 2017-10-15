package org.jtwig.model.expression.constant;

import org.jtwig.model.position.Position;

public class NullConstantExpression extends ConstantExpression<Void> {
    public NullConstantExpression(Position position) {
        super(position, null);
    }
}
