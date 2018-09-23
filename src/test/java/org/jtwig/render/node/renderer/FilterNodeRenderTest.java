package org.jtwig.render.node.renderer;

import org.hamcrest.Matcher;
import org.jtwig.escape.EscapeEngine;
import org.jtwig.escape.NoneEscapeEngine;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.expression.constant.ConstantExpression;
import org.jtwig.model.expression.operations.InjectableExpression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.FilterNode;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderRequest;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.StringRenderable;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.jtwig.support.MatcherUtils.theSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

public class FilterNodeRenderTest {
    private FilterNodeRender underTest = new FilterNodeRender();

    @Test
    public void render() throws Exception {
        String content = "content";
        String output = "output";
        Object value = new Object();
        EscapeEngine escapeMode = NoneEscapeEngine.instance();
        Position position = mock(Position.class);
        RenderRequest request = mock(RenderRequest.class, RETURNS_DEEP_STUBS);
        FilterNode filterNode = mock(FilterNode.class);
        Node node = mock(Node.class);
        InjectableExpression injectableExpression = mock(InjectableExpression.class);
        Expression expression = mock(Expression.class);

        when(filterNode.getFilterExpression()).thenReturn(injectableExpression);
        when(filterNode.getContent()).thenReturn(node);
        when(injectableExpression.getPosition()).thenReturn(position);
        when(request.getEnvironment().getRenderEnvironment().getRenderNodeService().render(request, node)).thenReturn(new StringRenderable(content, NoneEscapeEngine.instance()));
        when(injectableExpression.inject(argThat(theSame(new ConstantExpression(position, content))))).thenReturn(expression);
        when(request.getEnvironment().getRenderEnvironment().getCalculateExpressionService().calculate(request, expression)).thenReturn(value);
        when(request.getEnvironment().getValueEnvironment().getStringConverter().convert(value)).thenReturn(output);
        when(request.getRenderContext().getCurrent(EscapeEngine.class)).thenReturn(escapeMode);

        Renderable result = underTest.render(request, filterNode);

        assertThat(result, is((Matcher) theSame(new StringRenderable(output, escapeMode))));
    }
}