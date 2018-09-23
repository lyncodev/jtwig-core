package org.jtwig.parser.config;

import com.google.common.base.Optional;
import org.jtwig.parser.cache.TemplateCache;
import org.jtwig.parser.config.command.CommandNodeConfiguration;
import org.jtwig.parser.config.content.ContentNodeConfiguration;
import org.jtwig.render.expression.calculator.operation.binary.BinaryOperator;
import org.jtwig.render.expression.calculator.operation.unary.UnaryOperator;

import java.util.List;

public class JtwigParserConfiguration {
    private final SyntaxConfiguration syntax;
    private final List<UnaryOperator> unaryOperators;
    private final List<BinaryOperator> binaryOperators;
    private final List<CommandNodeConfiguration> commands;
    private final List<ContentNodeConfiguration> contents;
    private final Optional<TemplateCache> templateCache;

    public JtwigParserConfiguration(SyntaxConfiguration syntax, List<UnaryOperator> unaryOperators, List<BinaryOperator> binaryOperators, List<CommandNodeConfiguration> commands, List<ContentNodeConfiguration> contents, Optional<TemplateCache> templateCache) {
        this.unaryOperators = unaryOperators;
        this.binaryOperators = binaryOperators;
        this.syntax = syntax;
        this.commands = commands;
        this.contents = contents;
        this.templateCache = templateCache;
    }

    public List<UnaryOperator> getUnaryOperators() {
        return unaryOperators;
    }

    public List<BinaryOperator> getBinaryOperators() {
        return binaryOperators;
    }

    public SyntaxConfiguration getSyntax() {
        return syntax;
    }

    public List<CommandNodeConfiguration> getCommands() {
        return commands;
    }

    public List<ContentNodeConfiguration> getContents() {
        return contents;
    }

    public Optional<TemplateCache> getTemplateCache() {
        return templateCache;
    }
}
