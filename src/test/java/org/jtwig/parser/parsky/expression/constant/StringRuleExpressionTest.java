package org.jtwig.parser.parsky.expression.constant;

import org.jtwig.model.expression.constant.StringConstantExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class StringRuleExpressionTest {
    @Test
    public void singleQuote() throws Exception {
        StringConstantExpression result = ParskyTestUtils.parse("'te\"st'", StringConstantExpression.class);

        assertThat(result.getConstantValue(), is("te\"st"));
    }

    @Test
    public void escapeSingleQuote() throws Exception {
        StringConstantExpression result = ParskyTestUtils.parse("'tes\\'t'", StringConstantExpression.class);

        assertThat(result.getConstantValue(), is("tes't"));
    }

    @Test
    public void doubleQuote() throws Exception {
        StringConstantExpression result = ParskyTestUtils.parse("\"test\"", StringConstantExpression.class);

        assertThat(result.getConstantValue(), is("test"));
    }
}