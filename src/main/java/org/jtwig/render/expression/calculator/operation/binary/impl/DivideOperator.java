package org.jtwig.render.expression.calculator.operation.binary.impl;

import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;

public class DivideOperator implements BinaryOperator {
    @Override
    public String symbol() {
        return "/";
    }

    @Override
    public int precedence() {
        return 5;
    }
}
