package org.jtwig.parser.parsky.tag;

import com.google.common.base.Function;
import org.jtwig.parser.config.TagConfiguration;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.ArrayList;
import java.util.List;

public class UnknownTagRuleExpression implements RuleFactory {
    public static UnknownTagRuleExpression create(List<TagConfiguration> tags) {
        List<String> possibleKeywords = new ArrayList<>();
        for (TagConfiguration tag : tags) {
            possibleKeywords.addAll(tag.getTags());
        }
        return new UnknownTagRuleExpression(possibleKeywords);
    }

    private final List<String> possibleKeywords;

    public UnknownTagRuleExpression(List<String> possibleKeywords) {
        this.possibleKeywords = possibleKeywords;
    }

    @Override
    public Rule create(Grammar context) {
        return Rules.sequence(
                Rules.skipWhitespacesAfter(context.rule(JtwigGrammar.START_CODE)),
                Rules.test(Rules.not(anyOf(possibleKeywords))),
                Rules.firstOf(
                        Rules.transform(
                                Rules.text(Rules.zeroOrMore(Rules.not(context.rule(JtwigExpressionGrammar.IDENTIFIER)))),
                                new Transform() {
                                    @Override
                                    public Result transform(ParserRequest request, Object input) {
                                        return Transform.Result.fail(String.format("Unknown tag '%s'", input));
                                    }
                                }
                        ),
                        Rules.fail("Unknown tag construct")
                )
        );
    }

    private Rule anyOf(List<String> possibleKeywords) {
        List<Rule> result= new ArrayList<>();
        for (String keyword : possibleKeywords) {
            result.add(Rules.string(keyword));
        }
        return Rules.firstOf(result);
    }
}
