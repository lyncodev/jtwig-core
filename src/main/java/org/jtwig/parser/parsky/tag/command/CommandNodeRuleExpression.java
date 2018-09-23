package org.jtwig.parser.parsky.tag.command;

import com.google.common.base.Function;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.basic.KeywordRuleExpressionFactory;
import org.jtwig.parser.config.command.CommandNodeConfiguration;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import static org.jtwig.model.position.Position.position;

public class CommandNodeRuleExpression implements RuleFactory {
    public static CommandNodeRuleExpression create (KeywordRuleExpressionFactory factory, CommandNodeConfiguration configuration) {
        return new CommandNodeRuleExpression(
                configuration.getKeyword(),
                JtwigGrammar.START_CODE,
                JtwigGrammar.END_CODE,
                configuration.getRule(),
                configuration.getNodeFactory(),
                factory
        );
    }

    private final String keyword;
    private final String startRule;
    private final String endRule;
    private final RuleFactory contentExpression;
    private final CommandNodeFactory nodeFactory;
    private final KeywordRuleExpressionFactory keywordRuleExpressionFactory;

    public CommandNodeRuleExpression(String keyword, String startRule, String endRule, RuleFactory contentExpression, CommandNodeFactory nodeFactory, KeywordRuleExpressionFactory keywordRuleExpressionFactory) {
        this.keyword = keyword;
        this.startRule = startRule;
        this.endRule = endRule;
        this.contentExpression = contentExpression;
        this.nodeFactory = nodeFactory;
        this.keywordRuleExpressionFactory = keywordRuleExpressionFactory;
    }

    @Override
    public Rule create(Grammar context) {
        return Rules.transform(
                Rules.sequence(
                        context.rule(startRule),
                        Rules.skipWhitespaces(keywordRuleExpressionFactory.create(keyword)),
                        Rules.skipWhitespacesAfter(contentExpression.create(context)),
                        Rules.mandatory(context.rule(endRule), "Code island not closed")
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        return nodeFactory.create(
                                position(request),
                                input.get(2)
                        );
                    }
                })
        );
    }
}
