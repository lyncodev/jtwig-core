package org.jtwig.parser.config.command;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.SetNode;
import org.jtwig.parser.parsky.tag.command.CommandNodeFactory;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import java.util.List;

public class SetNodeConfiguration extends CommandNodeConfiguration {
    public SetNodeConfiguration() {
        super(SetNode.class, "set", new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.sequence(
                        Rules.mandatory(context.rule(VariableExpression.class), "Expecting variable name"),
                        Rules.skipWhitespaces(Rules.mandatory(Rules.string("="), "Expecting attribution operation '='")),
                        Rules.mandatory(context.rule(Expression.class), "Expecting assignment expression")
                );
            }
        }, new CommandNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object input) {
                List expressions = (List) input;
                return Transform.Result.success(new SetNode(
                        position,
                        (VariableExpression) expressions.get(0),
                        (Expression) expressions.get(2)
                ));
            }
        });
    }
}
