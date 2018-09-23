package org.jtwig.parser.config.content;

import org.jtwig.model.position.Position;
import org.jtwig.model.tree.VerbatimNode;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.ParserContext;
import org.jtwig.parser.parsky.tag.content.ContentNodeFactory;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactories;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import static java.util.Arrays.asList;

public class VerbatimNodeConfiguration extends ContentNodeConfiguration {
    public VerbatimNodeConfiguration() {
        super("verbatim", asList("verbatim", "endverbatim"), VerbatimNode.class, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.empty();
            }
        }, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.test(
                        Rules.zeroOrMore(Rules.not(
                                Rules.sequence(
                                        context.rule(JtwigGrammar.START_CODE),
                                        Rules.skipWhitespaces(Rules.string("endverbatim")),
                                        context.rule(JtwigGrammar.END_CODE)
                                )
                        ))
                );
            }
        }, RuleFactories.simple(Rules.empty()), new ContentNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object start, Object content, Object end) {
                return Transform.Result.success(new VerbatimNode(
                        position,
                        (String) content
                ));
            }
        });
    }
}
