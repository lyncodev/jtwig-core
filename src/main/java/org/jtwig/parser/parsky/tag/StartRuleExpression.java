package org.jtwig.parser.parsky.tag;

import org.jtwig.model.tree.CompositeNode;
import org.jtwig.model.tree.ExtendsNode;
import org.jtwig.model.tree.TextNode;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import static org.jtwig.model.position.Position.position;

public class StartRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar context) {
        return Rules.firstOf(
                Rules.transform(
                        Rules.sequence(
                                context.rule(ExtendsNode.class),
                                Rules.mandatory(Rules.endOfInput(), "Unable to parse template")
                        ),
                        Transforms.pick(0)
                ),
                Rules.transform(
                        Rules.sequence(
                                context.rule(CompositeNode.class),
                                Rules.mandatory(Rules.endOfInput(), "Unable to parse template")
                        ),
                        Transforms.pick(0)
                ),
                Rules.transform(
                        Rules.text(Rules.skipWhitespaces(
                                Rules.mandatory(Rules.endOfInput(), "Unable to parse template")
                        )),
                        new Transform() {
                            @Override
                            public Result transform(ParserRequest request, Object input) {
                                return Transform.Result.success(new TextNode(
                                        position(request),
                                        "", new TextNode.Configuration()
                                ));
                            }
                        }
                )
        );
    }
}
