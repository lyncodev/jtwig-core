package org.jtwig.parser.config.command;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.ImportNode;
import org.jtwig.model.tree.ImportSelfNode;
import org.jtwig.parser.parsky.ParserContext;
import org.jtwig.parser.parsky.tag.command.CommandNodeFactory;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;
import org.parsky.grammar.rules.transform.Transforms;

import java.util.List;

public class ImportNodeConfiguration extends CommandNodeConfiguration {
    public ImportNodeConfiguration() {
        super(ImportNode.class, "import", new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.sequence(
                        Rules.firstOf(
                                Rules.skipWhitespaces(Rules.string("_self")),
                                Rules.transform(Rules.sequence(
                                        Rules.test(Rules.not(Rules.skipWhitespaces(Rules.string("as")))),
                                        Rules.skipWhitespaces(Rules.mandatory(context.rule(Expression.class), "Missing import path expression"))
                                ), Transforms.pick(1)),
                                Rules.fail("Missing import path expression")
                        ),
                        Rules.transform(
                                Rules.sequence(
                                        Rules.mandatory(Rules.skipWhitespacesAfter(Rules.string("as")), "Wrong syntax expecting token 'as'"),
                                        Rules.skipWhitespacesAfter(Rules.mandatory(context.rule(VariableExpression.class), "Missing alias declaration"))
                                ),
                                Transforms.<ParserContext>pick(1)
                        )
                );
            }
        }, new CommandNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object input) {
                List inputs = (List) input;
                if (inputs.get(0) instanceof String) {
                    return Transform.Result.success(new ImportSelfNode(position, (VariableExpression) inputs.get(1)));
                }
                return Transform.Result.success(new ImportNode(
                        position,
                        (Expression) inputs.get(0),
                        (VariableExpression) inputs.get(1)
                ));
            }
        });
    }
}
