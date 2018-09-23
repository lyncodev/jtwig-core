package org.jtwig.parser.parsky.expression.collections;

import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.collections.EnumeratedListExpression;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import java.util.List;

import static org.jtwig.model.position.Position.position;

public class EnumerationListRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar context) {
        return Rules.transform(
                Rules.list(
                        context.rule(Expression.class),
                        Rules.string("["),
                        Rules.string(","),
                        Rules.mandatory(Rules.string("]"), "Missing end bracket")
                ),
                new Transform() {
                    @Override
                    public Result transform(ParserRequest request, Object input) {
                        return Transform.Result.success(new EnumeratedListExpression(
                                position(request),
                                (List) input
                        ));
                    }
                }
        );
    }
}
