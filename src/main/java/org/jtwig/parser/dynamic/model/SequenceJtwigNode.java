package org.jtwig.parser.dynamic.model;

import org.jtwig.parsing.model.Range;

import java.util.List;

public class SequenceJtwigNode extends JtwigNode {
    private final List<JtwigNode> nodes;

    public SequenceJtwigNode(Range range, List<JtwigNode> nodes) {
        super(range);
        this.nodes = nodes;
    }

    public List<JtwigNode> getNodes() {
        return nodes;
    }
}
