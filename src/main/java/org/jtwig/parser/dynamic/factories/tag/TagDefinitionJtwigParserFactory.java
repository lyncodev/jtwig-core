package org.jtwig.parser.dynamic.factories.tag;

import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.tag.JtwigEndTagDefinition;
import org.jtwig.parser.dynamic.model.tag.JtwigStartTagDefinition;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;

public interface TagDefinitionJtwigParserFactory<S extends JtwigStartTagDefinition, E extends JtwigEndTagDefinition> {
    TransformSequenceMatcher<S> createStartTag (ParserConfiguration parserConfiguration);
    TransformSequenceMatcher<E> createEndTag (ParserConfiguration parserConfiguration);
}
