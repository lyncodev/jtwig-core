package org.jtwig.parser.parsky.tag;

import org.jtwig.model.tree.CompositeNode;
import org.jtwig.model.tree.Node;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.ListTransform;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.ArrayList;
import java.util.List;

import static org.jtwig.model.position.Position.position;

public class CompositeNodeRuleExpression implements RuleFactory {
    private final String commentRule;

    public CompositeNodeRuleExpression(String commentRule) {
        this.commentRule = commentRule;
    }

    @Override
    public Rule create(Grammar context) {
        return Rules.transform(
                Rules.zeroOrMore(Rules.firstOf(
                        context.rule(Node.class),
                        context.rule(commentRule)
                )),
                Transforms.list(new ListTransform.TransformList() {
                    @Override
                    public Transform.Result transform(ParserRequest request, ListTransform.Request input) {
                        List<Node> values = new ArrayList<>();
                        for (Object item : input) {
                            if (item instanceof Node) {
                                values.add((Node) item);
                            }
                        }

                        return Transform.Result.success(new CompositeNode(
                                position(request),
                                values
                        ));
                    }
                })
        );
    }
}
