package org.jtwig.parser.dynamic.factories;

import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parsing.sequence.SequenceMatcher;

import java.util.ArrayList;
import java.util.List;

public class JtwigParserFactoryRegistry {
    private final List<JtwigNodeParserFactory> factories;

    public JtwigParserFactoryRegistry() {
        this.factories = new ArrayList<>();
    }

    public JtwigParserFactoryRegistry include (JtwigNodeParserFactory factory) {
        factories.add(factory);
        return this;
    }

    public List<SequenceMatcher> create(ParserConfiguration configuration) {
        ArrayList<SequenceMatcher> sequenceMatchers = new ArrayList<>();
        for (JtwigNodeParserFactory factory : factories) {
            sequenceMatchers.add(factory.create(configuration));
        }
        return sequenceMatchers;
    }
}
