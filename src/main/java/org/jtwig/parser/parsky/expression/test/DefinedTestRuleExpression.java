package org.jtwig.parser.parsky.expression.test;

import org.jtwig.model.expression.operations.test.DefinedTestExpression;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transforms;

public class DefinedTestRuleExpression implements RuleFactory {
    @Override
    public Rule create(Grammar context) {
        return Rules.transform(
                Rules.skipWhitespaces(Rules.string("defined")),
                Transforms.value(new DefinedTestExpression())
        );
    }
}
