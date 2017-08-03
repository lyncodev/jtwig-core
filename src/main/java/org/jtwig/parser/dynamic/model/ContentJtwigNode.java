package org.jtwig.parser.dynamic.model;

public class ContentJtwigNode implements JtwigNode {
    private final String content;

    public ContentJtwigNode(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
