package org.jtwig.parser.parsky.expression.operations;

import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transforms;

public class BinaryOperatorSequenceMatcherFactory {
    public Rule create(BinaryOperator operator) {
        if (endsWithNonSymbol(operator.symbol())) {
            return Rules.transform(
                    Rules.sequence(
                            Rules.string(operator.symbol()),
                            Rules.test(Rules.not(Rules.firstOf(
                                    Rules.range('a', 'z'),
                                    Rules.range('A', 'Z'),
                                    Rules.range('0', '9')
                            )))
                    ),
                    Transforms.value(operator)
            );
        } else {
            return Rules.transform(
                    Rules.string(operator.symbol()),
                    Transforms.value(operator)
            );
        }
    }

    boolean endsWithNonSymbol(String symbol) {
        return symbol.matches(".*[a-zA-Z0-9_\\$]$");
    }
}
