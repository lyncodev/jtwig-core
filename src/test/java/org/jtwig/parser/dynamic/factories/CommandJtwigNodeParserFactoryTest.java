package org.jtwig.parser.dynamic.factories;

import org.jtwig.parser.dynamic.IntegrationTestUtils;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.factories.command.CommandDefinitionJtwigParserFactory;
import org.jtwig.parser.dynamic.factories.control.IsTrimWhiteSpaceParserFactory;
import org.jtwig.parser.dynamic.model.CommandJtwigNode;
import org.jtwig.parser.dynamic.model.command.JtwigCommandDefinition;
import org.jtwig.parsing.Parser;
import org.jtwig.parsing.sequence.SequenceMatchers;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.Transformations;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CommandJtwigNodeParserFactoryTest {
    private CommandJtwigParserFactory underTest = new CommandJtwigParserFactory(new MyCommandParser(), new IsTrimWhiteSpaceParserFactory());

    @Test
    public void noTrim() throws Exception {
        CommandJtwigNode result = IntegrationTestUtils.parse(underTest, "{% example %}").output(CommandJtwigNode.class);

        assertThat(result.getWhiteSpaceControl().isTrimBefore(), is(false));
        assertThat(result.getWhiteSpaceControl().isTrimAfter(), is(false));
        assertThat(result.getCommandDefinition(), instanceOf(MyCommand.class));
    }

    @Test
    public void trimBefore() throws Exception {
        CommandJtwigNode output = IntegrationTestUtils.parse(underTest, "{%-  example   %}").output(CommandJtwigNode.class);

        assertThat(output.getWhiteSpaceControl().isTrimBefore(), is(true));
        assertThat(output.getWhiteSpaceControl().isTrimAfter(), is(false));
        assertThat(output.getCommandDefinition(), instanceOf(MyCommand.class));
    }

    @Test
    public void trimAfter() throws Exception {
        CommandJtwigNode output = IntegrationTestUtils.parse(underTest, "{%  example   -%}").output(CommandJtwigNode.class);

        assertThat(output.getWhiteSpaceControl().isTrimBefore(), is(false));
        assertThat(output.getWhiteSpaceControl().isTrimAfter(), is(true));
        assertThat(output.getCommandDefinition(), instanceOf(MyCommand.class));
    }

    @Test
    public void trimBoth() throws Exception {
        CommandJtwigNode output = IntegrationTestUtils.parse(underTest, "{%-  example   -%}").output(CommandJtwigNode.class);

        assertThat(output.getWhiteSpaceControl().isTrimBefore(), is(true));
        assertThat(output.getWhiteSpaceControl().isTrimAfter(), is(true));
        assertThat(output.getCommandDefinition(), instanceOf(MyCommand.class));
    }

    @Test
    public void noClosing() throws Exception {
        Parser.Result result = IntegrationTestUtils.parse(underTest, "{% example  ");

        assertThat(result.isError(), is(true));
        assertThat(result.getOffset(), is(12));
    }

    public static class MyCommandParser implements CommandDefinitionJtwigParserFactory<MyCommand> {
        @Override
        public TransformSequenceMatcher<MyCommand> create(ParserConfiguration configuration) {
            return SequenceMatchers.transform(SequenceMatchers.string("example"), Transformations.constant(new MyCommand()));
        }
    }

    public static class MyCommand implements JtwigCommandDefinition {

    }
}