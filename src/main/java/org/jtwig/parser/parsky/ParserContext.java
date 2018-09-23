package org.jtwig.parser.parsky;

import com.google.common.base.Optional;
import org.jtwig.model.tree.TextNode;
import org.jtwig.resource.reference.ResourceReference;

import java.util.concurrent.atomic.AtomicReference;

public class ParserContext {
    private final ResourceReference resourceReference;
    private final AtomicReference<Optional<TextNode>> currentTextNode = new AtomicReference<>(Optional.<TextNode>absent());
    private final AtomicReference<Optional<Boolean>> lastWhitespace = new AtomicReference<>(Optional.<Boolean>absent());

    public ParserContext(ResourceReference resourceReference) {
        this.resourceReference = resourceReference;
    }

    public ResourceReference getResourceReference() {
        return resourceReference;
    }

    public void start(boolean whitespace) {
        if (currentTextNode.get().isPresent()) {
            if (whitespace) {
                currentTextNode.get().get()
                        .getConfiguration()
                        .setTrimRight(true);
            }
            currentTextNode.set(Optional.<TextNode>absent());;
        }
    }

    public void end(boolean whitespace) {
        lastWhitespace.set(Optional.of(whitespace));
    }

    public void current(TextNode node) {
        currentTextNode.set(Optional.of(node));
        node.getConfiguration().setTrimLeft(lastWhitespace.get().isPresent() ? lastWhitespace.get().get() : false);
        lastWhitespace.set(Optional.<Boolean>absent());
    }
}
