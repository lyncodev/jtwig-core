package org.jtwig.parser.parsky.expression.collections;

import org.jtwig.model.expression.collections.MapExpression;
import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.jtwig.model.expression.constant.StringConstantExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class MapRuleExpressionTest {

    @Test
    public void emptyMap() throws Exception {
        MapExpression result = ParskyTestUtils.parse("{ }", MapExpression.class);

        assertThat(result.getExpressions().isEmpty(), is(true));
    }

    @Test
    public void oneEntryMap() throws Exception {
        MapExpression result = ParskyTestUtils.parse("{ 1: 'value' }", MapExpression.class);

        assertThat(result.getExpressions().isEmpty(), is(false));
        assertThat(result.getExpressions().get("1"), instanceOf(StringConstantExpression.class));
    }

    @Test
    public void twoEntryMap() throws Exception {
        MapExpression result = ParskyTestUtils.parse("{ 1: 'value', 'a': 123 }", MapExpression.class);

        assertThat(result.getExpressions().isEmpty(), is(false));
        assertThat(result.getExpressions().get("1"), instanceOf(StringConstantExpression.class));
        assertThat(result.getExpressions().get("a"), instanceOf(NumberConstantExpression.class));
    }

    @Test
    public void recursiveMap() throws Exception {
        MapExpression result = ParskyTestUtils.parse("{ 1: 'value', 'a': {} }", MapExpression.class);

        assertThat(result.getExpressions().isEmpty(), is(false));
        assertThat(result.getExpressions().get("1"), instanceOf(StringConstantExpression.class));
        assertThat(result.getExpressions().get("a"), instanceOf(MapExpression.class));
    }
}