package org.jtwig.parser.parsky.basic;

import org.jtwig.parser.parsky.ParserContext;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;

public class KeywordRuleExpressionFactory {
    public Rule create(String keyword) {
        return Rules.sequence(
                Rules.<ParserContext>string(keyword),
                Rules.test(
                        Rules.not(
                                Rules.firstOf(
                                        Rules.range('a', 'z'),
                                        Rules.range('A', 'Z'),
                                        Rules.anyOf("_$")
                                )
                        )
                )
        );
    }
}
