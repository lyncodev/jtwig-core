package org.jtwig.parser.parsky.expression;

import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class VariableExpressionSequenceMatcherTest {
    private VariableExpressionSequenceMatcher underTest = new VariableExpressionSequenceMatcher();

    @Test
    public void variable() throws Exception {
        assertThat(ParskyTestUtils.parse("a123", underTest).getIdentifier(), is("a123"));
        assertThat(ParskyTestUtils.parse("_a", underTest).getIdentifier(), is("_a"));
        assertThat(ParskyTestUtils.parse("$1", underTest).getIdentifier(), is("$1"));
        assertThat(ParskyTestUtils.parse("test", underTest).getIdentifier(), is("test"));
    }


    @Test
    public void invalidVariable() throws Exception {
        assertThat(ParskyTestUtils.match("123", underTest).isMismatch(), is(true));
        assertThat(ParskyTestUtils.match("^a", underTest).isMismatch(), is(true));
        assertThat(ParskyTestUtils.match("!1", underTest).isMismatch(), is(true));
    }
}