package org.jtwig.parser.parsky.expression.collections;

import org.jtwig.model.expression.collections.ComprehensionListExpression;
import org.jtwig.model.expression.constant.ConstantExpression;
import org.jtwig.parser.parsky.ParserContext;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import static org.jtwig.model.position.Position.position;

public class ComprehensionListRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar context) {
        return Rules.firstOf(
                Rules.transform(
                        Rules.sequence(
                                Rules.skipWhitespacesAfter(Rules.string("[")),
                                context.rule(ConstantExpression.class),
                                Rules.string(".."),
                                Rules.skipWhitespacesAfter(context.rule(ConstantExpression.class)),
                                Rules.mandatory(Rules.string("]"), "Missing end bracket")
                        ),
                        Transforms.list(new ListTransform.TransformList() {
                            @Override
                            public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                                return Transform.Result.success(new ComprehensionListExpression(
                                        position(request),
                                        input.get(1, ConstantExpression.class),
                                        input.get(3, ConstantExpression.class)
                                ));
                            }
                        })
                ), Rules.transform(
                        Rules.sequence(
                                context.rule(ConstantExpression.class),
                                Rules.string(".."),
                                context.rule(ConstantExpression.class)
                        ),
                        Transforms.list(new ListTransform.TransformList() {
                            @Override
                            public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                                return Transform.Result.success(new ComprehensionListExpression(
                                        position(request),
                                        input.get(0, ConstantExpression.class),
                                        input.get(2, ConstantExpression.class)
                                ));
                            }
                        })
                )
        );
    }
}
