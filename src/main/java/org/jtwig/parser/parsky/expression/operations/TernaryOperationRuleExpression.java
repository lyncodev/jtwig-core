package org.jtwig.parser.parsky.expression.operations;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operations.TernaryOperationExpression;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.recursive.DependentRuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import static org.jtwig.model.position.Position.position;

public class TernaryOperationRuleExpression implements DependentRuleFactory {
    @Override
    public Rule create(Rule rule, Grammar grammar) {
        return Rules.transform(
                Rules.sequence(
                        rule,
                        Rules.skipWhitespaces(Rules.string("?")),
                        grammar.rule(Expression.class),
                        Rules.skipWhitespaces(Rules.string(":")),
                        grammar.rule(Expression.class)
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        return Transform.Result.success(new TernaryOperationExpression(
                                position(request),
                                input.get(0, Expression.class),
                                input.get(2, Expression.class),
                                input.get(4, Expression.class)
                        ));
                    }
                })
        );
    }
}
