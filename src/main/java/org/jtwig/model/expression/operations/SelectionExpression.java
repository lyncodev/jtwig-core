package org.jtwig.model.expression.operations;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;

public class SelectionExpression extends BinaryOperationExpression {
    public SelectionExpression(Position position, Expression leftOperand, BinaryOperator binaryOperator, Expression rightOperand) {
        super(position, leftOperand, binaryOperator, rightOperand);
    }
}
