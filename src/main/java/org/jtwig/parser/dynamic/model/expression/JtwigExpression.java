package org.jtwig.parser.dynamic.model.expression;

import org.jtwig.parsing.model.Range;

public abstract class JtwigExpression {
    private final Range range;

    public JtwigExpression(Range range) {
        this.range = range;
    }

    public Range getRange() {
        return range;
    }
}
