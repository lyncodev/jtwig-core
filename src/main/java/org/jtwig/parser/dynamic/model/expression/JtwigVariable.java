package org.jtwig.parser.dynamic.model.expression;

import org.jtwig.parsing.model.Range;

public class JtwigVariable extends JtwigExpression {
    private final String name;

    public JtwigVariable(Range range, String name) {
        super(range);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
