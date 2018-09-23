package org.jtwig.parser.parsky.expression.collections;

import org.jtwig.model.expression.collections.ComprehensionListExpression;
import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.jtwig.model.expression.constant.StringConstantExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ComprehensionListRuleExpressionTest {

    @Test
    public void list() throws Exception {
        ComprehensionListExpression result = ParskyTestUtils.parse("1..10", ComprehensionListExpression.class);

        assertThat(result.getStart(), instanceOf(NumberConstantExpression.class));
        assertThat(result.getEnd(), instanceOf(NumberConstantExpression.class));
    }

    @Test
    public void listChars() throws Exception {
        ComprehensionListExpression result = ParskyTestUtils.parse("'a'..'z'", ComprehensionListExpression.class);

        assertThat(result.getStart(), instanceOf(StringConstantExpression.class));
        assertThat(result.getEnd(), instanceOf(StringConstantExpression.class));
    }
}