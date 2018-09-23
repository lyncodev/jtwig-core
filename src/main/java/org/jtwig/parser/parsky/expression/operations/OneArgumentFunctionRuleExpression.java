package org.jtwig.parser.parsky.expression.operations;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operations.FunctionExpression;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.Collections;

import static org.jtwig.model.position.Position.position;

public class OneArgumentFunctionRuleExpression implements RuleFactory {
    private final RuleFactory ruleFactory;

    public OneArgumentFunctionRuleExpression(RuleFactory ruleFactory) {
        this.ruleFactory = ruleFactory;
    }

    @Override
    public Rule create(Grammar grammar) {
        return Rules.transform(
                Rules.sequence(
                        Rules.skipWhitespacesAfter(grammar.rule(JtwigExpressionGrammar.IDENTIFIER)),
                        ruleFactory.create(grammar)
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        return Transform.Result.success(new FunctionExpression(
                                position(request),
                                input.get(0, String.class),
                                Collections.singletonList(input.get(1, Expression.class))
                        ));
                    }
                })
        );
    }

}
