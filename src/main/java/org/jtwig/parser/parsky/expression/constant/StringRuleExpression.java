package org.jtwig.parser.parsky.expression.constant;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jtwig.model.expression.constant.StringConstantExpression;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.Arrays;

import static org.jtwig.model.position.Position.position;

public class StringRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar grammar) {
        return Rules.firstOf(Arrays.asList(
                stringWith('"'),
                stringWith('\'')
        ));
    }
    private static Rule stringWith(char character) {
        return Rules.transform(
                Rules.sequence(
                        Rules.character(character),
                        Rules.text(Rules.zeroOrMore(
                                Rules.firstOf(
                                        Rules.sequence(
                                                Rules.string("\\"),
                                                Rules.not(Rules.endOfInput())
                                        ),
                                        Rules.not(Rules.character(character))
                                )
                        )),
                        Rules.character(character)
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        return Transform.Result.success(new StringConstantExpression(
                                position(request),
                                StringEscapeUtils.unescapeJava(input.get(1, String.class))
                        ));
                    }
                })
        );
    }
}
