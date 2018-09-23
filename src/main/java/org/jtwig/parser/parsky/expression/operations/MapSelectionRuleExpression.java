package org.jtwig.parser.parsky.expression.operations;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operations.MapSelectionExpression;
import org.jtwig.model.position.Position;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.recursive.DependentRuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.Iterator;
import java.util.List;

import static org.jtwig.model.position.Position.position;

public class MapSelectionRuleExpression implements DependentRuleFactory {
    public static RuleFactory asRuleFactory (final RuleFactory factory) {
        return new RuleFactory() {
            @Override
            public Rule create(Grammar grammar) {
                return new MapSelectionRuleExpression().create(
                        factory.create(grammar)
                        , grammar
                );
            }
        };
    }

    @Override
    public Rule create(Rule rule, Grammar grammar) {
        return Rules.transform(
                Rules.sequence(
                        Rules.skipWhitespaces(rule),
                        Rules.oneOrMore(
                                Rules.transform(
                                        Rules.sequence(
                                                Rules.skipWhitespaces(Rules.string("[")),
                                                grammar.rule(Expression.class),
                                                Rules.skipWhitespaces(Rules.string("]"))
                                        ),
                                        Transforms.pick(1)
                                )
                        )
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        Position position = position(request);
                        Iterator<Expression> list = input.get(1, List.class).iterator();

                        MapSelectionExpression result = new MapSelectionExpression(
                                position,
                                input.get(0, Expression.class),
                                list.next()
                        );

                        while (list.hasNext()) {
                            result = new MapSelectionExpression(
                                    position,
                                    result,
                                    list.next()
                            );
                        }
                        return Transform.Result.success(result);
                    }
                })
        );
    }
}
