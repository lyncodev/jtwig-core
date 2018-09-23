package org.jtwig.parser.parsky.tag;

import org.jtwig.model.tree.TextNode;
import org.jtwig.parser.config.SyntaxConfiguration;
import org.jtwig.parser.parsky.ParserContext;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import static org.jtwig.model.position.Position.position;

public class TextNodeRuleExpression implements RuleFactory {
    private final SyntaxConfiguration syntax;

    public TextNodeRuleExpression(SyntaxConfiguration syntax) {
        this.syntax = syntax;
    }

    @Override
    public Rule create(Grammar context) {
        return Rules.transform(
                Rules.text(
                        Rules.sequence(
                                Rules.test(Rules.not(Rules.firstOf(
                                        Rules.firstOf(
                                                Rules.<ParserContext>string(syntax.getStartCode()),
                                                Rules.<ParserContext>string(syntax.getStartComment()),
                                                Rules.<ParserContext>string(syntax.getStartOutput())
                                        ),
                                        Rules.endOfInput()
                                ))),
                                Rules.zeroOrMore(Rules.not(Rules.firstOf(
                                        Rules.firstOf(
                                                Rules.string(syntax.getStartCode()),
                                                Rules.string(syntax.getStartComment()),
                                                Rules.string(syntax.getStartOutput())
                                        ),
                                        Rules.endOfInput()
                                )))
                        )
                ),
                new Transform() {
                    @Override
                    public Result transform(ParserRequest request, Object input) {
                        TextNode result = new TextNode(
                                position(request),
                                (String) input,
                                new TextNode.Configuration()
                        );
                        request.getContext().peek(ParserContext.class).get().current(result);
                        return Transform.Result.success(result);
                    }
                }
        );
    }
}
