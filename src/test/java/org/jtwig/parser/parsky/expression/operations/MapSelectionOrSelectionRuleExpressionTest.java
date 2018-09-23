package org.jtwig.parser.parsky.expression.operations;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.jtwig.model.expression.operations.MapSelectionExpression;
import org.jtwig.model.expression.operations.SelectionExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class MapSelectionOrSelectionRuleExpressionTest {
    @Test
    public void mapSelection() throws Exception {
        MapSelectionExpression result = ParskyTestUtils.parse("a[1]", MapSelectionExpression.class);

        assertThat(result.getMapExpression(), instanceOf(VariableExpression.class));
        assertThat(result.getSelectValue(), instanceOf(NumberConstantExpression.class));
    }

    @Test
    public void mapWhitespaceSelection() throws Exception {
        MapSelectionExpression result = ParskyTestUtils.parse("a [ 1 ]", MapSelectionExpression.class);

        assertThat(result.getMapExpression(), instanceOf(VariableExpression.class));
        assertThat(result.getSelectValue(), instanceOf(NumberConstantExpression.class));
    }

    @Test
    public void mapWhitespaceVariable() throws Exception {
        MapSelectionExpression result = ParskyTestUtils.parse("a [ c ]", MapSelectionExpression.class);

        assertThat(result.getMapExpression(), instanceOf(VariableExpression.class));
        assertThat(result.getSelectValue(), instanceOf(VariableExpression.class));
    }

    @Test
    public void mapSelectionVariable() throws Exception {
        MapSelectionExpression result = ParskyTestUtils.parse("var.test.field[c]", MapSelectionExpression.class);

        assertThat(result.getMapExpression(), instanceOf(SelectionExpression.class));
        assertThat(result.getSelectValue(), instanceOf(VariableExpression.class));
    }

    @Test
    public void mapSelectionOfMapSelection() throws Exception {
        Expression result = ParskyTestUtils.parse("var.test.field[c].test[1]", Expression.class);

        System.out.println(result);
    }

    public static class One {
        public Two two;
    }

    public static class Two {
        public Three[] field;
    }

    public static class Three {
        public Four[] test;
    }

    public static class Four {

    }
}
