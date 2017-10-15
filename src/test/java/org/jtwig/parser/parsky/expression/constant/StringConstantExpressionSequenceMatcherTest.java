package org.jtwig.parser.parsky.expression.constant;

import org.jtwig.model.expression.constant.StringConstantExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;
import org.parsky.sequence.model.SequenceMatcherResult;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StringConstantExpressionSequenceMatcherTest {
    private StringConstantExpressionSequenceMatcher underTest = new StringConstantExpressionSequenceMatcher();

    @Test
    public void singleQuoteString() throws Exception {
        StringConstantExpression result = ParskyTestUtils.parse("'example'", underTest);

        assertThat(result.getConstantValue(), is("example"));
    }

    @Test
    public void doubleQuoteString() throws Exception {
        StringConstantExpression result = ParskyTestUtils.parse("\"example\"", underTest);

        assertThat(result.getConstantValue(), is("example"));
    }

    @Test
    public void wrongQuote() throws Exception {
        SequenceMatcherResult<StringConstantExpression> result = ParskyTestUtils.match("\"example", underTest);

        assertThat(result.isMismatch(), is(true));
    }
}