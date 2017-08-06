package org.jtwig.parser.dynamic.factories.command;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.factories.expression.ExpressionJtwigParserFactory;
import org.jtwig.parser.dynamic.factories.expression.VariableExpressionJtwigParserFactory;
import org.jtwig.parser.dynamic.model.command.SetJtwigCommandDefinition;
import org.jtwig.parser.dynamic.model.expression.JtwigExpression;
import org.jtwig.parser.dynamic.model.expression.JtwigVariable;
import org.jtwig.parsing.sequence.SequenceMatchers;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.ListTransformationRequest;
import org.jtwig.parsing.transform.Transformations;

public class SetCommandDefinitionJtwigParserFactory implements CommandDefinitionJtwigParserFactory<SetJtwigCommandDefinition> {
    private final ExpressionJtwigParserFactory expressionJtwigParserFactory;
    private final VariableExpressionJtwigParserFactory variableExpressionJtwigParserFactory;

    public SetCommandDefinitionJtwigParserFactory(ExpressionJtwigParserFactory expressionJtwigParserFactory, VariableExpressionJtwigParserFactory variableExpressionJtwigParserFactory) {
        this.expressionJtwigParserFactory = expressionJtwigParserFactory;
        this.variableExpressionJtwigParserFactory = variableExpressionJtwigParserFactory;
    }

    @Override
    public TransformSequenceMatcher<SetJtwigCommandDefinition> create(ParserConfiguration parserConfiguration) {
        return SequenceMatchers.transform(
                SequenceMatchers.sequence(
                        SequenceMatchers.string("set"),
                        SequenceMatchers.skipWhitespaces(variableExpressionJtwigParserFactory.create(parserConfiguration)),
                        SequenceMatchers.string("="),
                        SequenceMatchers.skipWhitespaces(expressionJtwigParserFactory.create(parserConfiguration))
                ),
                Transformations.fromContentList(new Function<ListTransformationRequest, SetJtwigCommandDefinition>() {
                    @Override
                    public SetJtwigCommandDefinition apply(ListTransformationRequest input) {
                        return new SetJtwigCommandDefinition(
                                input.get(0, JtwigVariable.class),
                                input.get(1, JtwigExpression.class)
                        );
                    }
                })
        );
    }
}
