package org.jtwig.parser.parsky.expression.constant;

import com.google.common.base.Function;
import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import java.math.BigDecimal;

import static org.jtwig.model.position.Position.position;

public class NumberRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar context) {
        return Rules.transform(Rules.text(Rules.sequence(
                Rules.oneOrMore(Rules.range('0', '9')),
                Rules.optional(Rules.sequence(
                        Rules.character('.'),
                        Rules.oneOrMore(Rules.range('0', '9'))
                ))
        )), new Transform() {
            @Override
            public Result transform(ParserRequest request, Object input) {
                return Result.success(new NumberConstantExpression(
                        position(request),
                        new BigDecimal((String) input)
                ));
            }
        });
    }
}
