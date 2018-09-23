package org.jtwig.parser.parsky;

import com.google.common.collect.ImmutableList;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.tree.*;
import org.jtwig.parser.config.JtwigParserConfiguration;
import org.jtwig.parser.config.TagConfiguration;
import org.jtwig.parser.config.command.*;
import org.jtwig.parser.config.content.*;
import org.jtwig.parser.parsky.basic.KeywordRuleExpressionFactory;
import org.jtwig.parser.parsky.expression.JtwigExpressionGrammar;
import org.jtwig.parser.parsky.tag.*;
import org.jtwig.parser.parsky.tag.command.CommandNodeRuleExpression;
import org.jtwig.parser.parsky.tag.content.ContentNodeRuleExpression;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.GrammarBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.parsky.grammar.RuleFactories.anyOf;
import static org.parsky.grammar.RuleFactories.reference;

public class JtwigGrammar {
    public static final String PRE_UNARY = "preUnary";
    public static final String PRE_BINARY = "preBinary";
    public static final String PRE_PRIMARY = "prePrimary";
    public static final String PRE_TEST = "preTest";
    public static final String START_OUTPUT = "startOutput";
    public static final String START_COMMENT = "startComment";
    public static final String START_CODE = "startCode";
    public static final String END_COMMENT = "endComment";
    public static final String COMMENT = "comment";
    public static final String END_CODE = "endCode";
    public static final String END_OUTPUT = "endOutput";
    public static final String PRIVATE_CONTENT = "privateNode";
    public static final String UNKNOWN_TAG = "unknownTag";
    public static final String PRE_MAP_SELECTION = "preMap";
    public static final String SELECTION = "selection";
    public static final String SIMPLE_FUNCTION = "simpleFunction";
    public static final String PRIMARY_CONSTANT = "primaryConstant";
    public static final String PRE_PRIMARY_CONSTANT = "prePrimaryConstant";
    public static final List<CommandNodeConfiguration> NATIVE_COMMANDS = Arrays.<CommandNodeConfiguration>asList(
            new DoNodeConfiguration(),
            new SetNodeConfiguration(),
            new FlushNodeConfiguration(),
            new IncludeNodeConfiguration(),
            new ImportNodeConfiguration()
    );
    public static final List<ContentNodeConfiguration> NATIVE_CONTENTS = Arrays.<ContentNodeConfiguration>asList(
            new BlockNodeConfiguration(),
            new IfNodeConfiguration(),
            new ForLoopNodeConfiguration(),
            new MacroNodeConfiguration(),
            new EmbedNodeConfiguration(),
            new AutoEscapeNodeConfiguration(),
            new ContentEscapeNodeConfiguration(),
            new FilterNodeConfiguration(),
            new VerbatimNodeConfiguration()
    );
    public static final List<ContentNodeConfiguration> NATIVE_PRIVATE_CONTENTS = Arrays.<ContentNodeConfiguration>asList(
            new BlockOverrideNodeConfiguration()
    );

    public static JtwigGrammar jtwigGrammar () {
        return new JtwigGrammar(new JtwigExpressionGrammar());
    }

    private final JtwigExpressionGrammar jtwigExpressionGrammar;

    private JtwigGrammar(JtwigExpressionGrammar jtwigExpressionGrammar) {
        this.jtwigExpressionGrammar = jtwigExpressionGrammar;
    }

