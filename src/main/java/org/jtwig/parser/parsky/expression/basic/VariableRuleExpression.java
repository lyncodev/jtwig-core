package org.jtwig.parser.parsky.expression.basic;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import static org.jtwig.model.position.Position.position;

public class VariableRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar grammar) {
        // [a-zA-Z_$][a-zA-Z0-9_$]*
        return Rules.transform(
                grammar.rule(JtwigExpressionGrammar.IDENTIFIER),
                new Transform() {
                    @Override
                    public Result transform(ParserRequest request, Object input) {
                        return Transform.Result.success(new VariableExpression(
                                position(request),
                                (String) input
                        ));
                    }
                }
        );
    }
}
