package org.jtwig.parser.parsky;

import org.jtwig.model.position.Position;
import org.jtwig.parser.parsky.position.PositionFactory;
import org.parsky.sequence.model.Range;

public class ParskyContext {
    private final PositionFactory positionFactory;

    public ParskyContext(PositionFactory positionFactory) {
        this.positionFactory = positionFactory;
    }

    public Position position (Range range) {
        return positionFactory.create(range);
    }
}
