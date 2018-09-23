package org.jtwig.parser.config.content;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.constant.BooleanConstantExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.CompositeNode;
import org.jtwig.model.tree.IfNode;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.basic.KeywordRuleExpressionFactory;
import org.jtwig.parser.parsky.tag.content.ContentNodeFactory;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactories;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.jtwig.model.position.Position.position;

public class IfNodeConfiguration extends ContentNodeConfiguration {
    private static final KeywordRuleExpressionFactory keywordRuleExpressionFactory = new KeywordRuleExpressionFactory();

    public IfNodeConfiguration() {
        super("if", asList("if", "elseif", "else", "endif"), IfNode.class, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.mandatory(context.rule(Expression.class), "Expecting conditional expression");
            }
        }, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.flatten(
                        Rules.sequence(
                                context.rule(CompositeNode.class),
                                Rules.zeroOrMore(
                                        elseIfRule(context)
                                ),
                                Rules.optional(elseRule(context))
                        )
                );
            }
        }, RuleFactories.simple(Rules.empty()), new ContentNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object start, Object content, Object end) {
                ArrayList<IfNode.IfConditionNode> conditions = new ArrayList<>();
                List contents = (List) content;

                conditions.add(new IfNode.IfConditionNode(
                        position,
                        (Expression) start,
                        (Node) contents.get(0)
                ));

                for (int i = 1; i < contents.size(); i++) {
                    if (contents.get(i) != null) {
                        conditions.add((IfNode.IfConditionNode) contents.get(i));
                    }
                }

                return Transform.Result.success(new IfNode(
                        position,
                        conditions
                ));
            }
        });
    }

    private static Rule elseIfRule(Grammar context) {
        return Rules.transform(
                Rules.sequence(
                        context.rule(JtwigGrammar.START_CODE),
                        Rules.skipWhitespaces(keywordRuleExpressionFactory.create("elseif")),
                        Rules.skipWhitespacesAfter(Rules.mandatory(context.rule(Expression.class), "Expecting else if conditional expression")),
                        Rules.mandatory(context.rule(JtwigGrammar.END_CODE), "Code island not closed"),
                        context.rule(CompositeNode.class)
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        return Transform.Result.success(new IfNode.IfConditionNode(
                                position(request),
                                input.get(2, Expression.class),
                                input.get(4, Node.class)
                        ));
                    }
                })
        );
    }

    private static Rule elseRule(Grammar context) {
        return Rules.transform(
                Rules.sequence(
                        context.rule(JtwigGrammar.START_CODE),
                        Rules.skipWhitespaces(keywordRuleExpressionFactory.create("else")),
                        Rules.mandatory(context.rule(JtwigGrammar.END_CODE), "Expecting ending of else block"),
                        context.rule(CompositeNode.class)
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        Position position = position(request);
                        return Transform.Result.success(new IfNode.IfConditionNode(
                                position,
                                new BooleanConstantExpression(position, true),
                                input.get(3, Node.class)
                        ));
                    }
                })
        );
    }
}
