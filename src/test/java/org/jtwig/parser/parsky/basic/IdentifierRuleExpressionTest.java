package org.jtwig.parser.parsky.basic;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IdentifierRuleExpressionTest {
    @Test
    public void identifier() throws Exception {
        VariableExpression result = ParskyTestUtils.parse("abc", VariableExpression.class);

        assertThat(result.getIdentifier(), is("abc"));
    }
}