package org.jtwig.parser.parsky.expression.constant;

import com.google.common.base.Function;
import org.jtwig.model.expression.constant.StringConstantExpression;
import org.jtwig.parser.parsky.ParskyContext;
import org.parsky.character.CharacterMatchers;
import org.parsky.sequence.FirstOfSequenceMatcher;
import org.parsky.sequence.SequenceMatcher;
import org.parsky.sequence.SequenceMatchers;
import org.parsky.sequence.transform.ListContentTransformation;
import org.parsky.sequence.transform.Transformations;

import java.util.Arrays;

public class StringConstantExpressionSequenceMatcher extends FirstOfSequenceMatcher<ParskyContext, StringConstantExpression> {
    public StringConstantExpressionSequenceMatcher() {
        super(Arrays.asList(
                stringWith('"'),
                stringWith('\'')

        ));
    }

    private static SequenceMatcher<ParskyContext, StringConstantExpression> stringWith(char character) {
        return SequenceMatchers.transform(
                SequenceMatchers.<ParskyContext, Object>sequence(
                        (SequenceMatcher) SequenceMatchers.<ParskyContext>match(CharacterMatchers.character(character)),
                        (SequenceMatcher) SequenceMatchers.matchedText(SequenceMatchers.until(SequenceMatchers.<ParskyContext, String>firstOf(
                                SequenceMatchers.<ParskyContext>match(CharacterMatchers.character(character)),
                                SequenceMatchers.<ParskyContext>match(CharacterMatchers.endOfInput())
                        ))),
                        (SequenceMatcher) SequenceMatchers.<ParskyContext>match(CharacterMatchers.character(character))
                ),
                Transformations.fromContentList(new Function<ListContentTransformation.Request<ParskyContext, Object>, StringConstantExpression>() {
                    @Override
                    public StringConstantExpression apply(ListContentTransformation.Request<ParskyContext, Object> input) {
                        return new StringConstantExpression(
                                input.getContext().position(input.getRange()),
                                input.get(1, String.class)
                        );
                    }
                })
        );
    }
}
