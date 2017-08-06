package org.jtwig.parser.dynamic.factories.expression;

import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.expression.JtwigExpression;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;

public interface ExpressionJtwigParserFactory<T extends JtwigExpression> {
    TransformSequenceMatcher<T> create (ParserConfiguration configuration);
}
