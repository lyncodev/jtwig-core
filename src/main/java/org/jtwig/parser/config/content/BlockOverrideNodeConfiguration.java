package org.jtwig.parser.config.content;

import org.jtwig.model.position.Position;
import org.jtwig.model.tree.CompositeNode;
import org.jtwig.model.tree.Node;
import org.jtwig.model.tree.OverrideBlockNode;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.jtwig.parser.parsky.tag.content.ContentNodeFactory;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import static java.util.Arrays.asList;

public class BlockOverrideNodeConfiguration extends ContentNodeConfiguration {
    public static final String BLOCK = "block";
    public static final String END_BLOCK = "endblock";

    public BlockOverrideNodeConfiguration() {
        super(BLOCK, asList(BLOCK, END_BLOCK), OverrideBlockNode.class, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.mandatory(context.rule(JtwigExpressionGrammar.IDENTIFIER), "Block identifier not specified");
            }
        }, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return context.rule(CompositeNode.class);
            }
        }, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.firstOf(
                        context.rule(JtwigExpressionGrammar.IDENTIFIER),
                        Rules.empty()
                );
            }
        }, new ContentNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object start, Object content, Object end) {
                if (end != null && !start.equals(end))
                    return Transform.Result.fail(String.format("Expecting block '%s' to end with the same identifier but found '%s' instead", start, end));
                return Transform.Result.success(new OverrideBlockNode(
                        position,
                        (String) start,
                        (Node) content
                ));
            }
        });
    }
}
