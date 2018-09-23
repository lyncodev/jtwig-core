package org.jtwig.parser.parsky.basic;

import com.google.common.base.Function;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.Keyword;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.jtwig.reflection.util.Lists2;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;

import java.util.Arrays;

public class IdentifierRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar grammar) {
        return Rules.text(Rules.sequence(
                Rules.test(Rules.not(grammar.rule(JtwigExpressionGrammar.KEYWORD))),
                grammar.rule(JtwigExpressionGrammar.INITIAL_IDENTIFIER),
                Rules.zeroOrMore(
                        Rules.firstOf(
                                Rules.range('a', 'z'),
                                Rules.range('A', 'Z'),
                                Rules.range('0', '9'),
                                Rules.anyOf("_$")
                        )
                )
        ));
    }
}
