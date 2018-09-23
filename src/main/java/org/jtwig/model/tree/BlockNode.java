package org.jtwig.model.tree;

import org.jtwig.model.position.Position;

public class BlockNode extends ContentNode {
    private final String blockIdentifier;

    public BlockNode(Position position, String blockIdentifier, Node content) {
        super(position, content);
        this.blockIdentifier = blockIdentifier;
    }

    public String getIdentifier() {
        return blockIdentifier;
    }
}
