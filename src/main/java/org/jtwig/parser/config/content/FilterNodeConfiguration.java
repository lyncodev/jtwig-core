package org.jtwig.parser.config.content;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.operations.BinaryOperationExpression;
import org.jtwig.model.expression.operations.FunctionExpression;
import org.jtwig.model.expression.operations.InjectableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.FilterNode;
import org.jtwig.model.tree.Node;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

public class FilterNodeConfiguration extends SimpleContentNodeConfiguration {
    public FilterNodeConfiguration() {
        super("filter", FilterNode.class, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.firstOf(
                        context.rule(BinaryOperationExpression.class),
                        context.rule(FunctionExpression.class),
                        context.rule(VariableExpression.class)
                );
            }
        }, new SimpleContentNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object start, Node content) {
                return Transform.Result.success(new FilterNode(
                        position,
                        content,
                        (InjectableExpression) start
                ));
            }
        });
    }
}
