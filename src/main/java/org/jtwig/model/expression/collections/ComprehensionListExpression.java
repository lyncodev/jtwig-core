package org.jtwig.model.expression.collections;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;

public class ComprehensionListExpression extends Expression {
    private final Expression start;
    private final Expression end;

    public ComprehensionListExpression(Position position, Expression start, Expression end) {
        super(position);
        this.start = start;
        this.end = end;
    }

    public Expression getStart() {
        return start;
    }

    public Expression getEnd() {
        return end;
    }
}
