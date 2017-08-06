package org.jtwig.parser.dynamic.factories;

import org.jtwig.parser.dynamic.IntegrationTestUtils;
import org.jtwig.parser.dynamic.factories.control.IsTrimWhiteSpaceParserFactory;
import org.jtwig.parser.dynamic.model.CommandJtwigNode;
import org.jtwig.parser.dynamic.model.ContentJtwigNode;
import org.jtwig.parser.dynamic.model.SequenceJtwigNode;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SequenceJtwigParserFactoryTest {
    private SequenceJtwigParserFactory underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new SequenceJtwigParserFactory(new JtwigParserFactoryRegistry(Arrays.<JtwigNodeParserFactory>asList(
                new CommandJtwigParserFactory(
                        new CommandJtwigNodeParserFactoryTest.MyCommandParser(),
                        new IsTrimWhiteSpaceParserFactory()
                ),
                new ContentJtwigParserFactory()
        )));
    }

    @Test
    public void simple() throws Exception {
        SequenceJtwigNode result = IntegrationTestUtils.parse(underTest, "hello {% example %} oi").output(SequenceJtwigNode.class);

        assertThat(result.getNodes().get(0), is(instanceOf(ContentJtwigNode.class)));
        assertThat(result.getNodes().get(1), is(instanceOf(CommandJtwigNode.class)));
        assertThat(result.getNodes().get(2), is(instanceOf(ContentJtwigNode.class)));
    }

}