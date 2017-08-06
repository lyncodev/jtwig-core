package org.jtwig.parser.dynamic.model.command;

import org.jtwig.parser.dynamic.model.expression.JtwigExpression;
import org.jtwig.parser.dynamic.model.expression.JtwigVariable;

public class SetJtwigCommandDefinition implements JtwigCommandDefinition {
    private final JtwigVariable variable;
    private final JtwigExpression expression;

    public SetJtwigCommandDefinition(JtwigVariable variable, JtwigExpression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public JtwigVariable getVariable() {
        return variable;
    }

    public JtwigExpression getExpression() {
        return expression;
    }
}
