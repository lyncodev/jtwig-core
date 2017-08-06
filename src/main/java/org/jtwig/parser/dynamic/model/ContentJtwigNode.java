package org.jtwig.parser.dynamic.model;

import org.jtwig.parser.dynamic.model.position.Position;

public class ContentJtwigNode extends JtwigNode {
    private final String content;

    public ContentJtwigNode(Position position, String content) {
        super(position);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
