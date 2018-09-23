package org.jtwig.parser.config.content;

import com.google.common.base.Optional;
import org.jtwig.model.expression.constant.StringConstantExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.ContentEscapeNode;
import org.jtwig.model.tree.Node;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

public class ContentEscapeNodeConfiguration extends SimpleContentNodeConfiguration {
    public ContentEscapeNodeConfiguration() {
        super("contentescape", ContentEscapeNode.class, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.optional(context.rule(StringConstantExpression.class));
            }
        }, new SimpleContentNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object start, Node content) {
                return Transform.Result.success(new ContentEscapeNode(
                        position,
                        content,
                        start == null ? Optional.<String>absent() :
                                Optional.of(((StringConstantExpression) start).getConstantValue())
                ));
            }
        });
    }
}
