package org.jtwig.parser.parsky.expression.operations;

import org.jtwig.parser.parsky.ParskyTestUtils;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.impl.NegativeUnaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.impl.NotUnaryOperator;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class UnaryOperatorRuleExpressionTest {
    @Test
    public void unaryOperator() throws Exception {
        UnaryOperator result = ParskyTestUtils.parse("not", UnaryOperator.class);

        assertThat(result, instanceOf(NotUnaryOperator.class));
    }

    @Test
    public void negative() throws Exception {
        UnaryOperator result = ParskyTestUtils.parse("-", UnaryOperator.class);

        assertThat(result, instanceOf(NegativeUnaryOperator.class));
    }
}