package org.jtwig.parser.dynamic.factories.expression;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.expression.JtwigVariable;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.Transformations;

import static org.jtwig.parsing.character.CharacterMatchers.anyOf;
import static org.jtwig.parsing.character.CharacterMatchers.or;
import static org.jtwig.parsing.character.CharacterMatchers.range;
import static org.jtwig.parsing.sequence.SequenceMatchers.flatten;
import static org.jtwig.parsing.sequence.SequenceMatchers.match;
import static org.jtwig.parsing.sequence.SequenceMatchers.sequence;
import static org.jtwig.parsing.sequence.SequenceMatchers.transform;
import static org.jtwig.parsing.sequence.SequenceMatchers.zeroOrMore;

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
                Transformations.fromString(new Function<String, JtwigVariable>() {
                    @Override
                    public JtwigVariable apply(String input) {
                        return new JtwigVariable(input);
                    }
                })
        );
    }
}
