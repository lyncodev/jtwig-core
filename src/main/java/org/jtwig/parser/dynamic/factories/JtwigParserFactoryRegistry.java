package org.jtwig.parser.dynamic.factories;

import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parsing.sequence.SequenceMatcher;

import java.util.ArrayList;
import java.util.List;

public class JtwigParserFactoryRegistry {
    private final List<JtwigParserFactory> factories;

    public JtwigParserFactoryRegistry() {
        this.factories = new ArrayList<>();
    }

    public JtwigParserFactoryRegistry include () {

    }

    public List<SequenceMatcher> create(ParserConfiguration configuration) {
        return null;
    }
}
