package org.jtwig.parser.parsky.expression.basic;

import com.google.common.collect.ImmutableList;
import org.jtwig.model.expression.Expression;
import org.jtwig.parser.parsky.ParserContext;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transforms;

public class PrimaryRuleExpression implements RuleFactory {
    private final RuleFactory ruleFactory;

    public PrimaryRuleExpression(RuleFactory ruleFactory) {
        this.ruleFactory = ruleFactory;
    }

    @Override
    public Rule create(Grammar context) {
        return Rules.firstOf(
                ImmutableList.<Rule>builder()
                        .add(Rules.transform(
                                Rules.sequence(
                                        Rules.skipWhitespacesAfter(Rules.string("(")),
                                        Rules.skipWhitespacesAfter(context.rule(Expression.class)),
                                        Rules.mandatory(Rules.string(")"), "Uneven parentheses")
                                ),
                                Transforms.pick(1)
                        ))
                        .add(ruleFactory.create(context))
                        .build()
        );
    }
}
