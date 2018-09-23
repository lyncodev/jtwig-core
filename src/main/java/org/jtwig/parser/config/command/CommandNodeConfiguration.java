package org.jtwig.parser.config.command;

import org.jtwig.model.tree.Node;
import org.jtwig.parser.config.TagConfiguration;
import org.jtwig.parser.parsky.tag.command.CommandNodeFactory;
import org.parsky.grammar.RuleFactory;

import java.util.Collections;
import java.util.List;

public class CommandNodeConfiguration implements TagConfiguration {
    private final Class<? extends Node> type;
    private final String keyword;
    private final RuleFactory rule;
    private final CommandNodeFactory nodeFactory;

    public CommandNodeConfiguration(Class<? extends Node> type, String keyword, RuleFactory rule, CommandNodeFactory nodeFactory) {
        this.keyword = keyword;
        this.rule = rule;
        this.nodeFactory = nodeFactory;
        this.type = type;
    }

    public Class<? extends Node> getType() {
        return type;
    }

    public String getKeyword() {
        return keyword;
    }

    public RuleFactory getRule() {
        return rule;
    }

    public CommandNodeFactory getNodeFactory() {
        return nodeFactory;
    }

    @Override
    public List<String> getTags() {
        return Collections.singletonList(keyword);
    }
}
