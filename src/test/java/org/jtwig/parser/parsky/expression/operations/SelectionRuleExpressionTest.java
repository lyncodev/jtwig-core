package org.jtwig.parser.parsky.expression.operations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.model.expression.Expression;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class SelectionRuleExpressionTest {

    @Test
    public void parsky() throws Exception {
        Expression expression = ParskyTestUtils.parse("list[0].key", Expression.class);

        System.out.println(expression);
    }
    @Test
    public void parsky2() throws Exception {
        Expression expression = ParskyTestUtils.parse("(list[1])[0]", Expression.class);

        System.out.println(expression);
    }

    @Test
    public void parsky3() throws Exception {
        Expression expression = ParskyTestUtils.parse("(list[1])[0] * 6 / (3 + 3) + 4 - (1 + 3)", Expression.class);

        System.out.println(expression);
    }

    @Test
    public void name() throws Exception {
        String render = JtwigTemplate.inlineTemplate("{{ var.list[0].key[0] }}")
                .render(JtwigModel.newModel().with("var", ImmutableMap.of("list", ImmutableList.of(ImmutableMap.of("key", ImmutableList.of("test"))))));

        System.out.println(render);
    }

    public static class Bean {
        private final String key;

        public Bean(String key) {
            this.key = key;
        }
    }
}