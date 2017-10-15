package org.jtwig.parser.parsky;

import org.jtwig.parser.parsky.position.IsNewLineService;
import org.jtwig.parser.parsky.position.PositionFactory;
import org.jtwig.resource.reference.ResourceReference;
import org.parsky.sequence.SequenceMatcher;
import org.parsky.sequence.model.SequenceMatcherRequest;
import org.parsky.sequence.model.SequenceMatcherResult;

import static org.mockito.Mockito.mock;

public class ParskyTestUtils {
    public static <T> T parse (String content, SequenceMatcher<ParskyContext, T> sequenceMatcher) {
        return match(content, sequenceMatcher).getMatchResult().getValue();
    }

    public static <T> SequenceMatcherResult<T> match (String content, SequenceMatcher<ParskyContext, T> sequenceMatcher) {
        char[] charArray = content.toCharArray();
        return sequenceMatcher.matches(new SequenceMatcherRequest<>(
                charArray,
                0,
                new ParskyContext(new PositionFactory(new IsNewLineService(), mock(ResourceReference.class), charArray))
        ));
    }
}
