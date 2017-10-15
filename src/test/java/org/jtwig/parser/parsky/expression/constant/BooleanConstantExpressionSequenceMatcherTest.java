package org.jtwig.parser.parsky.expression.constant;

import org.jtwig.model.expression.constant.BooleanConstantExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;
import org.parsky.sequence.model.SequenceMatcherResult;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BooleanConstantExpressionSequenceMatcherTest {
    private BooleanConstantExpressionSequenceMatcher underTest = new BooleanConstantExpressionSequenceMatcher();

    @Test
    public void trueTest() throws Exception {
        BooleanConstantExpression result = ParskyTestUtils.parse("true", underTest);

        assertThat(result.getConstantValue(), is(true));
    }

    @Test
    public void falseTest() throws Exception {
        BooleanConstantExpression result = ParskyTestUtils.parse("false", underTest);

        assertThat(result.getConstantValue(), is(false));
    }

    @Test
    public void unmatch() throws Exception {
        SequenceMatcherResult<BooleanConstantExpression> result = ParskyTestUtils.match("test", underTest);

        assertThat(result.isMismatch(), is(true));
    }
}