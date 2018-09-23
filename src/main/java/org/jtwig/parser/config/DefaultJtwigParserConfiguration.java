package org.jtwig.parser.config;

import com.google.common.base.Optional;
import org.jtwig.parser.cache.InMemoryConcurrentPersistentTemplateCache;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.parser.config.command.CommandNodeConfiguration;
import org.jtwig.parser.config.content.ContentNodeConfiguration;
import org.jtwig.render.expression.calculator.operation.binary.impl.*;
import org.jtwig.render.expression.calculator.operation.unary.impl.NegativeUnaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.impl.NotUnaryOperator;

import java.util.Arrays;
import java.util.Collections;

public class DefaultJtwigParserConfiguration extends JtwigParserConfiguration {
    public DefaultJtwigParserConfiguration() {
        super(new DefaultSyntaxConfiguration(),
                Arrays.asList(
                        new NotUnaryOperator(),
                        new NegativeUnaryOperator()
                ), Arrays.asList(
                        new MatchesOperator(),
                        new CompositionOperator(),
                        new InOperator(),
                        new ConcatOperator(),

                        new SumOperator(),
                        new SubtractOperator(),
                        new IntDivideOperator(),
                        new IntMultiplyOperator(),
                        new DivideOperator(),
                        new MultiplyOperator(),
                        new ModOperator(),

                        new LessOrEqualOperator(),
                        new GreaterOrEqualOperator(),
                        new LessOperator(),
                        new GreaterOperator(),

                        new AndOperator(),
                        new OrOperator(),
                        new EquivalentOperator(),
                        new DifferentOperator()
                ),
                Collections.<CommandNodeConfiguration>emptyList(),
                Collections.<ContentNodeConfiguration>emptyList(),
                Optional.<TemplateCache>of(new InMemoryConcurrentPersistentTemplateCache()));
    }
}
