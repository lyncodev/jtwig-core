package org.jtwig.parser.parsky;

import org.jtwig.model.expression.collections.MapExpression;
import org.jtwig.model.expression.constant.NumberConstantExpression;
import org.jtwig.model.tree.OutputNode;
import org.jtwig.parser.config.DefaultJtwigParserConfiguration;
import org.junit.Test;
import org.parsky.engine.print.PrintParserEngine;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class JtwigGrammarTest {
    @Test
    public void output() throws Exception {
        OutputNode result = ParskyTestUtils.parse("{{ 1 }}", OutputNode.class);

        assertThat(result.getExpression(), instanceOf(NumberConstantExpression.class));
    }

    @Test
    public void mapExpression() throws Exception {
        OutputNode result = ParskyTestUtils.parse("{{ { one: 'two' } }}", OutputNode.class);

        assertThat(result.getExpression(), instanceOf(MapExpression.class));
    }

    @Test
    public void printGrammar() throws Exception {

        System.out.println(PrintParserEngine.print(JtwigGrammar.jtwigGrammar().create(new DefaultJtwigParserConfiguration())));

    }
}