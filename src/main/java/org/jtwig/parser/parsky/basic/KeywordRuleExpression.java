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

public class KeywordRuleExpression implements RuleFactory {
    @Override
    public Rule create(final Grammar grammar) {
        return Rules.sequence(
                Rules.firstOf(
                        Lists2.transform(Arrays.asList(Keyword.values()), new Function<Keyword, Rule>() {
                            @Override
                            public Rule apply(Keyword input) {
                                return Rules.string(input.toString());
                            }
                        })
                ),
                Rules.not(grammar.rule(JtwigExpressionGrammar.INITIAL_IDENTIFIER))
        );
    }
}
