package org.jtwig.parser.parsky.expression.collections;

import org.hamcrest.CoreMatchers;
import org.jtwig.model.expression.collections.EnumeratedListExpression;
import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class EnumerationListRuleExpressionTest {
    @Test
    public void enumerationEmptyTest() throws Exception {
        EnumeratedListExpression result = ParskyTestUtils.parse("[]", EnumeratedListExpression.class);

        assertThat(result.getExpressions().isEmpty(), is(true));
    }

    @Test
    public void enumerationOneElementTest() throws Exception {
        EnumeratedListExpression result = ParskyTestUtils.parse("[1]", EnumeratedListExpression.class);

        assertThat(result.getExpressions().isEmpty(), is(false));
        assertThat(result.getExpressions().get(0), CoreMatchers.instanceOf(NumberConstantExpression.class));
    }

    @Test
    public void enumerationTwoElementsTest() throws Exception {
        EnumeratedListExpression result = ParskyTestUtils.parse("[1, []]", EnumeratedListExpression.class);

        assertThat(result.getExpressions().isEmpty(), is(false));
        assertThat(result.getExpressions().get(0), CoreMatchers.instanceOf(NumberConstantExpression.class));
        assertThat(result.getExpressions().get(1), CoreMatchers.instanceOf(EnumeratedListExpression.class));
    }
}