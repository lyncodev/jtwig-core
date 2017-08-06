package org.jtwig.parser.dynamic.factories;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.JtwigNode;
import org.jtwig.parser.dynamic.model.SequenceJtwigNode;
import org.jtwig.parser.dynamic.model.position.Position;
import org.jtwig.parsing.sequence.SequenceMatchers;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.ListTransformationRequest;
import org.jtwig.parsing.transform.Transformations;
import org.jtwig.parsing.tree.ContentNode;
import org.jtwig.parsing.tree.ListNode;
import org.jtwig.parsing.tree.Node;

import java.util.ArrayList;
import java.util.List;

public class SequenceJtwigParserFactory implements JtwigNodeParserFactory<SequenceJtwigNode> {
    private final JtwigParserFactoryRegistry registry;

    public SequenceJtwigParserFactory(JtwigParserFactoryRegistry registry) {
        this.registry = registry;
    }

    @Override
    public TransformSequenceMatcher<SequenceJtwigNode> create(ParserConfiguration configuration) {
        return SequenceMatchers.transform(
                SequenceMatchers.sequence(
                        SequenceMatchers.position(),
                        SequenceMatchers.zeroOrMore(
                                SequenceMatchers.firstOf(
                                        registry.create(configuration)
                                )
                        ),
                        SequenceMatchers.position()
                ),
                Transformations.fromContentList(new Function<ListTransformationRequest, SequenceJtwigNode>() {
                    @Override
                    public SequenceJtwigNode apply(ListTransformationRequest input) {
                        return new SequenceJtwigNode(
                                new Position(input.get(0, Integer.class), input.get(2, Integer.class)),
                                extractList(input.get(1, ListNode.class).getNodes())
                        );
                    }

                    private List<JtwigNode> extractList(List<Node> nodes) {
                        List<JtwigNode> result = new ArrayList<>();
                        for (Node node : nodes) {
                            result.add(((ContentNode<JtwigNode>) node).getContent());
                        }
                        return result;
                    }
                })
        );
    }
}
