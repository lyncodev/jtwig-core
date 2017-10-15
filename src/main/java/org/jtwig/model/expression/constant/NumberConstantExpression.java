package org.jtwig.model.expression.constant;

import org.jtwig.model.position.Position;

import java.math.BigDecimal;

public class NumberConstantExpression extends ConstantExpression<BigDecimal> {
    public NumberConstantExpression(Position position, BigDecimal value) {
        super(position, value);
    }
}
