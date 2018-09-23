package org.jtwig.parser.parsky.expression.collections;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.collections.MapExpression;
import org.jtwig.model.expression.constant.ConstantExpression;
import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.jtwig.model.expression.constant.StringConstantExpression;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.jtwig.model.position.Position.position;

public class MapRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar context) {
        return Rules.transform(
                Rules.list(
                        entry(context),
                        Rules.string("{"),
                        Rules.string(","),
                        Rules.mandatory(Rules.string("}"), "Missing end curly bracket")
                ),
                new Transform() {
                    @Override
                    public Result transform(ParserRequest request, Object input) {
                        Map<String, Expression> map = new LinkedHashMap<>();
                        for (Pair<String, Expression> pair : ((List<Pair<String, Expression>>) input)) {
                            map.put(pair.getKey(), pair.getValue());
                        }
                        return Transform.Result.success(new MapExpression(
                                position(request),
                                map
                        ));
                    }
                }
        );
    }

    private Rule entry(Grammar container) {
        return Rules.transform(
                Rules.sequence(
                        Rules.firstOf(
                                Rules.transform(
                                        container.rule(NumberConstantExpression.class),
                                        new Transform() {
                                            @Override
                                            public Result transform(ParserRequest request, Object input) {
                                                return Transform.Result.success(((ConstantExpression)input).getConstantValue().toString());
                                            }
                                        }
                                ),
                                Rules.transform(
                                        container.rule(StringConstantExpression.class),
                                        new Transform() {
                                            @Override
                                            public Result transform(ParserRequest request, Object input) {
                                                return Transform.Result.success(((ConstantExpression)input).getConstantValue().toString());
                                            }
                                        }
                                ),
                                container.rule(JtwigExpressionGrammar.IDENTIFIER)
                        ),
                        Rules.skipWhitespaces(Rules.string(":")),
                        container.rule(Expression.class)
                ),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        return Transform.Result.success(new ImmutablePair<>(
                                input.get(0, String.class),
                                input.get(2, Expression.class)
                        ));
                    }
                })
        );
    }
}
