package org.jtwig.parser.parsky.expression;

import com.google.common.base.Function;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.parser.parsky.ParskyContext;
import org.parsky.character.CharacterMatchers;
import org.parsky.sequence.SequenceMatcher;
import org.parsky.sequence.SequenceMatchers;
import org.parsky.sequence.TransformSequenceMatcher;
import org.parsky.sequence.transform.ContentTransformation;
import org.parsky.sequence.transform.Transformations;

public class VariableExpressionSequenceMatcher extends TransformSequenceMatcher<ParskyContext, String, VariableExpression> {
    // [a-zA-Z_$][a-zA-Z0-9_$]*
    public VariableExpressionSequenceMatcher() {
        super(SequenceMatchers.matchedText(SequenceMatchers.sequence(
                (SequenceMatcher) SequenceMatchers.match(
                        CharacterMatchers.or(
                                CharacterMatchers.range('a', 'z'),
                                CharacterMatchers.range('A', 'Z'),
                                CharacterMatchers.anyOf("_$")
                        )
                ),
                SequenceMatchers.zeroOrMore(
                        SequenceMatchers.match(
                                CharacterMatchers.or(
                                        CharacterMatchers.range('a', 'z'),
                                        CharacterMatchers.range('A', 'Z'),
                                        CharacterMatchers.range('0', '9'),
                                        CharacterMatchers.anyOf("_$")
                                )
                        )
                )
        )), Transformations.fromString(new Function<ContentTransformation.Request<ParskyContext, String>, VariableExpression>() {
            @Override
            public VariableExpression apply(ContentTransformation.Request<ParskyContext, String> input) {
                return new VariableExpression(
                        input.getContext().position(input.getRange()),
                        input.getValue()
                );
            }
        }));
    }
}
