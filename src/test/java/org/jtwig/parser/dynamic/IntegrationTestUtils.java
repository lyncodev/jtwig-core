package org.jtwig.parser.dynamic;

import org.jtwig.parser.dynamic.config.DefaultParserConfiguration;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parsing.Parser;
import org.jtwig.parsing.factory.SequenceMatcherFactory;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;

public class IntegrationTestUtils {

    private static final DefaultParserConfiguration CONFIGURATION = new DefaultParserConfiguration();

    public static <T> Parser.Result<T> parse (Class<T> type, SequenceMatcherFactory<ParserConfiguration, TransformSequenceMatcher<T>> parser, String input) {
        return new Parser<>(type, parser.create(CONFIGURATION)).parse(input);
    }

    public static ParserConfiguration configuration() {
        return CONFIGURATION;
    }
}
