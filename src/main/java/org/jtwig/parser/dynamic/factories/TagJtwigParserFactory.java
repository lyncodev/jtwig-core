package org.jtwig.parser.dynamic.factories;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.factories.control.IsTrimWhiteSpaceParserFactory;
import org.jtwig.parser.dynamic.factories.tag.TagDefinitionJtwigParserFactory;
import org.jtwig.parser.dynamic.model.JtwigNode;
import org.jtwig.parser.dynamic.model.TagJtwigNode;
import org.jtwig.parser.dynamic.model.control.TagWhiteSpaceControl;
import org.jtwig.parser.dynamic.model.tag.JtwigEndTagDefinition;
import org.jtwig.parser.dynamic.model.tag.JtwigStartTagDefinition;
import org.jtwig.parsing.sequence.SequenceMatchers;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.ListTransformationRequest;
import org.jtwig.parsing.transform.Transformations;

import static org.jtwig.parsing.sequence.SequenceMatchers.skipWhitespaces;
import static org.jtwig.parsing.sequence.SequenceMatchers.string;

public class TagJtwigParserFactory implements JtwigParserFactory<TagJtwigNode> {
    private final TagDefinitionJtwigParserFactory tagDefinitionJtwigParserFactory;
    private final IsTrimWhiteSpaceParserFactory isTrimWhiteSpaceParserFactory;
    private final SequenceJtwigParserFactory sequenceJtwigParserFactory;

    public TagJtwigParserFactory(TagDefinitionJtwigParserFactory tagDefinitionJtwigParserFactory, IsTrimWhiteSpaceParserFactory isTrimWhiteSpaceParserFactory, SequenceJtwigParserFactory sequenceJtwigParserFactory) {
        this.tagDefinitionJtwigParserFactory = tagDefinitionJtwigParserFactory;
        this.isTrimWhiteSpaceParserFactory = isTrimWhiteSpaceParserFactory;
        this.sequenceJtwigParserFactory = sequenceJtwigParserFactory;
    }

    @Override
    public TransformSequenceMatcher<TagJtwigNode> create(ParserConfiguration configuration) {
        return SequenceMatchers.transform(
                SequenceMatchers.sequence(
                        string(configuration.getCodeIslandConfiguration().getStartCode()),
                        isTrimWhiteSpaceParserFactory.create(configuration),
                        skipWhitespaces(tagDefinitionJtwigParserFactory.createStartTag(configuration)),
                        isTrimWhiteSpaceParserFactory.create(configuration),
                        string(configuration.getCodeIslandConfiguration().getEndCode()),

                        sequenceJtwigParserFactory.create(configuration),

                        string(configuration.getCodeIslandConfiguration().getStartCode()),
                        isTrimWhiteSpaceParserFactory.create(configuration),
                        skipWhitespaces(tagDefinitionJtwigParserFactory.createEndTag(configuration)),
                        isTrimWhiteSpaceParserFactory.create(configuration),
                        string(configuration.getCodeIslandConfiguration().getEndCode())
                ),
                Transformations.fromContentList(new Function<ListTransformationRequest, TagJtwigNode>() {
                    @Override
                    public TagJtwigNode apply(ListTransformationRequest input) {
                        return new TagJtwigNode(
                                new TagWhiteSpaceControl(input.get(0, Boolean.class), input.get(2, Boolean.class)),
                                input.get(3, JtwigNode.class),
                                input.get(1, JtwigStartTagDefinition.class),
                                input.get(5, JtwigEndTagDefinition.class),
                                new TagWhiteSpaceControl(input.get(4, Boolean.class), input.get(6, Boolean.class))
                        );
                    }
                })
        );
    }
}
