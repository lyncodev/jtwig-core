package org.jtwig.parser.dynamic.factories;

import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.JtwigNode;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;

public interface JtwigParserFactory<T extends JtwigNode> {
    TransformSequenceMatcher<T> create (ParserConfiguration configuration);
}
