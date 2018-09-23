package org.jtwig.parser.parsky;

import org.jtwig.environment.Environment;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.JtwigParser;
import org.jtwig.parser.ParseException;
import org.jtwig.resource.ResourceService;
import org.jtwig.resource.metadata.ResourceMetadata;
import org.jtwig.resource.reference.ResourceReference;
import org.parsky.Parsky;
import org.parsky.ParskyException;

import java.nio.charset.Charset;

import static org.parboiled.common.FileUtils.readAllText;

public class ParskyJtwigParser implements JtwigParser {
    private Parsky parsky;

    public ParskyJtwigParser(Parsky parsky) {
        this.parsky = parsky;
    }

    @Override
    public Node parse(Environment environment, ResourceReference resource) {
        ResourceService resourceService = environment.getResourceEnvironment().getResourceService();
        ResourceMetadata resourceMetadata = resourceService.loadMetadata(resource);
        Charset charset = resourceMetadata.getCharset().or(environment.getResourceEnvironment().getDefaultInputCharset());
        String content = readAllText(resourceMetadata.load(), charset);

        ParserContext context = new ParserContext(resource);

        try {
            return (Node) parsky.parse(context, content);
        } catch (ParskyException e) {
            throw new ParseException(e);
        }
    }
}
