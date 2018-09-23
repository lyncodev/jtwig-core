package org.jtwig.parser.config.content;

import com.google.common.base.Optional;
import org.jtwig.model.expression.constant.StringConstantExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.AutoEscapeNode;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.parsky.ParserContext;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

public class AutoEscapeNodeConfiguration extends SimpleContentNodeConfiguration {
    public AutoEscapeNodeConfiguration() {
        super("autoescape", AutoEscapeNode.class, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.firstOf(
                        Rules.transform(
                                context.rule(StringConstantExpression.class),
                                new Transform() {
                                    @Override
                                    public Result transform(ParserRequest request, Object input) {
                                        return Transform.Result.success(((StringConstantExpression) input).getConstantValue());
                                    }
                                }
                        ),
                        Rules.transform(
                                Rules.<ParserContext>string("false"),
                                Transforms.value("none")
                        ),
                        Rules.<ParserContext>empty()
                );
            }
        }, new SimpleContentNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object start, Node content) {
                return Transform.Result.success(new AutoEscapeNode(
                        position,
                        content,
                        Optional.fromNullable((String) start)
                ));
            }
        });
    }
}
