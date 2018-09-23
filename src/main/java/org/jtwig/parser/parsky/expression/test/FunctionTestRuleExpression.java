package org.jtwig.parser.parsky.expression.test;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.operations.FunctionExpression;
import org.jtwig.model.expression.operations.InjectableExpression;
import org.jtwig.model.expression.operations.test.FunctionTestExpression;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

public class FunctionTestRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar context) {
        return Rules.transform(
                Rules.firstOf(
                        context.rule(FunctionExpression.class),
                        context.rule(VariableExpression.class)
                ),
                new Transform() {
                    @Override
                    public Result transform(ParserRequest request, Object input) {
                        return Transform.Result.success(new FunctionTestExpression((InjectableExpression) input));
                    }
                }
        );
    }
}
