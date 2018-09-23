package org.jtwig.parser.parsky.expression.operations;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operations.UnaryOperationExpression;
import org.jtwig.model.position.Position;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactories;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.prefix.PrefixCombiner;
import org.parsky.grammar.prefix.PrefixConfiguration;
import org.parsky.grammar.prefix.PrefixRuleConfiguration;
import org.parsky.grammar.prefix.PrefixRuleFactory;
import org.parsky.grammar.recursive.DependentRuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.List;

import static org.parsky.grammar.rules.Rules.*;


public class UnaryOperationRuleExpression implements DependentRuleFactory {
    private final List<UnaryOperator> operators;

    public UnaryOperationRuleExpression(List<UnaryOperator> operators) {
        this.operators = operators;
    }

    @Override
    public Rule create(Rule rule, final Grammar grammar) {
        return PrefixRuleFactory.prefix(new PrefixConfiguration(
                RuleFactories.simple(rule),
                Collections2.transform(operators, new Function<UnaryOperator, PrefixRuleConfiguration>() {
                    @Override
                    public PrefixRuleConfiguration apply(UnaryOperator input) {
                        return new PrefixRuleConfiguration(
                                input.precedence(),
                                RuleFactories.simple(rule(input, grammar))
                        );
                    }
                }),
                new PrefixCombiner() {
                    @Override
                    public Transform.Result combine(ParserRequest request, Object operator, Object element) {
                        return Transform.Result.success(new UnaryOperationExpression(
                                Position.position(request),
                                (UnaryOperator) operator,
                                (Expression) element
                        ));
                    }
                }
        )).create(grammar);
    }

    private Rule rule(UnaryOperator operator, Grammar grammar) {
        if (endsWithNonSymbol(operator.symbol())) {
            return transform(
                    sequence(
                            string(operator.symbol()),
                            test(not(grammar.rule(JtwigExpressionGrammar.INITIAL_IDENTIFIER)))
                    ),
                    Transforms.value(operator)
            );
        } else {
            return transform(
                    string(operator.symbol()),
                    Transforms.value(operator)
            );
        }
    }

    boolean endsWithNonSymbol(String symbol) {
        return symbol.matches(".*[a-zA-Z0-9_\\$]$");
    }
}
