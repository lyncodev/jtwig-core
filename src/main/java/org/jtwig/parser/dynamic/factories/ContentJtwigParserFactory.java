package org.jtwig.parser.dynamic.factories;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.ContentJtwigNode;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.Transformations;

import static org.jtwig.parsing.sequence.SequenceMatchers.flatten;
import static org.jtwig.parsing.sequence.SequenceMatchers.or;
import static org.jtwig.parsing.sequence.SequenceMatchers.string;
import static org.jtwig.parsing.sequence.SequenceMatchers.transform;
import static org.jtwig.parsing.sequence.SequenceMatchers.until;

public class ContentJtwigParserFactory implements JtwigParserFactory<ContentJtwigNode> {
    @Override
    public TransformSequenceMatcher<ContentJtwigNode> create(ParserConfiguration configuration) {
        return transform(
                flatten(until(or(
                        string(configuration.getCodeIslandConfiguration().getStartCode()),
                        string(configuration.getCodeIslandConfiguration().getStartOutput()),
                        string(configuration.getCodeIslandConfiguration().getStartComment())
                ))),
                Transformations.fromString(new Function<String, ContentJtwigNode>() {
                    @Override
                    public ContentJtwigNode apply(String input) {
                        return new ContentJtwigNode(input);
                    }
                })
        );
    }
}
