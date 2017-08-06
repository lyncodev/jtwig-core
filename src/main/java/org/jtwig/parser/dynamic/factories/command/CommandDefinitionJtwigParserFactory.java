package org.jtwig.parser.dynamic.factories.command;

import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.command.JtwigCommandDefinition;
import org.jtwig.parsing.factory.SequenceMatcherFactory;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;

public interface CommandDefinitionJtwigParserFactory<T extends JtwigCommandDefinition> extends SequenceMatcherFactory<ParserConfiguration, TransformSequenceMatcher<T>> {

}
