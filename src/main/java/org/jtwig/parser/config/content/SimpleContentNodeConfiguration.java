package org.jtwig.parser.config.content;

import org.jtwig.model.position.Position;
import org.jtwig.model.tree.CompositeNode;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.parsky.tag.content.ContentNodeFactory;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactories;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import static java.util.Arrays.asList;

public class SimpleContentNodeConfiguration extends ContentNodeConfiguration {
    public SimpleContentNodeConfiguration(String keyword, Class<? extends Node> type, RuleFactory startRule, final SimpleContentNodeFactory factory) {
        super(keyword, asList(keyword, "end"+keyword), type, startRule, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return context.rule(CompositeNode.class);
            }
        }, RuleFactories.simple(Rules.empty()), new ContentNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object start, Object content, Object end) {
                return factory.create(position, start, (Node) content);
            }
        });
    }

    public interface SimpleContentNodeFactory {
        Transform.Result create (Position position, Object start, Node content);
    }
}
