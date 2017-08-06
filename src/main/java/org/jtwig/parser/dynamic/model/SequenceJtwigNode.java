package org.jtwig.parser.dynamic.model;

import org.jtwig.parser.dynamic.model.position.Position;

import java.util.List;

public class SequenceJtwigNode extends JtwigNode {
    private final List<JtwigNode> nodes;

    public SequenceJtwigNode(Position position, List<JtwigNode> nodes) {
        super(position);
        this.nodes = nodes;
    }

    public List<JtwigNode> getNodes() {
        return nodes;
    }
}
