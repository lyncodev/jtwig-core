package org.jtwig.parser.dynamic.model;

import org.jtwig.parser.dynamic.model.position.Position;
import org.jtwig.parser.dynamic.model.position.WithPosition;

public abstract class JtwigNode implements WithPosition {
    private final Position position;

    public JtwigNode(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
