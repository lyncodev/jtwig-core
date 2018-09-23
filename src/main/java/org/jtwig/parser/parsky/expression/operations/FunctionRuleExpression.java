package org.jtwig.parser.parsky.expression.operations;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operations.FunctionExpression;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.List;

import static org.jtwig.model.position.Position.position;

public class FunctionRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar grammar) {
        return Rules.transform(
                Rules.sequence(
                        Rules.skipWhitespacesAfter(grammar.rule(JtwigExpressionGrammar.IDENTIFIER)),
                        Rules.list(
                                grammar.rule(Expression.class),
                                Rules.string("("),
                                Rules.string(","),
                                Rules.mandatory(Rules.string(")"), "Missing end parenthesis")
                        )
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        return Transform.Result.success(new FunctionExpression(
                                position(request),
                                input.get(0, String.class),
                                input.get(1, List.class)
                        ));
                    }
                })
        );
    }

}
