package org.jtwig.parser.dynamic.factories;

import org.jtwig.parser.dynamic.IntegrationTestUtils;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.factories.control.IsTrimWhiteSpaceParserFactory;
import org.jtwig.parser.dynamic.factories.tag.TagDefinitionJtwigParserFactory;
import org.jtwig.parser.dynamic.model.JtwigNode;
import org.jtwig.parser.dynamic.model.TagJtwigNode;
import org.jtwig.parser.dynamic.model.tag.JtwigEndTagDefinition;
import org.jtwig.parser.dynamic.model.tag.JtwigStartTagDefinition;
import org.jtwig.parsing.Parser;
import org.jtwig.parsing.factory.SequenceMatcherFactory;
import org.jtwig.parsing.factory.SequenceMatcherFactoryRegistry;
import org.jtwig.parsing.sequence.SequenceMatcher;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.Transformations;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.jtwig.parsing.sequence.SequenceMatchers.string;
import static org.jtwig.parsing.sequence.SequenceMatchers.transform;
import static org.junit.Assert.assertThat;

public class TagJtwigParserFactoryTest {
    private JtwigNodeParserFactory<TagJtwigNode> underTest = new TagJtwigParserFactory(
                new MyTagParser(),
                new IsTrimWhiteSpaceParserFactory(), sequenceFactory());

    private <T extends JtwigNode> SequenceMatcherFactory<ParserConfiguration, TransformSequenceMatcher<T>> sequenceFactory() {
        return new SequenceJtwigParserFactory<>(new SequenceMatcherFactoryRegistry<ParserConfiguration, SequenceMatcher>(createFactories()));
    }

    private List createFactories() {
        return (List) Arrays.asList(

                new CommandJtwigParserFactory(
                        new CommandJtwigNodeParserFactoryTest.MyCommandParser(),
                        new IsTrimWhiteSpaceParserFactory()
                ),
                new ContentJtwigParserFactory()
        );
    }

    @Test
    public void simple() throws Exception {
        TagJtwigNode result = IntegrationTestUtils.parse(underTest, "{% mytag  %}Example{% endmytag  %}").output(TagJtwigNode.class);

        assertThat(result.getStartTagDefinition(), instanceOf(StartMyTag.class));
        assertThat(result.getEndTagDefinition(), instanceOf(EndMyTag.class));
    }

    @Test
    public void noEndingTag() throws Exception {
        Parser.Result result = IntegrationTestUtils.parse(underTest, "{% mytag  %}Example");

        assertThat(result.isError(), is(true));
    }

    public static class MyTagParser implements TagDefinitionJtwigParserFactory<StartMyTag, EndMyTag> {
        @Override
        public TransformSequenceMatcher<StartMyTag> createStartTag(ParserConfiguration parserConfiguration) {
            return transform(
                    string("mytag"),
                    Transformations.constant(new StartMyTag())
            );
        }

        @Override
        public TransformSequenceMatcher<EndMyTag> createEndTag(ParserConfiguration parserConfiguration) {
            return transform(
                    string("endmytag"),
                    Transformations.constant(new EndMyTag())
            );
        }
    }

    public static class StartMyTag implements JtwigStartTagDefinition {
    }

    public static class EndMyTag implements JtwigEndTagDefinition {
    }
}