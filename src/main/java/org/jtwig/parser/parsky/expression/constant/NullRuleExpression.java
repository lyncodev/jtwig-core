package org.jtwig.parser.parsky.expression.constant;

import org.jtwig.model.expression.constant.NullConstantExpression;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import static org.jtwig.model.position.Position.position;

public class NullRuleExpression implements RuleFactory {

    @Override
    public Rule create(Grammar grammar) {
        return Rules.transform(Rules.string("null"), new Transform() {
            @Override
            public Result transform(ParserRequest request, Object input) {
                return Result.success(new NullConstantExpression(position(request)));
            }
        });
    }
}
