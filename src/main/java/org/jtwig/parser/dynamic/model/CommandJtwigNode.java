package org.jtwig.parser.dynamic.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jtwig.parser.dynamic.model.command.JtwigCommandDefinition;
import org.jtwig.parser.dynamic.model.control.TagWhiteSpaceControl;
import org.jtwig.parsing.model.Range;

public class CommandJtwigNode extends JtwigNode {
    private final TagWhiteSpaceControl whiteSpaceControl;
    private final JtwigCommandDefinition commandDefinition;

    public CommandJtwigNode(Range range, TagWhiteSpaceControl whiteSpaceControl, JtwigCommandDefinition commandDefinition) {
        super(range);
        this.whiteSpaceControl = whiteSpaceControl;
        this.commandDefinition = commandDefinition;
    }

    public TagWhiteSpaceControl getWhiteSpaceControl() {
        return whiteSpaceControl;
    }

    public JtwigCommandDefinition getCommandDefinition() {
        return commandDefinition;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
