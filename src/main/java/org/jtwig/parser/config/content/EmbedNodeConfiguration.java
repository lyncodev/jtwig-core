package org.jtwig.parser.config.content;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.collections.MapExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.EmbedNode;
import org.jtwig.model.tree.OverrideBlockNode;
import org.jtwig.model.tree.include.IncludeConfiguration;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.ParserContext;
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
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.jtwig.model.position.Position.position;

public class EmbedNodeConfiguration extends ContentNodeConfiguration {
    public static final String EMBED = "embed";
    public static final String ENDEMBED = "end" + EMBED;

    public EmbedNodeConfiguration() {
        super(EMBED, asList(EMBED, ENDEMBED), EmbedNode.class, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.sequence(
                        Rules.skipWhitespacesAfter(Rules.mandatory(context.rule(Expression.class), "Embed construct missing path expression")),
                        Rules.firstOf(
                                Rules.transform(
                                        Rules.sequence(
                                                Rules.skipWhitespacesAfter(Rules.<ParserContext>string("ignore")),
                                                Rules.skipWhitespaces(Rules.mandatory(Rules.<ParserContext>string("missing"), "Did you mean 'ignore missing'?"))
                                        ),
                                        Transforms.value(true)
                                ),
                                Rules.transform(Transforms.value(false))
                        ),
                        Rules.firstOf(
                                Rules.transform(
                                        Rules.sequence(
                                                Rules.skipWhitespacesAfter(Rules.string("with")),
                                                Rules.skipWhitespacesAfter(context.rule(Expression.class))
                                        ),
                                        Transforms.pick(1)
                                ),
                                Rules.transform(Transforms.list(new ListTransform.TransformList() {
                                    @Override
                                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                                        return Transform.Result.success(new MapExpression(
                                                position(request),
                                                Collections.<String, Expression>emptyMap()
                                        ));
                                    }
                                }))
                        ),
                        Rules.firstOf(
                                Rules.transform(
                                        Rules.skipWhitespacesAfter(Rules.<ParserContext>string("only")),
                                        Transforms.value(false)
                                ),
                                Rules.transform(Transforms.value(true))
                        )
                );
            }
        }, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.flatten(Rules.sequence(
                        Rules.zeroOrMore(Rules.skipWhitespaces(Rules.firstOf(
                                context.rule(OverrideBlockNode.class),
                                context.rule(JtwigGrammar.COMMENT)
                        ))),
                        Rules.test(Rules.firstOf(
                                Rules.sequence(
                                        Rules.skipWhitespaces(context.rule(JtwigGrammar.START_CODE)),
                                        Rules.mandatory(Rules.sequence(
                                                Rules.skipWhitespaces(Rules.string(ENDEMBED)),
                                                context.rule(JtwigGrammar.END_CODE)
                                        ), "Embed construct can only contain block elements")
                                ),
                                Rules.empty()
                        ))
                ));
            }
        }, RuleFactories.simple(Rules.empty()), new ContentNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object start, Object content, Object end) {
                List inputs = (List) start;
                List contents = (List) content;
                List<OverrideBlockNode> nodes = new ArrayList<>();
                for (Object item : contents) {
                    if (item instanceof OverrideBlockNode) {
                        nodes.add((OverrideBlockNode) item);
                    }
                }
                return Transform.Result.success(new EmbedNode(
                        position,
                        nodes,
                        new IncludeConfiguration(
                                (Expression) inputs.get(0),
                                (Expression) inputs.get(2),
                                (Boolean) inputs.get(3),
                                (Boolean) inputs.get(1)

                        )
                ));
            }
        });
    }
}
