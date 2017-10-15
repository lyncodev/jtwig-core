package org.jtwig.parser.parsky.position;

import org.jtwig.model.position.Position;
import org.jtwig.resource.reference.ResourceReference;
import org.junit.Test;
import org.parsky.sequence.model.Range;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class PositionFactoryTest {
    private final ResourceReference resourceReference = mock(ResourceReference.class);
    private final char[] content = "example\none\ntwo\nthree".toCharArray();
    private PositionFactory underTest = new PositionFactory(new IsNewLineService(), resourceReference, content);

    @Test
    public void firstCharacter() throws Exception {
        Position result = underTest.create(Range.range(0, 0));

        assertThat(result.getColumn(), is(0));
        assertThat(result.getLine(), is(0));
    }

    @Test
    public void firstCharOfSecondLine() throws Exception {
        Position result = underTest.create(Range.range(8, 0));

        assertThat(result.getColumn(), is(0));
        assertThat(result.getLine(), is(1));
    }

    @Test
    public void secondCharOfSecondLine() throws Exception {
        Position result = underTest.create(Range.range(9, 0));

        assertThat(result.getColumn(), is(1));
        assertThat(result.getLine(), is(1));
    }
}