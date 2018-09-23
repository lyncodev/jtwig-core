package org.jtwig.parser.parsky.basic;

import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;

public class InitialIdentifierRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar grammar) {
        return Rules.firstOf(
                Rules.range('a', 'z'),
                Rules.range('A', 'Z'),
                Rules.anyOf("_$")
        );
    }
}
