package org.jtwig.parser.config.content;

import org.jtwig.model.expression.VariableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.MacroNode;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import java.util.List;

public class MacroNodeConfiguration extends SimpleContentNodeConfiguration {
    public MacroNodeConfiguration() {
        super("macro", MacroNode.class, new RuleFactory() {
            @Override
            public Rule create(Grammar context) {
                return Rules.sequence(
                        Rules.skipWhitespacesAfter(Rules.mandatory(context.rule(VariableExpression.class), "Missing macro name")),
                        Rules.mandatory(Rules.list(
                                Rules.skipWhitespaces(context.rule(JtwigExpressionGrammar.IDENTIFIER)),
                                Rules.string("("),
                                Rules.string(","),
                                Rules.mandatory(Rules.string(")"), "Missing end parenthesis"))
                        , "Missing macro arguments")
                );
            }
        }, new SimpleContentNodeFactory() {
            @Override
            public Transform.Result create(Position position, Object start, Node content) {
                List inputs = (List) start;
                return Transform.Result.success(new MacroNode(
                        position,
                        (VariableExpression) inputs.get(0),
                        (List) inputs.get(1),
                        content
                ));
            }
        });
    }
}
