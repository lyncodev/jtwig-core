package org.jtwig.model.expression.constant;

import org.jtwig.model.position.Position;

public class StringConstantExpression extends ConstantExpression<String> {
    public StringConstantExpression(Position position, String value) {
        super(position, value);
    }
}
