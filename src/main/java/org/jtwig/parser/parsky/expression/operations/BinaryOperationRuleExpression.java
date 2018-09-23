package org.jtwig.parser.parsky.expression.operations;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.operations.BinaryOperationExpression;
import org.jtwig.model.position.Position;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactories;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.infix.InfixCombiner;
import org.parsky.grammar.infix.InfixConfiguration;
import org.parsky.grammar.infix.InfixRuleConfiguration;
import org.parsky.grammar.infix.InfixRuleFactory;
import org.parsky.grammar.recursive.DependentRuleFactory;
import org.parsky.grammar.rules.ReferenceRule;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import java.util.ArrayList;
import java.util.List;

public class BinaryOperationRuleExpression implements DependentRuleFactory {
    private final List<BinaryOperator> binaryOperators;
    private final BinaryOperatorSequenceMatcherFactory binaryOperatorSequenceMatcherFactory;

    public BinaryOperationRuleExpression(List<BinaryOperator> binaryOperators, BinaryOperatorSequenceMatcherFactory binaryOperatorSequenceMatcherFactory) {
        this.binaryOperators = binaryOperators;
        this.binaryOperatorSequenceMatcherFactory = binaryOperatorSequenceMatcherFactory;
    }

    @Override
    public Rule create(Rule rule, Grammar context) {
        final List<InfixRuleConfiguration> infixes = new ArrayList<>();
        for (BinaryOperator binaryOperator : binaryOperators) {
            infixes.add(InfixRuleConfiguration.infixRule(
                    binaryOperator.precedence(),
                    RuleFactories.simple(Rules.skipWhitespaces(binaryOperatorSequenceMatcherFactory.create(binaryOperator)))
            ));
        }
        return ReferenceRule.create(
                BinaryOperationExpression.class.getSimpleName(),
                new InfixRuleFactory(InfixConfiguration.foldLeft(
                        "binaryExpression",
                        RuleFactories.simple(Rules.skipWhitespaces(rule)),
                        infixes,
                        new InfixCombiner() {
                            @Override
                            public Transform.Result combine(ParserRequest request, Object leftOperand, Object operator, Object rightOperand) {
                                return Transform.Result.success(new BinaryOperationExpression(
                                        Position.position(request),
                                        (Expression) leftOperand,
                                        (BinaryOperator) operator,
                                        (Expression) rightOperand
                                ));
                            }
                        },
                        false
                )).create(context)
        );
    }
}
