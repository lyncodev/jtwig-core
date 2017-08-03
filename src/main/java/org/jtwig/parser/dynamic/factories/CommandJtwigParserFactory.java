package org.jtwig.parser.dynamic.factories;

import com.google.common.base.Function;
import org.jtwig.parser.dynamic.config.ParserConfiguration;
import org.jtwig.parser.dynamic.factories.command.CommandDefinitionJtwigParserFactory;
import org.jtwig.parser.dynamic.factories.control.IsTrimWhiteSpaceParserFactory;
import org.jtwig.parser.dynamic.model.CommandJtwigNode;
import org.jtwig.parser.dynamic.model.command.JtwigCommandDefinition;
import org.jtwig.parser.dynamic.model.control.TagWhiteSpaceControl;
import org.jtwig.parsing.sequence.TransformSequenceMatcher;
import org.jtwig.parsing.transform.ListTransformationRequest;
import org.jtwig.parsing.transform.Transformations;

import static org.jtwig.parsing.sequence.SequenceMatchers.sequence;
import static org.jtwig.parsing.sequence.SequenceMatchers.skipWhitespaces;
import static org.jtwig.parsing.sequence.SequenceMatchers.string;
import static org.jtwig.parsing.sequence.SequenceMatchers.transform;

public class CommandJtwigParserFactory implements JtwigParserFactory<CommandJtwigNode> {
    private final CommandDefinitionJtwigParserFactory commandParser;
    private final IsTrimWhiteSpaceParserFactory isTrimWhiteSpaceParserFactory;

    public CommandJtwigParserFactory(CommandDefinitionJtwigParserFactory commandParser, IsTrimWhiteSpaceParserFactory isTrimWhiteSpaceParserFactory) {
        this.commandParser = commandParser;
        this.isTrimWhiteSpaceParserFactory = isTrimWhiteSpaceParserFactory;
    }

    @Override
    public TransformSequenceMatcher<CommandJtwigNode> create(ParserConfiguration configuration) {
        return transform(
                sequence(
                        string(configuration.getCodeIslandConfiguration().getStartCode()),
                        isTrimWhiteSpaceParserFactory.create(configuration),
                        skipWhitespaces(commandParser.create(configuration)),
                        isTrimWhiteSpaceParserFactory.create(configuration),
                        string(configuration.getCodeIslandConfiguration().getEndCode())
                ),
                Transformations.fromContentList(new Function<ListTransformationRequest, CommandJtwigNode>() {
                    @Override
                    public CommandJtwigNode apply(ListTransformationRequest input) {
                        return new CommandJtwigNode(
                                new TagWhiteSpaceControl(input.get(0, Boolean.class), input.get(2, Boolean.class)),
                                input.get(1, JtwigCommandDefinition.class));
                    }
                })
        );
    }
}
