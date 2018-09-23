package org.jtwig.parser.config.command;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.collections.MapExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.IncludeNode;
import org.jtwig.model.tree.include.IncludeConfiguration;
import org.jtwig.parser.parsky.ParserContext;
import org.jtwig.parser.parsky.tag.command.CommandNodeFactory;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.Collections;
import java.util.List;

import static org.jtwig.model.position.Position.position;

public class IncludeNodeConfiguration extends CommandNodeConfiguration {
    public IncludeNodeConfiguration() {
        super(IncludeNode.class, "include", new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.sequence(
                        Rules.skipWhitespacesAfter(Rules.mandatory(context.rule(Expression.class), "Include missing path expression")),
                        Rules.firstOf(Rules.transform(
                                Rules.sequence(
                                        Rules.skipWhitespacesAfter(Rules.<ParserContext>string("ignore")),
                                        Rules.skipWhitespaces(Rules.mandatory(Rules.<ParserContext>string("missing"), "Did you mean 'ignore missing'?"))
                                ),
                                Transforms.value(true)
                        ), Rules.transform(Transforms.value(false))),
                        Rules.firstOf(
                                Rules.transform(
                                        Rules.sequence(
                                                Rules.skipWhitespacesAfter(Rules.<ParserContext>string("with")),
                                                Rules.skipWhitespacesAfter(Rules.mandatory(context.rule(Expression.class), "Expecting map of values"))
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
        }, new CommandNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object input) {
                List inputs = (List) input;
                return Transform.Result.success(new IncludeNode(
                        position,
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
