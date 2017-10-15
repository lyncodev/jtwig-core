package org.jtwig.parser.parsky.expression.constant;

import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;
import org.parsky.sequence.model.SequenceMatcherResult;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NumberConstantSequenceMatcherTest {
    private NumberConstantSequenceMatcher underTest = new NumberConstantSequenceMatcher();

    @Test
    public void integer() throws Exception {
        NumberConstantExpression result = ParskyTestUtils.parse("123", underTest);

        assertThat(result.getConstantValue(), is(new BigDecimal("123")));
    }

    @Test
    public void floatNumber() throws Exception {
        NumberConstantExpression result = ParskyTestUtils.parse("123.123", underTest);

        assertThat(result.getConstantValue(), is(new BigDecimal("123.123")));
    }

    @Test
    public void noMatch() throws Exception {
        SequenceMatcherResult<NumberConstantExpression> result = ParskyTestUtils.match("a", underTest);

        assertThat(result.isMismatch(), is(true));
    }
}