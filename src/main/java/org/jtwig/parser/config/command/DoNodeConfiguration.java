package org.jtwig.parser.config.command;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.DoNode;
import org.jtwig.parser.parsky.tag.command.CommandNodeFactory;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.transform.Transform;

public class DoNodeConfiguration extends CommandNodeConfiguration {
    public DoNodeConfiguration() {
        super(DoNode.class, "do", new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return context.rule(Expression.class);
            }
        }, new CommandNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object input) {
                return Transform.Result.success(new DoNode(
                        position,
                        (Expression) input
                ));
            }
        });
    }
}
