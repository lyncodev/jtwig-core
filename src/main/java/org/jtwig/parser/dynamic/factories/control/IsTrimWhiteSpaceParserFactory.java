package org.jtwig.parser.dynamic.factories.control;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parsing.factory.SequenceMatcherFactory;
import org.jtwig.parsing.sequence.SequenceMatchers;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.FromStringTransformation;
import org.jtwig.parsing.transform.Transformations;

public class IsTrimWhiteSpaceParserFactory implements SequenceMatcherFactory<ParserConfiguration, TransformSequenceMatcher<Boolean>> {
    @Override
    public TransformSequenceMatcher<Boolean> create(final ParserConfiguration configuration) {
        return SequenceMatchers.transform(
                SequenceMatchers.optional(
                        SequenceMatchers.string(configuration.getTrimWhiteSpace())
                ),
                Transformations.fromString(new Function<FromStringTransformation.Request, Boolean>() {
                    @Override
                    public Boolean apply(FromStringTransformation.Request input) {
                        return configuration.getTrimWhiteSpace().equals(input.getInput());
                    }
                })
        );
    }
}
