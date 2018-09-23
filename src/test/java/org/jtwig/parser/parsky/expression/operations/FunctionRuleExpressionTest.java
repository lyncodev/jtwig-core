package org.jtwig.parser.parsky.expression.operations;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.jtwig.model.expression.operations.FunctionExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

public class FunctionRuleExpressionTest {
    @Test
    public void oneArgument() throws Exception {
        FunctionExpression result = ParskyTestUtils.parse("function one", FunctionExpression.class);

        assertThat(result.getFunctionIdentifier(), is("function"));
        assertThat(result.getArguments(), hasSize(1));
        assertThat(result.getArguments().get(0), instanceOf(VariableExpression.class));
    }

    @Test
    public void noArgument() throws Exception {
        FunctionExpression result = ParskyTestUtils.parse("function ()", FunctionExpression.class);

        assertThat(result.getFunctionIdentifier(), is("function"));
        assertThat(result.getArguments(), hasSize(0));
    }

    @Test
    public void oneMultiArgument() throws Exception {
        FunctionExpression result = ParskyTestUtils.parse("function (test)", FunctionExpression.class);

        assertThat(result.getFunctionIdentifier(), is("function"));
        assertThat(result.getArguments(), hasSize(1));
        assertThat(result.getArguments().get(0), instanceOf(VariableExpression.class));
    }

    @Test
    public void twoMultiArgument() throws Exception {
        FunctionExpression result = ParskyTestUtils.parse("function (test, 2)", FunctionExpression.class);

        assertThat(result.getFunctionIdentifier(), is("function"));
        assertThat(result.getArguments(), hasSize(2));
        assertThat(result.getArguments().get(0), instanceOf(VariableExpression.class));
        assertThat(result.getArguments().get(1), instanceOf(NumberConstantExpression.class));
    }
}