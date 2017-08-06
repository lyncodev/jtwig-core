package org.jtwig.parser.dynamic.factories;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.ContentJtwigNode;
import org.jtwig.parser.dynamic.model.position.Position;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.ListTransformationRequest;
import org.jtwig.parsing.transform.Transformations;

import static org.jtwig.parsing.sequence.SequenceMatchers.flatten;
import static org.jtwig.parsing.sequence.SequenceMatchers.or;
import static org.jtwig.parsing.sequence.SequenceMatchers.position;
import static org.jtwig.parsing.sequence.SequenceMatchers.sequence;
import static org.jtwig.parsing.sequence.SequenceMatchers.string;
import static org.jtwig.parsing.sequence.SequenceMatchers.transform;
import static org.jtwig.parsing.sequence.SequenceMatchers.until;

public class ContentJtwigParserFactory implements JtwigNodeParserFactory<ContentJtwigNode> {
    @Override
    public TransformSequenceMatcher<ContentJtwigNode> create(ParserConfiguration configuration) {
        return transform(
                sequence(
                        position(),
                        transform(
                                flatten(until(or(
                                        string(configuration.getCodeIslandConfiguration().getStartCode()),
                                        string(configuration.getCodeIslandConfiguration().getStartOutput()),
                                        string(configuration.getCodeIslandConfiguration().getStartComment())
                                ))),
                                Transformations.toContentString()
                        ),
                        position()
                ),
                Transformations.fromContentList(new Function<ListTransformationRequest, ContentJtwigNode>() {
                    @Override
                    public ContentJtwigNode apply(ListTransformationRequest input) {
                        return new ContentJtwigNode(new Position(input.get(0, Integer.class), input.get(2, Integer.class)), input.get(1, String.class));
                    }
                })
        );
    }
}
