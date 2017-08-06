package org.jtwig.parser.dynamic.factories;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.ContentJtwigNode;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.FromStringTransformation;
import org.jtwig.parsing.transform.Transformations;

import static org.jtwig.parsing.sequence.SequenceMatchers.*;

public class ContentJtwigParserFactory implements JtwigNodeParserFactory<ContentJtwigNode> {
    @Override
    public TransformSequenceMatcher<ContentJtwigNode> create(ParserConfiguration configuration) {
        return transform(
                flatten(until(or(
                        string(configuration.getCodeIslandConfiguration().getStartCode()),
                        string(configuration.getCodeIslandConfiguration().getStartOutput()),
                        string(configuration.getCodeIslandConfiguration().getStartComment())
                ))),
                Transformations.fromString(new Function<FromStringTransformation.Request, ContentJtwigNode>() {
                    @Override
                    public ContentJtwigNode apply(FromStringTransformation.Request input) {
                        return new ContentJtwigNode(input.getResult().getRange(), input.getInput());
                    }
                })
        );
    }
}
