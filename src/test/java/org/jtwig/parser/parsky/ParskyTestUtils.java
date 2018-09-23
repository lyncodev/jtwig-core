package org.jtwig.parser.parsky;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.config.DefaultJtwigParserConfiguration;
import org.jtwig.resource.reference.ResourceReference;
import org.parsky.Parsky;
import org.parsky.ParskyException;
import org.parsky.context.Context;
import org.parsky.engine.ParserRequest;
import org.parsky.engine.ParserResult;
import org.parsky.engine.simple.SimpleParserEngine;
import org.parsky.grammar.*;
import org.parsky.grammar.rules.ReferenceRule;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.mock;

public class ParskyTestUtils {
    public static GrammarBuilder copy (GrammarImpl grammar) {
        GrammarBuilder grammarBuilder = GrammarBuilder.newGrammar();
        for (Map.Entry<String, ReferenceRule> entry : grammar.getReferences().entrySet()) {
            grammarBuilder.define(entry.getKey(), RuleFactories.simple(entry.getValue()));
        }

        return grammarBuilder;
    }

    public static <T> T parse (String content, Class<T> type) throws ParskyException {
        final AtomicReference<Class> retrieveType = new AtomicReference<>((Class) type);
        if (Expression.class.isAssignableFrom(type)) retrieveType.set(Expression.class);
        Grammar grammar = copy((GrammarImpl) JtwigGrammar.jtwigGrammar()
                .create(new DefaultJtwigParserConfiguration()))
                .define("newStart", new RuleFactory() {
                    @Override
                    public Rule create(Grammar grammar) {
                        return Rules.transform(
                                Rules.sequence(
                                        grammar.rule(retrieveType.get()),
                                        Rules.mandatory(Rules.endOfInput(), "Expecting end of input")
                                ),
                                Transforms.pick(0)
                        );
                    }
                })
                .build("newStart");
        return type.cast(Parsky.simple(grammar)
                .parse(new ParserContext(mock(ResourceReference.class)), content));
    }

    public static Node result (String content) throws ParskyException {
        return (Node) Parsky.simple(JtwigGrammar.jtwigGrammar()
                .create(new DefaultJtwigParserConfiguration()))
                .parse(new ParserContext(mock(ResourceReference.class)), content);
    }

    public static ParserResult match (String content, Class type) {

        Rule rule = JtwigGrammar.jtwigGrammar()
                .create(new DefaultJtwigParserConfiguration())
                .rule(type);
        char[] charArray = content.toCharArray();
        Context context = new Context();
        context.put(new ParserContext(mock(ResourceReference.class)));
        return SimpleParserEngine.create().apply(rule, new ParserRequest(
                charArray,
                0,
                false,
                context
        ));
    }
}
