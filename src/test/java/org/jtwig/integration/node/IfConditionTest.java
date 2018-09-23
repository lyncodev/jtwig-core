package org.jtwig.integration.node;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.jtwig.JtwigModel.newModel;

public class IfConditionTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleIfCondition() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if (true) %}ok{% else %}ko{% endif %}");

        String result = jtwigTemplate.render(newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void ifExpressionWhereVariableUnexpected() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% if (variable) %}OK{% else %}KO{% endif %}")
                .render(JtwigModel.newModel().with("variable", new Object()));

        assertThat(result, is("OK"));
    }

    @Test
    public void ifConditionWhiteSpaceControlEndIf() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if (true) -%} ok{% else %}ko{% endif %}");

        String result = jtwigTemplate.render(newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void ifConditionWhiteSpaceControlStartIf() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate(" {%- if (true) -%} ok{% else %}ko{% endif %}");

        String result = jtwigTemplate.render(newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void ifConditionWhiteSpaceControlStartElse() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate(" {%- if (true) -%} ok {%- else %}ko{% endif %}");

        String result = jtwigTemplate.render(newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void ifConditionWhiteSpaceControl() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate(" {%- if (false) -%} ko {%- else -%} ok {%- endif -%} ");

        String result = jtwigTemplate.render(newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void simpleIfSharedContext() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% set a = 2 %}{% if (true) %}{% set a = 1 %}{% endif %}{{ a }}");

        String result = jtwigTemplate.render(newModel());

        assertThat(result, is("1"));
    }

    @Test
    public void simpleIfConditionWithVariable() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if (isItTrue) %}ok{% else %}ko{% endif %}");

        String result = jtwigTemplate.render(newModel().with("isItTrue", true));

        assertThat(result, is("ok"));
    }

    @Test
    public void ifConditionElse() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if (false) %}ko{% else %}ok{% endif %}");

        String result = jtwigTemplate.render(newModel());

        assertThat(result, is("ok"));
    }

    @Test
    public void ifConditionWithoutExpression() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if ()%}ko{% endif %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting conditional expression"));
        jtwigTemplate.render(newModel());
    }

    @Test
    public void ifConditionWithoutEndCode() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if (true) ko{% endif %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));
        jtwigTemplate.render(newModel());
    }

    @Test
    public void ifConditionWithoutEndIfEndCode() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if (true) %}ko{% endif");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));
        jtwigTemplate.render(newModel());
    }


    @Test
    public void elseIfConditionWithoutExpression() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if (true) %}{% elseif ()%}ko{% endif %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting else if conditional expression"));
        jtwigTemplate.render(newModel());
    }

    @Test
    public void elseIfConditionWithoutEndingParentheses() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if (true) %}{% elseif (true %}ko{% endif %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Uneven parentheses"));
        jtwigTemplate.render(newModel());
    }

    @Test
    public void elseIfConditionWithoutEndCode() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if (true) %}{% elseif (true) ko{% endif %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));
        jtwigTemplate.render(newModel());
    }

    @Test
    public void ifConditionIncorrectElseIf() throws Exception {
        // For the time being we will use this, but we might want to improve our messages and try to check the if syntax
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if (false) %}ko{% else if (true) %}ok{% endif %}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting ending of else block"));
        jtwigTemplate.render(newModel());
    }

    @Test
    public void ifConditionMissingEnd() throws Exception {
        // For the time being we will use this, but we might want to improve our messages and try to check the if syntax
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% if (false) %}ko");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Missing endblock tag"));
        jtwigTemplate.render(newModel());
    }
}
