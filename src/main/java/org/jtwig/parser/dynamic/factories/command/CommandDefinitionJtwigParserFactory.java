package org.jtwig.parser.dynamic.factories.command;

import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.command.JtwigCommandDefinition;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;

public interface CommandDefinitionJtwigParserFactory<T extends JtwigCommandDefinition> {
    TransformSequenceMatcher<T> create (ParserConfiguration parserConfiguration);
}
