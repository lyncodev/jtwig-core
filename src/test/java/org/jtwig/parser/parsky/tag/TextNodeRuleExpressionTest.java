package org.jtwig.parser.parsky.tag;

import org.jtwig.model.tree.CompositeNode;
import org.jtwig.model.tree.OutputNode;
import org.jtwig.model.tree.TextNode;
import org.jtwig.parser.parsky.ParskyTestUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

public class TextNodeRuleExpressionTest {
    @Test
    public void text() throws Exception {
        TextNode textNode = ParskyTestUtils.parse("example {{", TextNode.class);

        assertThat(textNode.getText(), is("example "));
    }


    @Test
    public void trimRight() throws Exception {
        CompositeNode result = ParskyTestUtils.parse("text {{- 1 }}", CompositeNode.class);

        assertThat(result.getNodes(), hasSize(2));
        assertThat(result.getNodes().get(0), instanceOf(TextNode.class));
        assertThat(result.getNodes().get(1), instanceOf(OutputNode.class));
        assertThat(((TextNode) result.getNodes().get(0)).getConfiguration().isTrimRight(), is(true));
        assertThat(((TextNode) result.getNodes().get(0)).getConfiguration().isTrimLeft(), is(false));
    }

    @Test
    public void trimLeft() throws Exception {
        CompositeNode result = ParskyTestUtils.parse("{{ 1 -}}  Hello", CompositeNode.class);

        assertThat(result.getNodes(), hasSize(2));
        assertThat(result.getNodes().get(0), instanceOf(OutputNode.class));
        assertThat(result.getNodes().get(1), instanceOf(TextNode.class));
        assertThat(((TextNode) result.getNodes().get(1)).getConfiguration().isTrimRight(), is(false));
        assertThat(((TextNode) result.getNodes().get(1)).getConfiguration().isTrimLeft(), is(true));
    }

    @Test
    public void trimBoth() throws Exception {
        CompositeNode result = ParskyTestUtils.parse("{{ 1 -}}  Hello {{- 2 }}", CompositeNode.class);

        assertThat(result.getNodes(), hasSize(3));
        assertThat(result.getNodes().get(0), instanceOf(OutputNode.class));
        assertThat(result.getNodes().get(1), instanceOf(TextNode.class));
        assertThat(result.getNodes().get(2), instanceOf(OutputNode.class));
        assertThat(((TextNode) result.getNodes().get(1)).getConfiguration().isTrimRight(), is(true));
        assertThat(((TextNode) result.getNodes().get(1)).getConfiguration().isTrimLeft(), is(true));
    }

    @Test
    public void trimNone() throws Exception {
        CompositeNode result = ParskyTestUtils.parse("{{ 1 }}  Hello {{ 2 }}", CompositeNode.class);

        assertThat(result.getNodes(), hasSize(3));
        assertThat(result.getNodes().get(0), instanceOf(OutputNode.class));
        assertThat(result.getNodes().get(1), instanceOf(TextNode.class));
        assertThat(result.getNodes().get(2), instanceOf(OutputNode.class));
        assertThat(((TextNode) result.getNodes().get(1)).getConfiguration().isTrimRight(), is(false));
        assertThat(((TextNode) result.getNodes().get(1)).getConfiguration().isTrimLeft(), is(false));
    }
}