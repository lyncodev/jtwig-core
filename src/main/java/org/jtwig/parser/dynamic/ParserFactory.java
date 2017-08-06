package org.jtwig.parser.dynamic;

import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.factories.ContentJtwigParserFactory;
import org.jtwig.parser.dynamic.factories.SequenceJtwigParserFactory;
import org.jtwig.parser.dynamic.factories.TagJtwigParserFactory;
import org.jtwig.parser.dynamic.factories.control.IsTrimWhiteSpaceParserFactory;
import org.jtwig.parser.dynamic.factories.tag.TagDefinitionJtwigParserFactory;
import org.jtwig.parser.dynamic.model.JtwigNode;
import org.jtwig.parsing.Parser;
import org.jtwig.parsing.factory.AssignReferenceSequenceMatcherFactory;
import org.jtwig.parsing.factory.ReferenceSequenceMatcherFactory;
import org.jtwig.parsing.factory.SequenceMatcherFactory;
import org.jtwig.parsing.factory.SequenceMatcherFactoryRegistry;
import org.jtwig.parsing.sequence.SequenceMatcher;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ParserFactory {
    public Parser<List<JtwigNode>> create(ParserConfiguration configuration) {
        IsTrimWhiteSpaceParserFactory isTrimWhiteSpaceParserFactory = new IsTrimWhiteSpaceParserFactory();
        AtomicReference<TransformSequenceMatcher<? extends JtwigNode>> reference = new AtomicReference<>();
        List<TagDefinitionJtwigParserFactory> tags = new ArrayList<>();

        List<SequenceMatcherFactory<ParserConfiguration, TransformSequenceMatcher<? extends JtwigNode>>> factories = new ArrayList<>(Collections.<SequenceMatcherFactory<ParserConfiguration, TransformSequenceMatcher<? extends JtwigNode>>>singleton((SequenceMatcherFactory) new ContentJtwigParserFactory()));
        SequenceMatcherFactory<ParserConfiguration, TransformSequenceMatcher<? extends JtwigNode>> returnReference = ReferenceSequenceMatcherFactory.returnReference(reference);

        for (TagDefinitionJtwigParserFactory tag : tags) {
            factories.add((SequenceMatcherFactory) new TagJtwigParserFactory(tag, isTrimWhiteSpaceParserFactory, returnReference));
        }

        return new Parser(
                List.class,

                (TransformSequenceMatcher) AssignReferenceSequenceMatcherFactory.assignReference(reference, (SequenceMatcherFactory) new SequenceJtwigParserFactory(
                        new SequenceMatcherFactoryRegistry<>(
                                (List<SequenceMatcherFactory<ParserConfiguration, SequenceMatcher>>) ((List) factories)
                        )
                )).create(configuration)
        );
    }
}
