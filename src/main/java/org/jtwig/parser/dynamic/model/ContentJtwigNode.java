package org.jtwig.parser.dynamic.model;

import org.jtwig.parsing.model.Range;

public class ContentJtwigNode extends JtwigNode {
    private final String content;

    public ContentJtwigNode(Range range, String content) {
        super(range);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
