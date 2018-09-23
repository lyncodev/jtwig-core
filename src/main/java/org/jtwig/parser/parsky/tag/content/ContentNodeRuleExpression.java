package org.jtwig.parser.parsky.tag.content;

import org.jtwig.parser.config.content.ContentNodeConfiguration;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.basic.KeywordRuleExpressionFactory;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import static org.jtwig.model.position.Position.position;

public class ContentNodeRuleExpression implements RuleFactory {
    public static ContentNodeRuleExpression create (KeywordRuleExpressionFactory factory, ContentNodeConfiguration configuration) {
        return new ContentNodeRuleExpression(
                configuration.getKeyword(),
                JtwigGrammar.START_CODE,
                JtwigGrammar.END_CODE,
                configuration.getStartRule(),
                configuration.getContentRule(),
                configuration.getEndRule(),
                configuration.getFactory(),
                factory
        );
    }

    private final String keyword;
    private final String startRule;
    private final String endRule;
    private final RuleFactory startExpressionRule;
    private final RuleFactory contentRule;
    private final RuleFactory endExpressionRule;
    private final ContentNodeFactory contentNodeFactory;
    private final KeywordRuleExpressionFactory keywordRuleExpressionFactory;

    public ContentNodeRuleExpression(String keyword, String startRule, String endRule, RuleFactory startExpressionRule, RuleFactory contentRule, RuleFactory endExpressionRule, ContentNodeFactory contentNodeFactory, KeywordRuleExpressionFactory keywordRuleExpressionFactory) {
        this.keyword = keyword;
        this.startRule = startRule;
        this.endRule = endRule;
        this.startExpressionRule = startExpressionRule;
        this.contentRule = contentRule;
        this.endExpressionRule = endExpressionRule;
        this.contentNodeFactory = contentNodeFactory;
        this.keywordRuleExpressionFactory = keywordRuleExpressionFactory;
    }

    @Override
    public Rule create(Grammar context) {
        return Rules.transform(
                Rules.sequence(
                        context.rule(startRule),
                        Rules.skipWhitespaces(keywordRuleExpressionFactory.create(keyword)),
                        Rules.skipWhitespacesAfter(startExpressionRule.create(context)),
                        Rules.mandatory(context.rule(endRule), "Code island not closed"),

                        contentRule.create(context),

                        Rules.mandatory(context.rule(startRule), "Missing endblock tag"),
                        Rules.skipWhitespaces(keywordRuleExpressionFactory.create(String.format("end%s", keyword))),
                        Rules.skipWhitespacesAfter(endExpressionRule.create(context)),
                        Rules.mandatory(context.rule(endRule), "Code island not closed")
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        return contentNodeFactory.create(
                                position(request),
                                input.get(2),
                                input.get(4),
                                input.get(7)
                        );
                    }
                })
        );
    }
}
