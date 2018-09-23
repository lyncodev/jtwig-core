package org.jtwig.parser.parsky.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.expression.collections.ComprehensionListExpression;
import org.jtwig.model.expression.collections.EnumeratedListExpression;
import org.jtwig.model.expression.collections.MapExpression;
import org.jtwig.model.expression.constant.*;
import org.jtwig.model.expression.operations.FunctionExpression;
import org.jtwig.model.expression.operations.MapSelectionExpression;
import org.jtwig.model.expression.operations.test.*;
import org.jtwig.parser.config.JtwigParserConfiguration;
import org.jtwig.parser.parsky.basic.IdentifierRuleExpression;
import org.jtwig.parser.parsky.basic.InitialIdentifierRuleExpression;
import org.jtwig.parser.parsky.basic.KeywordRuleExpression;
import org.jtwig.parser.parsky.expression.basic.PrimaryRuleExpression;
import org.jtwig.parser.parsky.expression.basic.VariableRuleExpression;
import org.jtwig.parser.parsky.expression.operations.SelectionRuleExpression;
import org.jtwig.parser.parsky.expression.collections.ComprehensionListRuleExpression;
import org.jtwig.parser.parsky.expression.collections.EnumerationListRuleExpression;
import org.jtwig.parser.parsky.expression.collections.MapRuleExpression;
import org.jtwig.parser.parsky.expression.constant.BooleanRuleExpression;
import org.jtwig.parser.parsky.expression.constant.NullRuleExpression;
import org.jtwig.parser.parsky.expression.constant.NumberRuleExpression;
import org.jtwig.parser.parsky.expression.constant.StringRuleExpression;
import org.jtwig.parser.parsky.expression.operations.*;
import org.jtwig.parser.parsky.expression.test.*;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.GrammarBuilder;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.recursive.RecursiveRuleFactory;
import org.parsky.grammar.rules.Rule;

import java.util.Arrays;

import static org.parsky.grammar.RuleFactories.anyOf;
import static org.parsky.grammar.RuleFactories.reference;

public class JtwigExpressionGrammar {
    public static final String IDENTIFIER = "Identifier";
    public static final String PRIMARY = "primary";
    public static final String KEYWORD = "Keyword";
    public static final String INITIAL_IDENTIFIER = "InitialIdentifier";
    public static final String PARENTHESIS_EXPRESSION = "parenthesisExpression";

    public void populate(GrammarBuilder builder, JtwigParserConfiguration configuration) {
        builder
                .define(INITIAL_IDENTIFIER, new InitialIdentifierRuleExpression())
                .define(KEYWORD, new KeywordRuleExpression())
                .define(IDENTIFIER, new IdentifierRuleExpression())

                .define(NullConstantExpression.class, new NullRuleExpression())
                .define(BooleanConstantExpression.class, new BooleanRuleExpression())
                .define(NumberConstantExpression.class, new NumberRuleExpression())
                .define(StringConstantExpression.class, new StringRuleExpression())
                .define(ConstantExpression.class, anyOf(NullConstantExpression.class, BooleanConstantExpression.class, NumberConstantExpression.class, StringConstantExpression.class))
                .define(VariableExpression.class, new VariableRuleExpression())
                .define(ComprehensionListExpression.class, new ComprehensionListRuleExpression())
                .define(EnumeratedListExpression.class, new EnumerationListRuleExpression())
                .define(MapExpression.class, new MapRuleExpression())
                .define(FunctionExpression.class, new FunctionRuleExpression())
                .define(PRIMARY, anyOf(
                        MapExpression.class,
                        FunctionExpression.class,
                        VariableExpression.class,
                        EnumeratedListExpression.class,
                        ComprehensionListExpression.class,
                        ConstantExpression.class
                ))
                .define(MapSelectionExpression.class, MapSelectionRuleExpression.asRuleFactory(reference(PRIMARY)))
                .define(PARENTHESIS_EXPRESSION, new PrimaryRuleExpression(anyOf(MapSelectionExpression.class, PRIMARY)))

                .define(TestExpression.class, anyOf(SameAsTestExpression.class, DivisibleByTestExpression.class, DefinedTestExpression.class, IsFunctionTestExpression.class, NullTestExpression.class, FunctionTestExpression.class))
                .define(DefinedTestExpression.class, new DefinedTestRuleExpression())
                .define(IsFunctionTestExpression.class, new IsFunctionTestRuleExpression())
                .define(NullTestExpression.class, new IsNullTestRuleExpression())
                .define(FunctionTestExpression.class, new FunctionTestRuleExpression())
                .define(DivisibleByTestExpression.class, new DivisibleTestRuleExpression())
                .define(SameAsTestExpression.class, new SameAsTestRuleExpression())

                .define(Expression.class, anyOf(new OneArgumentFunctionRuleExpression(reference(ConstantExpression.class)), RecursiveRuleFactory.create(
                        reference(PARENTHESIS_EXPRESSION),
                        Arrays.asList(
                                new SelectionRuleExpression(),
                                new MapSelectionRuleExpression(),
                                new UnaryOperationRuleExpression(configuration.getUnaryOperators()),
                                new BinaryOperationRuleExpression(configuration.getBinaryOperators(), new BinaryOperatorSequenceMatcherFactory()),
                                new MapSelectionRuleExpression(),
                                new TernaryOperationRuleExpression(),
                                new TestOperationRuleExpression()
                        )
                )));
    }
}
