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

public class CompositeNodeRuleExpressionTest {
    @Test
    public void composite() throws Exception {
        CompositeNode result = ParskyTestUtils.parse("text {{- 1 }}", CompositeNode.class);

        assertThat(result.getNodes(), hasSize(2));
        assertThat(result.getNodes().get(0), instanceOf(TextNode.class));
        assertThat(result.getNodes().get(1), instanceOf(OutputNode.class));
    }

    @Test
    public void compositeComment() throws Exception {
        CompositeNode result = ParskyTestUtils.parse("text {# something here doesn't really matter #}{{- 1 }}", CompositeNode.class);

        assertThat(result.getNodes(), hasSize(2));
        assertThat(result.getNodes().get(0), instanceOf(TextNode.class));
        assertThat(result.getNodes().get(1), instanceOf(OutputNode.class));
    }
}