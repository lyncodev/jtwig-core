package org.jtwig.parser.dynamic.factories.control;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parsing.sequence.SequenceMatchers;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.Transformations;

public class IsTrimWhiteSpaceParserFactory {
    public TransformSequenceMatcher<Boolean> create (final ParserConfiguration configuration) {
        return SequenceMatchers.transform(
                SequenceMatchers.optional(
                        SequenceMatchers.string(configuration.getTrimWhiteSpace())
                ),
                Transformations.fromString(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(String input) {
                        return configuration.getTrimWhiteSpace().equals(input);
                    }
                })
        );
    }
}
