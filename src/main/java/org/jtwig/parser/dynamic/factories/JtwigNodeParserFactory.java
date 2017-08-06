package org.jtwig.parser.dynamic.factories;

import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.JtwigNode;
import org.jtwig.parsing.factory.SequenceMatcherFactory;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;

public interface JtwigNodeParserFactory<T extends JtwigNode> extends SequenceMatcherFactory<ParserConfiguration, TransformSequenceMatcher<T>> {

}
