package org.jtwig.parser.config.command;

import org.jtwig.model.position.Position;
import org.jtwig.model.tree.FlushNode;
import org.jtwig.parser.parsky.tag.command.CommandNodeFactory;
import org.parsky.grammar.RuleFactories;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

public class FlushNodeConfiguration extends CommandNodeConfiguration {
    public FlushNodeConfiguration() {
        super(FlushNode.class, "flush", RuleFactories.simple(Rules.empty()), new CommandNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object input) {
                return Transform.Result.success(new FlushNode(position));
            }
        });
    }
}
