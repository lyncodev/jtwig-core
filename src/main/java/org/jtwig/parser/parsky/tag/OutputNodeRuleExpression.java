package org.jtwig.parser.parsky.tag;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.OutputNode;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import static org.jtwig.model.position.Position.position;

public class OutputNodeRuleExpression implements RuleFactory {
    private final String startTagRule;
    private final String endTagRule;

    public OutputNodeRuleExpression(String startTagRule, String endTagRule) {
        this.startTagRule = startTagRule;
        this.endTagRule = endTagRule;
    }

    @Override
    public Rule create(Grammar context) {
        return Rules.transform(
                Rules.sequence(
                        Rules.skipWhitespacesAfter(context.rule(startTagRule)),
                        Rules.skipWhitespacesAfter(Rules.mandatory(context.rule(Expression.class), "Missing or invalid output expression")),
                        Rules.test(Rules.skipWhitespaces(Rules.mandatory(Rules.not(context.rule(Expression.class)), "Invalid expression"))),
                        Rules.mandatory(context.rule(endTagRule), "Code island not closed")
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        return Transform.Result.success(new OutputNode(
                                position(request),
                                input.get(1, Expression.class)
                        ));
                    }
                })
        );
    }
}
