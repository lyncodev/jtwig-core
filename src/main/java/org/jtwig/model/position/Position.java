package org.jtwig.model.position;

import org.apache.commons.lang3.tuple.Pair;
import org.jtwig.parser.parsky.ParserContext;
import org.jtwig.resource.reference.ResourceReference;
import org.parsky.engine.ParserRequest;
import org.parsky.error.ExcerptService;
import org.parsky.error.PositionService;

public class Position {
    private static final PositionService positionService = new PositionService();
    private static final ExcerptService excerptService = new ExcerptService();
    public static Position position (ParserRequest request) {
        return new Position(
                request.getContext().peek(ParserContext.class).get().getResourceReference(),
                request.getContent(),
                request.getOffset()
        );
    }

    private final ResourceReference resource;
    private final char[] content;
    private final int offset;

    public Position(ResourceReference resource, char[] content, int offset) {
        this.resource = resource;
        this.content = content;
        this.offset = offset;
    }

    public ResourceReference getResource() {
        return resource;
    }

    public char[] getContent() {
        return content;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        Pair<Integer, Integer> position = positionService.position(content, offset);
        return String.format("%s (Line: %d, Column: %d):\n%s",
                resource,
                position.getLeft(),
                position.getRight(),
                excerptService.excerpt(content, offset)
        );
    }
}
