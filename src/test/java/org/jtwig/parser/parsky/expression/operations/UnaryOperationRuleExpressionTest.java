package org.jtwig.parser.parsky.expression.operations;

import org.jtwig.model.expression.constant.BooleanConstantExpression;
import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.jtwig.model.expression.operations.UnaryOperationExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.jtwig.render.expression.calculator.operation.unary.impl.NegativeUnaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.impl.NotUnaryOperator;
import org.junit.Test;
import org.parsky.engine.ParserResult;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UnaryOperationRuleExpressionTest {
    @Test
    public void unaryOperator() throws Exception {
        UnaryOperationExpression result = ParskyTestUtils.parse("-1", UnaryOperationExpression.class);

        assertThat(result.getOperand(), instanceOf(NumberConstantExpression.class));
        assertThat(result.getOperator(), instanceOf(NegativeUnaryOperator.class));
    }

    @Test
    public void negativeOperator() throws Exception {
        UnaryOperationExpression result = ParskyTestUtils.parse("not true", UnaryOperationExpression.class);

        assertThat(result.getOperand(), instanceOf(BooleanConstantExpression.class));
        assertThat(result.getOperator(), instanceOf(NotUnaryOperator.class));
    }

    @Test
    public void noMatch() throws Exception {
        ParserResult result = ParskyTestUtils.match("nottrue", UnaryOperationExpression.class);

        assertThat(result.success(), is(false));
    }
}