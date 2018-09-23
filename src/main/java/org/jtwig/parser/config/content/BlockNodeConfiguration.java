package org.jtwig.parser.config.content;

import org.jtwig.model.position.Position;
import org.jtwig.model.tree.BlockNode;
import org.jtwig.model.tree.CompositeNode;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.ParserContext;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.jtwig.parser.parsky.tag.content.ContentNodeFactory;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import static java.util.Arrays.asList;

public class BlockNodeConfiguration extends ContentNodeConfiguration {
    public static final String BLOCK = "block";
    public static final String END_BLOCK = "endblock";

    public BlockNodeConfiguration() {
        super(BLOCK, asList(BLOCK, END_BLOCK), BlockNode.class, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.mandatory(context.rule(JtwigExpressionGrammar.IDENTIFIER), "Block identifier not specified");
            }
        }, new RuleFactory() {
            @Override
            public Rule create( Grammar context) {
                return context.rule(CompositeNode.class);
            }
        }, new RuleFactory() {
            @Override
            public Rule create( Grammar context) {
                return Rules.firstOf(
                        context.rule(JtwigExpressionGrammar.IDENTIFIER),
                        Rules.<ParserContext>empty()
                );
            }
        }, new ContentNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object start, Object content, Object end) {
                if (end != null && !start.equals(end))
                    return Transform.Result.fail(String.format("Expecting block '%s' to end with the same identifier but found '%s' instead", start, end));
                return Transform.Result.success(new BlockNode(
                        position,
                        (String) start,
                        (Node) content
                ));
            }
        });
    }
}
