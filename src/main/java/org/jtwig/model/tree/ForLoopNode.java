package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;


public class ForLoopNode extends ContentNode {
    private final Optional<VariableExpression> keyVariableExpression;
    private final VariableExpression variableExpression;
    public static ForLoopNode create(Position position, VariableExpression keyVariableExpression, VariableExpression variableExpression, Expression expression, Node content) {
        if (variableExpression == null) {
            return new ForLoopNode(
                    position,
                    keyVariableExpression,
                    expression,
                    content
            );
        } else {
            return new ForLoopNode(
                    position,
                    keyVariableExpression,
                    variableExpression,
                    expression,
                    content
            );
        }
    }

    private final Expression expression;

    public ForLoopNode(Position position, VariableExpression keyVariableExpression, VariableExpression variableExpression, Expression expression, Node content) {
        super(position, content);
        this.keyVariableExpression = Optional.fromNullable(keyVariableExpression);
        this.variableExpression = variableExpression;
        this.expression = expression;
    }

    public ForLoopNode(Position position, VariableExpression variableExpression, Expression expression, Node content) {
        super(position, content);
        this.keyVariableExpression = Optional.absent();
        this.variableExpression = variableExpression;
        this.expression = expression;
    }

    public VariableExpression getVariableExpression() {
        return variableExpression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Optional<VariableExpression> getKeyVariableExpression() {
        return keyVariableExpression;
    }
}
