package org.jtwig.parser.parsky.expression.constant;

import org.jtwig.model.expression.constant.NullConstantExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;
import org.parsky.sequence.model.SequenceMatcherResult;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class NullConstantExpressionSequenceMatcherTest {
    private NullConstantExpressionSequenceMatcher underTest = new NullConstantExpressionSequenceMatcher();

    @Test
    public void nullValueTest() throws Exception {
        NullConstantExpression result = ParskyTestUtils.parse("null", underTest);

        assertThat(result.getConstantValue(), nullValue());
    }

    @Test
    public void noMatch() throws Exception {
        SequenceMatcherResult<NullConstantExpression> result = ParskyTestUtils.match("nul", underTest);

        assertThat(result.isMismatch(), is(true));

    }
}