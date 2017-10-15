package org.jtwig.parser.parsky.expression.constant;

import com.google.common.base.Function;
import org.jtwig.model.expression.constant.BooleanConstantExpression;
import org.jtwig.parser.parsky.ParskyContext;
import org.parsky.sequence.SequenceMatchers;
import org.parsky.sequence.TransformSequenceMatcher;
import org.parsky.sequence.transform.ContentTransformation;
import org.parsky.sequence.transform.Transformations;

public class BooleanConstantExpressionSequenceMatcher extends TransformSequenceMatcher<ParskyContext, Boolean, BooleanConstantExpression> {
    public BooleanConstantExpressionSequenceMatcher() {
        super(SequenceMatchers.firstOf(
                SequenceMatchers.transform(SequenceMatchers.<ParskyContext>string("true"), Transformations.<ParskyContext, String, Boolean>constant(true)),
                SequenceMatchers.transform(SequenceMatchers.<ParskyContext>string("false"), Transformations.<ParskyContext, String, Boolean>constant(false))
        ), Transformations.from(new Function<ContentTransformation.Request<ParskyContext, Boolean>, BooleanConstantExpression>() {
            @Override
            public BooleanConstantExpression apply(ContentTransformation.Request<ParskyContext, Boolean> input) {
                return new BooleanConstantExpression(
                        input.getContext().position(input.getRange()),
                        input.getValue()
                );
            }
        }));
    }
}
