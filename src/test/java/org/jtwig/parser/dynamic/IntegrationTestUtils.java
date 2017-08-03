package org.jtwig.parser.dynamic;

import org.jtwig.parser.dynamic.config.DefaultParserConfiguration;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.factories.JtwigParserFactory;
import org.jtwig.parser.dynamic.model.JtwigNode;
import org.jtwig.parsing.Parser;
import org.jtwig.parsing.transform.Transformation;
import org.jtwig.parsing.tree.ContentNode;
import org.jtwig.parsing.tree.Node;

public class IntegrationTestUtils {

    private static final DefaultParserConfiguration CONFIGURATION = new DefaultParserConfiguration();

    public static <T extends JtwigNode> T parse (JtwigParserFactory<T> parser, String input) {
        return new Parser(parser.create(CONFIGURATION))
                .parse(input)
                .output(new Transformation<T>() {
                    @Override
                    public ContentNode<T> transform(Node node) {
                        return (ContentNode<T>) node;
                    }
                });
    }

    public static ParserConfiguration configuration() {
        return CONFIGURATION;
    }
}
