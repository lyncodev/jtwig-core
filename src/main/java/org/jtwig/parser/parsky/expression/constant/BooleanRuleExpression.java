package org.jtwig.parser.parsky.expression.constant;

import org.jtwig.model.expression.constant.BooleanConstantExpression;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import static org.jtwig.model.position.Position.position;

public class BooleanRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar context) {
        return Rules.transform(
                Rules.firstOf(
                        Rules.transform(Rules.string("true"), Transforms.value(true)),
                        Rules.transform(Rules.string("false"), Transforms.value(false))
                ),
                new Transform() {
                    @Override
                    public Result transform(ParserRequest request, Object input) {
                        return Result.success(new BooleanConstantExpression(
                                position(request),
                                (Boolean) input
                        ));
                    }
                }
        );
    }
}