    public Grammar create(JtwigParserConfiguration configuration) {
        KeywordRuleExpressionFactory keywordRuleExpressionFactory = new KeywordRuleExpressionFactory();

        GrammarBuilder builder = GrammarBuilder.newGrammar();
        jtwigExpressionGrammar.populate(builder, configuration);

        builder
                // 3. Code Island symbols
                .define(START_OUTPUT, new TagSymbolRuleExpression(configuration.getSyntax().getStartOutput(), true))
                .define(END_OUTPUT, new TagSymbolRuleExpression(configuration.getSyntax().getEndOutput(), false))
                .define(START_COMMENT, new TagSymbolRuleExpression(configuration.getSyntax().getStartComment(), true))
                .define(END_COMMENT, new TagSymbolRuleExpression(configuration.getSyntax().getEndComment(), false))
                .define(START_CODE, new TagSymbolRuleExpression(configuration.getSyntax().getStartCode(), true))
                .define(END_CODE, new TagSymbolRuleExpression(configuration.getSyntax().getEndCode(), false))
                .define(COMMENT, new CommentRuleExpression(START_COMMENT, END_COMMENT))
                // 3. Tags (Nodes)
                .define(UNKNOWN_TAG, UnknownTagRuleExpression.create(ImmutableList.<TagConfiguration>builder()
                        .addAll(NATIVE_COMMANDS)
                        .addAll(NATIVE_CONTENTS)
                        .addAll(configuration.getCommands())
                        .addAll(configuration.getContents())
                        .build()))
                .define(Node.class, anyOf(
                        ImmutableList.builder()
                                .add(TextNode.class, OutputNode.class)
                                .addAll(typesFromCommands(NATIVE_COMMANDS))
                                .addAll(typesFromContents(NATIVE_CONTENTS))
                                .addAll(typesFromCommands(configuration.getCommands()))
                                .addAll(typesFromContents(configuration.getContents()))
                                .add(UNKNOWN_TAG)
                                .build()
                                .toArray()))
                .define(PRIVATE_CONTENT, anyOf(
                        ImmutableList.builder()
                                .addAll(typesFromContents(NATIVE_PRIVATE_CONTENTS))
                                .build().toArray()))
                // 3.1. Text Node
                .define(TextNode.class, new TextNodeRuleExpression(configuration.getSyntax()))
                // 3.2. Output Node
                .define(OutputNode.class, new OutputNodeRuleExpression(START_OUTPUT, END_OUTPUT))
                .define(CompositeNode.class, new CompositeNodeRuleExpression(COMMENT))
                .define(ExtendsNode.class, new ExtendsRuleExpression(keywordRuleExpressionFactory));

        for (ContentNodeConfiguration nativePrivateContent : NATIVE_PRIVATE_CONTENTS) {
            builder.define(nativePrivateContent.getType(), ContentNodeRuleExpression.create(keywordRuleExpressionFactory, nativePrivateContent));
        }

        for (CommandNodeConfiguration nativeCommand : NATIVE_COMMANDS) {
            builder.define(nativeCommand.getType(), CommandNodeRuleExpression.create(keywordRuleExpressionFactory, nativeCommand));
        }

        for (ContentNodeConfiguration nativeContent : NATIVE_CONTENTS) {
            builder.define(nativeContent.getType(), ContentNodeRuleExpression.create(keywordRuleExpressionFactory, nativeContent));
        }

        // Extensions (Commands)
        for (CommandNodeConfiguration commandNodeConfiguration : configuration.getCommands()) {
            builder.define(commandNodeConfiguration.getType(), CommandNodeRuleExpression.create(keywordRuleExpressionFactory, commandNodeConfiguration));
        }

        // Extensions (Content)
        for (ContentNodeConfiguration contentNodeConfiguration : configuration.getContents()) {
            builder.define(contentNodeConfiguration.getType(), ContentNodeRuleExpression.create(keywordRuleExpressionFactory, contentNodeConfiguration));
        }
        return builder.define("start", new StartRuleExpression()).build("start");
    }

    private List<Class> typesFromContents(List<ContentNodeConfiguration> contents) {
        ArrayList<Class> list = new ArrayList<>();
        for (ContentNodeConfiguration content : contents) {
            list.add(content.getType());
        }
        return list;
    }

    private List<Class> typesFromCommands(List<CommandNodeConfiguration> singleTags) {
        ArrayList<Class> list = new ArrayList<>();
        for (CommandNodeConfiguration singleTag : singleTags) {
            list.add(singleTag.getType());
        }
        return list;
    }
}
