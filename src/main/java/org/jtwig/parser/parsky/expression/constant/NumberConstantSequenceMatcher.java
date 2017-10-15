package org.jtwig.parser.parsky.expression.constant;

import com.google.common.base.Function;
import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.jtwig.parser.parsky.ParskyContext;
import org.parsky.character.CharacterMatchers;
import org.parsky.sequence.SequenceMatcher;
import org.parsky.sequence.SequenceMatchers;
import org.parsky.sequence.TransformSequenceMatcher;
import org.parsky.sequence.transform.ContentTransformation;
import org.parsky.sequence.transform.Transformations;

import java.math.BigDecimal;

public class NumberConstantSequenceMatcher extends TransformSequenceMatcher<ParskyContext, String, NumberConstantExpression> {
    public NumberConstantSequenceMatcher() {
        // [0-9]+(?:\.[0-9]+)?
        super(SequenceMatchers.matchedText(SequenceMatchers.sequence(
                SequenceMatchers.oneOrMore(SequenceMatchers.match(CharacterMatchers.range('0', '9'))),
                SequenceMatchers.optional(SequenceMatchers.sequence(
                        (SequenceMatcher) SequenceMatchers.match(CharacterMatchers.character('.')),
                        SequenceMatchers.oneOrMore(SequenceMatchers.match(CharacterMatchers.range('0', '9')))
                ))
        )), Transformations.fromString("number", new Function<ContentTransformation.Request<ParskyContext, String>, NumberConstantExpression>() {
            @Override
            public NumberConstantExpression apply(ContentTransformation.Request<ParskyContext, String> input) {
                return new NumberConstantExpression(
                        input.getContext().position(input.getRange()),
                        new BigDecimal(input.getValue())
                );
            }
        }));
    }
}
