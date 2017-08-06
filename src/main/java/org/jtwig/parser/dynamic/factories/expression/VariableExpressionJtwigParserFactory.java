package org.jtwig.parser.dynamic.factories.expression;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.expression.JtwigVariable;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.FromStringTransformation;
import org.jtwig.parsing.transform.Transformations;

import static org.jtwig.parsing.character.CharacterMatchers.*;
import static org.jtwig.parsing.character.CharacterMatchers.or;
import static org.jtwig.parsing.sequence.SequenceMatchers.*;

public class VariableExpressionJtwigParserFactory implements ExpressionJtwigParserFactory<JtwigVariable> {
    @Override
    public TransformSequenceMatcher<JtwigVariable> create(ParserConfiguration configuration) {
        return transform(
                flatten(sequence(
                        match(
                                or(
                                        range('a', 'z'),
                                        range('A', 'Z'),
                                        anyOf("_$")
                                )
                        ),
                        zeroOrMore(
                                match(
                                        or(
                                                range('a', 'z'),
                                                range('A', 'Z'),
                                                range('0', '9'),
                                                anyOf("_$")
                                        )
                                )
                        )
                )),
                Transformations.fromString(new Function<FromStringTransformation.Request, JtwigVariable>() {
                    @Override
                    public JtwigVariable apply(FromStringTransformation.Request input) {
                        return new JtwigVariable(input.getResult().getRange(), input.getInput());
                    }
                })
        );
    }
}
