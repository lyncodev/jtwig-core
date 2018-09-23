package org.jtwig.integration.node;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;

public class UnknownNodeTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void unknownTag() throws Exception {
        expectedException.expectMessage(containsString("Unknown tag 'unknown'"));
        expectedException.expect(ParseException.class);

        JtwigTemplate.inlineTemplate("{% unknown %}blah{% unknown %}")
                .render(JtwigModel.newModel());

    }
}
