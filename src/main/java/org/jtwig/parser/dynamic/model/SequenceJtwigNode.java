package org.jtwig.parser.dynamic.model;

import java.util.List;

public class SequenceJtwigNode implements JtwigNode {
    private final List<JtwigNode> nodes;

    public SequenceJtwigNode(List<JtwigNode> nodes) {
        this.nodes = nodes;
    }

    public List<JtwigNode> getNodes() {
        return nodes;
    }
}
