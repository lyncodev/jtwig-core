package org.jtwig.model.position;

import org.jtwig.resource.reference.ResourceReference;
import org.parsky.context.Label;
import org.parsky.position.*;

import java.util.Collection;

public class JtwigPositionDescriber extends DefaultPositionDescriber {
    private final ResourceReference resourceReference;

    public JtwigPositionDescriber(Collection<Label> labels, ResourceReference resourceReference) {
        super(labels, new LineExtractorService(), new PositionExtractorService(), new PrintLabelListService());
        this.resourceReference = resourceReference;
    }


    @Override
    public String explain(char[] content, int offset) {
        return String.format(
                "%s - %s",
                resourceReference,
                super.explain(content, offset)
        );
    }
}
