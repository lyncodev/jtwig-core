package org.jtwig.parser.parsky.expression.test;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operations.TestOperationExpression;
import org.jtwig.model.expression.operations.test.NotTestExpression;
import org.jtwig.model.expression.operations.test.TestExpression;
import org.jtwig.model.position.Position;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.recursive.DependentRuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

public class TestOperationRuleExpression implements DependentRuleFactory {
    @Override
    public Rule create(Rule rule, Grammar grammar) {
        return Rules.transform(
                Rules.sequence(
                        Rules.skipWhitespacesAfter(rule),
                        Rules.skipWhitespacesAfter(Rules.string("is")),
                        Rules.optional(Rules.skipWhitespacesAfter(Rules.string("not"))),
                        Rules.mandatory(grammar.rule(TestExpression.class), "Missing test expression")
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        if (input.get(2) == null) {
                            return Transform.Result.success(new TestOperationExpression(
                                    Position.position(request),
                                    input.get(0, Expression.class),
                                    input.get(3, TestExpression.class)
                            ));
                        } else {
                            return Transform.Result.success(new TestOperationExpression(
                                    Position.position(request),
                                    input.get(0, Expression.class),
                                    new NotTestExpression(input.get(3, TestExpression.class))
                            ));
                        }
                    }
                })
        );
    }
}
