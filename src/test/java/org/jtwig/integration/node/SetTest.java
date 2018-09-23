package org.jtwig.integration.node;

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

public class SetTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void simpleSet() throws Exception {

        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate("{% set variable = 1 %}{{ variable }}");

        String result = jtwigTemplate.render(newModel());

        assertThat(result, is("1"));
    }

    @Test
    public void setWhiteSpaceControl() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate(" {%- set variable = 1 -%} {{ variable }}");

        String result = jtwigTemplate.render(newModel());

        assertThat(result, is("1"));
    }

    @Test
    public void setNotEnding() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate(" {%- set variable = 1 {{ variable }}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Code island not closed"));

        jtwigTemplate.render(newModel());
    }

    @Test
    public void setNoAttribution() throws Exception {
        JtwigTemplate jtwigTemplate = JtwigTemplate.inlineTemplate(" {%- set variable 1 %}{{ variable }}");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage(containsString("Expecting attribution operation '='"));

        jtwigTemplate.render(newModel());
    }
}
