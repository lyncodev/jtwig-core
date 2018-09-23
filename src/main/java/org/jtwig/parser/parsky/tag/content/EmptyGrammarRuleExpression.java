package org.jtwig.parser.parsky.tag.content;

import org.parsky.grammar.Grammar;
import org.parsky.grammar.GrammarRuleExpression;
import org.parsky.sequence.SequenceMatcher;
import org.parsky.sequence.SequenceMatchers;

public class EmptyGrammarRuleExpression implements GrammarRuleExpression {
    private static final EmptyGrammarRuleExpression INSTANCE = new EmptyGrammarRuleExpression();

    public static EmptyGrammarRuleExpression instance () {
        return INSTANCE;
    }

    private EmptyGrammarRuleExpression() {}

    @Override
    public SequenceMatcher sequenceMatcher(Grammar context) {
        return SequenceMatchers.empty();
    }
}
