package org.jtwig.parser.config.content;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.ForLoopNode;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.parsky.ParserContext;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.List;

public class ForLoopNodeConfiguration extends SimpleContentNodeConfiguration {
    public ForLoopNodeConfiguration() {
        super("for", ForLoopNode.class, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.sequence(
                        Rules.firstOf(
                                Rules.sequence(
                                        Rules.skipWhitespacesAfter(context.rule(VariableExpression.class)),
                                        Rules.skipWhitespacesAfter(Rules.<ParserContext>string(",")),
                                        Rules.mandatory(variable(context), "Expecting a second variable name in for loop. Example: {% for key, value in list %}")
                                ),
                                Rules.mandatory(variable(context), "Expecting a variable name in for loop")
                        ),
                        Rules.skipWhitespaces(Rules.mandatory(
                                Rules.<ParserContext>string("in"),
                                "Malformed for loop, missing 'in' keyword. For example: {% for i in list %}"
                        )),
                        Rules.mandatory(context.rule(Expression.class), "Expecting an expression in for loop")
                );
            }
        }, new SimpleContentNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object start, Node content) {
                List inputs = (List) start;
                if (inputs.get(0) instanceof List) {
                    return Transform.Result.success(new ForLoopNode(
                            position,
                            (VariableExpression) ((List) inputs.get(0)).get(0),
                            (VariableExpression) ((List) inputs.get(0)).get(2),
                            (Expression) inputs.get(2),
                            content
                    ));
                } else {
                    return Transform.Result.success(new ForLoopNode(
                            position,
                            (VariableExpression) inputs.get(0),
                            (Expression) inputs.get(2),
                            content
                    ));
                }
            }
        });
    }

    private static Rule variable(Grammar context) {
        return Rules.transform(Rules.sequence(
                Rules.test(Rules.not(Rules.string("in"))),
                context.rule(VariableExpression.class)
        ), Transforms.pick(1));
    }
}
