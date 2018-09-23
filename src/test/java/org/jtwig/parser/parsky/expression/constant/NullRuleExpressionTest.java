package org.jtwig.parser.parsky.expression.constant;

import org.hamcrest.CoreMatchers;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.constant.NullConstantExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class NullRuleExpressionTest {

    @Test
    public void nullValue() throws Exception {
        NullConstantExpression result = ParskyTestUtils.parse("null", NullConstantExpression.class);

        assertThat(result.getConstantValue(), CoreMatchers.nullValue());
    }
}