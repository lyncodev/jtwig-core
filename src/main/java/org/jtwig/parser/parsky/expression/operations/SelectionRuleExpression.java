package org.jtwig.parser.parsky.expression.operations;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.collections.MapExpression;
import org.jtwig.model.expression.operations.FunctionExpression;
import org.jtwig.model.expression.operations.MapSelectionExpression;
import org.jtwig.model.expression.operations.SelectionExpression;
import org.jtwig.model.position.Position;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.jtwig.render.expression.calculator.operation.binary.impl.SelectionOperator;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactories;
import org.parsky.grammar.recursive.DependentRuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

public class SelectionRuleExpression implements DependentRuleFactory {
    @Override
    public Rule create(Rule rule, Grammar grammar) {
        return Rules.transform(
                Rules.flatten(Rules.sequence(
                        rule,

                        Rules.oneOrMore(
                                Rules.transform(
                                        Rules.sequence(
                                                Rules.skipWhitespaces(Rules.string(".")),
                                                Rules.firstOf(
                                                        grammar.rule(MapSelectionExpression.class),
                                                        grammar.rule(FunctionExpression.class),
                                                        grammar.rule(VariableExpression.class)
                                                )
                                        ),
                                        Transforms.pick(1)
                                )
                        )
                )),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        Position position = Position.position(request);
                        return Transform.Result.success(buildResult(input, position, input.size() - 1));
                    }

                    private Expression buildResult(ListTransform.Request input, Position position, int offset) {
                        if (offset == 0) return input.get(0, Expression.class);
                        else {
                            Expression expression = input.get(offset, Expression.class);
                            if (expression instanceof MapSelectionExpression) {
                                return new MapSelectionExpression(
                                        position,
                                        new SelectionExpression(
                                                position,
                                                buildResult(input, position, offset - 1),
                                                new SelectionOperator(),
                                                ((MapSelectionExpression) expression).getMapExpression()
                                        ),
                                        ((MapSelectionExpression) expression).getSelectValue()
                                );
                            } else {
                                return new SelectionExpression(
                                        position,
                                        buildResult(input, position, offset - 1),
                                        new SelectionOperator(),
                                        expression
                                );
                            }
                        }
                    }
                })
        );
    }
}
