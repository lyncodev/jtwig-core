package org.jtwig.parser.parsky.expression.constant;

import com.google.common.base.Function;
import org.jtwig.model.expression.constant.NullConstantExpression;
import org.jtwig.parser.parsky.ParskyContext;
import org.parsky.sequence.SequenceMatchers;
import org.parsky.sequence.TransformSequenceMatcher;
import org.parsky.sequence.transform.ContentTransformation;
import org.parsky.sequence.transform.Transformations;

public class NullConstantExpressionSequenceMatcher extends TransformSequenceMatcher<ParskyContext, String, NullConstantExpression> {
    public NullConstantExpressionSequenceMatcher() {
        super(SequenceMatchers.<ParskyContext>string("null"), Transformations.from(new Function<ContentTransformation.Request<ParskyContext, String>, NullConstantExpression>() {
            @Override
            public NullConstantExpression apply(ContentTransformation.Request<ParskyContext, String> input) {
                return new NullConstantExpression(input.getContext().position(input.getRange()));
            }
        }));
    }
}
