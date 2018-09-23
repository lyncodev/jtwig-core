package org.jtwig.parser.parsky.expression.constant;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.constant.BooleanConstantExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BooleanRuleExpressionTest {
    @Test
    public void falseValue() throws Exception {
        BooleanConstantExpression result = ParskyTestUtils.parse("false", BooleanConstantExpression.class);

        assertThat(result.getConstantValue(), is(false));
    }

    @Test
    public void trueValue() throws Exception {
        BooleanConstantExpression result = ParskyTestUtils.parse("true", BooleanConstantExpression.class);

        assertThat(result.getConstantValue(), is(true));
    }
}