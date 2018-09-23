package org.jtwig.parser.config;

import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.Builder;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.parser.config.command.CommandNodeConfiguration;
import org.jtwig.parser.config.content.ContentNodeConfiguration;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;
import org.jtwig.util.builder.ListBuilder;

public class JtwigParserConfigurationBuilder<B extends JtwigParserConfigurationBuilder> implements Builder<JtwigParserConfiguration> {
    private final AndSyntaxConfigurationBuilder<B> syntax;
    private final ListBuilder<B, UnaryOperator> unaryOperators;
    private final ListBuilder<B, BinaryOperator> binaryOperators;
    private final ListBuilder<B, CommandNodeConfiguration> commands;
    private final ListBuilder<B, ContentNodeConfiguration> contents;
    private Optional<TemplateCache> templateCache = Optional.absent();

    public JtwigParserConfigurationBuilder() {
        this.syntax = new AndSyntaxConfigurationBuilder<>(self());
        this.unaryOperators = new ListBuilder<>(self());
        this.binaryOperators = new ListBuilder<>(self());
        this.commands = new ListBuilder<>(self());
        this.contents = new ListBuilder<>(self());
    }

    public JtwigParserConfigurationBuilder(JtwigParserConfiguration configuration) {
        this.syntax = new AndSyntaxConfigurationBuilder<>(self(), configuration.getSyntax());
        this.unaryOperators = new ListBuilder<>(self(), configuration.getUnaryOperators());
        this.binaryOperators = new ListBuilder<>(self(), configuration.getBinaryOperators());
        this.commands = new ListBuilder<>(self(), configuration.getCommands());
        this.contents = new ListBuilder<>(self(), configuration.getContents());
        this.templateCache = configuration.getTemplateCache();
    }

    public AndSyntaxConfigurationBuilder<B> syntax() {
        return syntax;
    }

    public ListBuilder<B, UnaryOperator> unaryOperators() {
        return unaryOperators;
    }

    public ListBuilder<B, BinaryOperator> binaryOperators() {
        return binaryOperators;
    }

    public ListBuilder<B, CommandNodeConfiguration> commands() {
        return commands;
    }

    public ListBuilder<B, ContentNodeConfiguration> contents() {
        return contents;
    }

    public B withTemplateCache(TemplateCache templateCache) {
        this.templateCache = Optional.fromNullable(templateCache);
        return self();
    }

    public B withoutTemplateCache() {
        this.templateCache = Optional.absent();
        return self();
    }

    @Override
    public JtwigParserConfiguration build() {
        return new JtwigParserConfiguration(
                syntax.build(),
                unaryOperators.build(),
                binaryOperators.build(),
                commands.build(),
                contents.build(),
                templateCache);
    }

    private B self() {
        return (B) this;
    }
}
