package org.jtwig.parser.parsky.expression.test;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operations.test.SameAsTestExpression;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

public class SameAsTestRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar context) {
        return Rules.transform(
                Rules.sequence(
                        Rules.skipWhitespaces(Rules.string("same")),
                        Rules.skipWhitespaces(Rules.string("as")),
                        context.rule(Expression.class)
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        return Transform.Result.success(new SameAsTestExpression(input.get(2, Expression.class)));
                    }
                })
        );
    }
}
