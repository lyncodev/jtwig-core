package org.jtwig.model.tree;

import org.jtwig.model.position.Position;
import org.jtwig.model.tree.visitor.NodeVisitor;

import java.util.Collection;
import java.util.List;

public class CompositeNode extends Node {
    private final List<Node> nodes;

    public CompositeNode(Position position, List<Node> nodes) {
        super(position);
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public void visit(NodeVisitor nodeConsumer) {
        super.visit(nodeConsumer);
        for (Node node : nodes) {
            node.visit(nodeConsumer);
        }
    }
}
