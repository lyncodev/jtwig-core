package org.jtwig.parser.parsky.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.collections.ComprehensionListExpression;
import org.jtwig.model.expression.collections.EnumeratedListExpression;
import org.jtwig.model.expression.collections.MapExpression;
import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.jtwig.model.expression.operations.*;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.junit.Test;
import org.parsky.ParskyException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.jtwig.parser.parsky.ParskyTestUtils.parse;
import static org.junit.Assert.assertThat;

public class ExpressionSequenceMatcherFactoryTest {
    @Test
    public void expression() throws Exception {
        assertThat(expression("a"), instanceOf(VariableExpression.class));
        assertThat(expression("abc"), instanceOf(VariableExpression.class));
        assertThat(expression("1"), instanceOf(NumberConstantExpression.class));
        assertThat(expression("0..10"), instanceOf(ComprehensionListExpression.class));
        assertThat(expression("{}"), instanceOf(MapExpression.class));
        assertThat(expression("{ one: 'two' }"), instanceOf(MapExpression.class));
        assertThat(expression("function (1,2)"), instanceOf(FunctionExpression.class));
        assertThat(expression("[a]"), instanceOf(EnumeratedListExpression.class));
        assertThat(expression("0..10"), instanceOf(ComprehensionListExpression.class));
        assertThat(expression("var.call().property.anotherCall(var2)"), instanceOf(SelectionExpression.class));
        assertThat(expression("-1"), instanceOf(UnaryOperationExpression.class));
        assertThat(expression("1 * 2"), instanceOf(BinaryOperationExpression.class));
        assertThat(expression("a[b]"), instanceOf(MapSelectionExpression.class));
        assertThat(expression("1 * 2 ? 1 : 2"), instanceOf(TernaryOperationExpression.class));
        assertThat(expression("1 is defined"), instanceOf(TestOperationExpression.class));
        assertThat(expression("function 1"), instanceOf(FunctionExpression.class));
    }

    private Expression expression(String input) throws ParskyException {
        try {
            return parse(input, Expression.class);
        } catch (Throwable e) {
            throw e;
        }
    }
}