package org.jtwig.parser.parsky.tag;

import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;

public class CommentRuleExpression implements RuleFactory {
    private final String startCommentRule;
    private final String endCommentRule;

    public CommentRuleExpression(String startCommentRule, String endCommentRule) {
        this.startCommentRule = startCommentRule;
        this.endCommentRule = endCommentRule;
    }

    @Override
    public Rule create(Grammar context) {
        return Rules.sequence(
                context.rule(startCommentRule),
                Rules.zeroOrMore(Rules.not(
                        Rules.firstOf(
                                context.rule(endCommentRule),
                                Rules.endOfInput()
                        )
                )),
                Rules.firstOf(
                        context.rule(endCommentRule),
                        Rules.endOfInput()
                )
        );
    }
}
