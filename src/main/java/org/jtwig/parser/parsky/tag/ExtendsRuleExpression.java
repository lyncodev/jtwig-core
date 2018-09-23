package org.jtwig.parser.parsky.tag;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.*;
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

import java.util.ArrayList;
import java.util.List;

import static org.jtwig.model.position.Position.position;

public class ExtendsRuleExpression implements RuleFactory {
    private final KeywordRuleExpressionFactory keywordRuleExpressionFactory;

    public ExtendsRuleExpression(KeywordRuleExpressionFactory keywordRuleExpressionFactory) {
        this.keywordRuleExpressionFactory = keywordRuleExpressionFactory;
    }

    @Override
    public Rule create(Grammar context) {
        return Rules.transform(
                Rules.sequence(
                        context.rule(JtwigGrammar.START_CODE),
                        Rules.skipWhitespaces(keywordRuleExpressionFactory.create("extends")),
                        Rules.skipWhitespaces(Rules.mandatory(context.rule(Expression.class), "Extends construct missing path expression")),
                        Rules.skipWhitespaces(Rules.mandatory(context.rule(JtwigGrammar.END_CODE), "Code island not closed")),

                        Rules.skipWhitespaces(Rules.zeroOrMore(
                                Rules.firstOf(
                                        Rules.skipWhitespaces(context.rule(OverrideBlockNode.class)),
                                        Rules.skipWhitespaces(context.rule(SetNode.class)),
                                        Rules.skipWhitespaces(context.rule(ImportNode.class)),
                                        Rules.skipWhitespaces(context.rule(JtwigGrammar.COMMENT))
                                )
                        )),
                        Rules.zeroOrMore(Rules.whitespace()),
                        Rules.mandatory(
                                Rules.endOfInput(),
                                "Extends templates only allows block, set and import constructs"
                        )
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        List<Node> nodes = new ArrayList<>();
                        List values = input.get(4, List.class);

                        for (Object value : values) {
                            if (value != null && (value instanceof Node)) {
                                nodes.add((Node) value);
                            }
                        }
                        return Transform.Result.success(new ExtendsNode(
                                position(request),
                                input.get(2, Expression.class),
                                nodes
                        ));
                    }
                })
        );
    }
}
