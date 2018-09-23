package org.jtwig.parser;

import com.google.common.base.Optional;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.parser.config.JtwigParserConfiguration;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.ParskyJtwigParser;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.parsky.Parsky;

public class JtwigParserFactory {
    public JtwigParser create (JtwigParserConfiguration configuration) {
        JtwigGrammar jtwigGrammar = JtwigGrammar.jtwigGrammar();
        JtwigParser parser = new ParskyJtwigParser(Parsky.simple(jtwigGrammar.create(configuration)));
        Optional<TemplateCache> templateCache = configuration.getTemplateCache();
        if (templateCache.isPresent()) {
            return new CachedJtwigParser(templateCache.get(), parser);
        } else {
            return parser;
        }
    }

}
