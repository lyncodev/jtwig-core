package org.jtwig.parser.dynamic.factories;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.JtwigNode;
import org.jtwig.parser.dynamic.model.SequenceJtwigNode;
import org.jtwig.parsing.sequence.SequenceMatchers;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.ListTransformationRequest;
import org.jtwig.parsing.transform.Transformations;

import java.util.ArrayList;
import java.util.List;

public class SequenceJtwigParserFactory implements JtwigParserFactory<SequenceJtwigNode> {
    private final JtwigParserFactoryRegistry registry;

    public SequenceJtwigParserFactory(JtwigParserFactoryRegistry registry) {
        this.registry = registry;
    }

    @Override
    public TransformSequenceMatcher<SequenceJtwigNode> create(ParserConfiguration configuration) {
        return SequenceMatchers.transform(
                SequenceMatchers.zeroOrMore(
                        SequenceMatchers.firstOf(
                                registry.create(configuration)
                        )
                ),
                Transformations.fromContentList(new Function<ListTransformationRequest, SequenceJtwigNode>() {
                    @Override
                    public SequenceJtwigNode apply(ListTransformationRequest input) {
                        List<JtwigNode> nodes = new ArrayList<>();
                        for (int i = 0; i < input.size(); i++) {
                            nodes.add(input.get(i, JtwigNode.class));
                        }
                        return new SequenceJtwigNode(nodes);
                    }
                })
        );
    }
}
