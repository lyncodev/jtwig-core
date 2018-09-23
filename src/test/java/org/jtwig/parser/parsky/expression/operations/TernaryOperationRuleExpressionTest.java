package org.jtwig.parser.parsky.expression.operations;

import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.jtwig.model.expression.operations.TernaryOperationExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class TernaryOperationRuleExpressionTest {
    @Test
    public void ternaryOperator() throws Exception {
        TernaryOperationExpression result = ParskyTestUtils.parse("1 ? 2 : 3", TernaryOperationExpression.class);

        assertThat(result.getFirstExpression(), instanceOf(NumberConstantExpression.class));
    }
}