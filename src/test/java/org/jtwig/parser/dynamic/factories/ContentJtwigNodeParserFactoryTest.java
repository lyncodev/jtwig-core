package org.jtwig.parser.dynamic.factories;

import org.jtwig.parser.dynamic.IntegrationTestUtils;
import org.jtwig.parser.dynamic.model.ContentJtwigNode;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ContentJtwigNodeParserFactoryTest {
    private ContentJtwigParserFactory underTest = new ContentJtwigParserFactory();

    @Test
    public void noCode() throws Exception {
        String input = "asdksaldkasldksaldkaslkdlsak laksdlk asldk asdasd ";

        ContentJtwigNode result = IntegrationTestUtils.parse(underTest, input);

        assertThat(result.getContent(), is(input));
    }

    @Test
    public void code() throws Exception {
        String input = String.format("asdksaldkasldksaldkaslkdlsak laksdlk asldk asdasd %s asdasd", IntegrationTestUtils.configuration().getCodeIslandConfiguration().getStartCode());

        ContentJtwigNode result = IntegrationTestUtils.parse(underTest, input);

        assertThat(result.getContent(), is("asdksaldkasldksaldkaslkdlsak laksdlk asldk asdasd "));
    }

    @Test
    public void output() throws Exception {
        String input = String.format("asdksaldkasldksaldkaslkdlsak laksdlk asldk asdasd %s asdasd", IntegrationTestUtils.configuration().getCodeIslandConfiguration().getStartOutput());

        ContentJtwigNode result = IntegrationTestUtils.parse(underTest, input);

        assertThat(result.getContent(), is("asdksaldkasldksaldkaslkdlsak laksdlk asldk asdasd "));
    }

    @Test
    public void comment() throws Exception {
        String input = String.format("asdksaldkasldksaldkaslkdlsak laksdlk asldk asdasd %s asdasd", IntegrationTestUtils.configuration().getCodeIslandConfiguration().getStartComment());

        ContentJtwigNode result = IntegrationTestUtils.parse(underTest, input);

        assertThat(result.getContent(), is("asdksaldkasldksaldkaslkdlsak laksdlk asldk asdasd "));
    }
}