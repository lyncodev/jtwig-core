package org.jtwig.parser.parsky.position;

import org.jtwig.model.position.Position;
import org.jtwig.resource.reference.ResourceReference;
import org.parsky.sequence.model.Range;

public class PositionFactory {
    private final IsNewLineService isNewLineService;
    private final ResourceReference reference;
    private final char[] content;

    public PositionFactory(IsNewLineService isNewLineService, ResourceReference reference, char[] content) {
        this.isNewLineService = isNewLineService;
        this.reference = reference;
        this.content = content;
    }

    public Position create (Range range) {
        int lineCounter = 0;
        int columnCounter = 0;
        for (int i = 0; i < content.length && i < range.getStartOffset(); i++) {
            if (isNewLineService.isNewLine(content, i)) {
                lineCounter++;
                columnCounter = 0;
            } else {
                columnCounter++;
            }
        }
        return new Position(
                reference,
                lineCounter,
                columnCounter
        );
    }
}
