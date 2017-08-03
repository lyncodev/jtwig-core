package org.jtwig.parser.dynamic.model;

import org.jtwig.parser.dynamic.model.control.TagWhiteSpaceControl;
import org.jtwig.parser.dynamic.model.tag.JtwigEndTagDefinition;
import org.jtwig.parser.dynamic.model.tag.JtwigStartTagDefinition;

public class TagJtwigNode implements JtwigNode {
    private final TagWhiteSpaceControl startTag;
    private final JtwigNode content;
    private final JtwigStartTagDefinition startTagDefinition;
    private final JtwigEndTagDefinition endTagDefinition;
    private final TagWhiteSpaceControl endTag;

    public TagJtwigNode(TagWhiteSpaceControl startTag, JtwigNode content, JtwigStartTagDefinition startTagDefinition, JtwigEndTagDefinition endTagDefinition, TagWhiteSpaceControl endTag) {
        this.startTag = startTag;
        this.content = content;
        this.startTagDefinition = startTagDefinition;
        this.endTagDefinition = endTagDefinition;
        this.endTag = endTag;
    }

    public TagWhiteSpaceControl getStartTag() {
        return startTag;
    }

    public JtwigNode getContent() {
        return content;
    }

    public JtwigStartTagDefinition getStartTagDefinition() {
        return startTagDefinition;
    }

    public JtwigEndTagDefinition getEndTagDefinition() {
        return endTagDefinition;
    }

    public TagWhiteSpaceControl getEndTag() {
        return endTag;
    }
}
