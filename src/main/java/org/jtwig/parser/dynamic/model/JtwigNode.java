package org.jtwig.parser.dynamic.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jtwig.parsing.model.Range;

public abstract class JtwigNode {
    private final Range range;

    public JtwigNode(Range range) {
        this.range = range;
    }

    public Range getRange() {
        return range;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
