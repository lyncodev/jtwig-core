package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.BinaryOperationExpression;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.TernaryOperationExpression;
import org.jtwig.model.expression.UnaryOperationExpression;
import org.jtwig.model.expression.constant.ConstantExpression;
import org.jtwig.parser.parboiled.AbstractParserTest;
import org.jtwig.render.expression.calculator.operation.binary.impl.SubtractOperator;
import org.jtwig.render.expression.calculator.operation.binary.impl.SumOperator;
import org.junit.Test;
import org.parboiled.support.ParsingResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class AnyExpressionParserTest extends AbstractParserTest {
    private AnyExpressionParser underTest = context.parser(AnyExpressionParser.class);

    @Test
    public void primaryExpression() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "123");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, is(instanceOf(ConstantExpression.class)));
        ConstantExpression variableExpression = (ConstantExpression) expression;
        assertThat(variableExpression.getConstantValue().toString(), equalTo("123"));
        assertThat(variableExpression.getPosition().getLine(), is(1));
        assertThat(variableExpression.getPosition().getColumn(), is(1));

    }

    @Test
    public void binaryOperationExpression() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "2 + 2");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, instanceOf(BinaryOperationExpression.class));
        BinaryOperationExpression exp = (BinaryOperationExpression) expression;
        assertThat(exp.getLeftOperand(), instanceOf(ConstantExpression.class));
        assertThat(exp.getRightOperand(), instanceOf(ConstantExpression.class));
        assertThat(exp.getBinaryOperator(), instanceOf(SumOperator.class));
    }

    @Test
    public void multiBinaryOperationExpression() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "2 * 2 - 2");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, instanceOf(BinaryOperationExpression.class));
        BinaryOperationExpression exp = (BinaryOperationExpression) expression;
        assertThat(exp.getBinaryOperator(), instanceOf(SubtractOperator.class));
    }

    @Test
    public void multiBinaryOperationExpression2() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "2 + 2 - 2");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, instanceOf(BinaryOperationExpression.class));
        BinaryOperationExpression exp = (BinaryOperationExpression) expression;
        assertThat(exp.getBinaryOperator(), instanceOf(SubtractOperator.class));
    }

    @Test
    public void Expression() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "2 + (2 - 2)");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, instanceOf(BinaryOperationExpression.class));
        BinaryOperationExpression exp = (BinaryOperationExpression) expression;
        assertThat(exp.getBinaryOperator(), instanceOf(SumOperator.class));
    }

    @Test
    public void TernaryExpression() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "(2 == 2) ? 1 : 2");

        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, instanceOf(TernaryOperationExpression.class));
        TernaryOperationExpression exp = (TernaryOperationExpression) expression;
        assertThat(exp.getFirstExpression(), instanceOf(BinaryOperationExpression.class));
        assertThat(exp.getSecondExpression(), instanceOf(ConstantExpression.class));
        assertThat(exp.getThirdExpression(), instanceOf(ConstantExpression.class));
    }

    @Test
    public void TernaryUnaryExpression() throws Exception {
        ParsingResult<Expression> result = parse(underTest.ExpressionRule(), "not(2 == 2) ? not 1 : 2");
        assertThat(result.matched, is(true));
        Expression expression = result.valueStack.pop();
        assertThat(expression, instanceOf(TernaryOperationExpression.class));
        TernaryOperationExpression exp = (TernaryOperationExpression) expression;
        assertThat(exp.getFirstExpression(), instanceOf(UnaryOperationExpression.class));
        assertThat(exp.getSecondExpression(), instanceOf(UnaryOperationExpression.class));
        assertThat(exp.getThirdExpression(), instanceOf(ConstantExpression.class));
    }
}