package org.jtwig.parser.dynamic.factories;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.model.JtwigNode;
import org.jtwig.parser.dynamic.model.SequenceJtwigNode;
import org.jtwig.parsing.factory.SequenceMatcherFactoryRegistry;
import org.jtwig.parsing.sequence.SequenceMatcher;
import org.jtwig.parsing.sequence.SequenceMatchers;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.ListTransformationRequest;
import org.jtwig.parsing.transform.Transformations;

import java.util.ArrayList;
import java.util.List;

public class SequenceJtwigParserFactory implements JtwigNodeParserFactory<SequenceJtwigNode> {
    private final SequenceMatcherFactoryRegistry<ParserConfiguration, SequenceMatcher> registry;

    public SequenceJtwigParserFactory(SequenceMatcherFactoryRegistry<ParserConfiguration, SequenceMatcher> registry) {
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
                        return new SequenceJtwigNode(
                                input.getMatchResult().getRange(),
                                extractList(input)
                        );
                    }

                    private List<JtwigNode> extractList(ListTransformationRequest nodes) {
                        List<JtwigNode> result = new ArrayList<>();
                        for (int i = 0; i < nodes.size(); i++) {
                            result.add(nodes.get(i, JtwigNode.class));
                        }
                        return result;
                    }
                })
        );
    }
}
