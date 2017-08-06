package org.jtwig.parser.dynamic.model.expression;

public class JtwigVariable implements JtwigExpression {
    private final String name;

    public JtwigVariable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
