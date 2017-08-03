package org.jtwig.parser.dynamic.model.control;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TagWhiteSpaceControl {
    private final boolean trimBefore;
    private final boolean trimAfter;

    public TagWhiteSpaceControl(boolean trimBefore, boolean trimAfter) {
        this.trimBefore = trimBefore;
        this.trimAfter = trimAfter;
    }

    public boolean isTrimBefore() {
        return trimBefore;
    }

    public boolean isTrimAfter() {
        return trimAfter;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
