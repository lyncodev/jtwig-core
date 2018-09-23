package org.jtwig.model.expression.collections;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;

import java.util.List;

public class EnumeratedListExpression extends Expression {
    private final List<Expression> expressions;

    public EnumeratedListExpression(Position position, List<Expression> expressions) {
        super(position);
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }
}
