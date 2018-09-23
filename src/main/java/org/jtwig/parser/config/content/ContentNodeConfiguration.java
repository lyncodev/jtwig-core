package org.jtwig.parser.config.content;

import org.jtwig.model.tree.Node;
import org.jtwig.parser.config.TagConfiguration;
import org.jtwig.parser.parsky.tag.content.ContentNodeFactory;
import org.parsky.grammar.RuleFactory;

import java.util.List;

public class ContentNodeConfiguration implements TagConfiguration {
    private final String keyword;
    private final List<String> tags;
    private final Class<? extends Node> type;
    private final RuleFactory startRule;
    private final RuleFactory contentRule;
    private final RuleFactory endRule;
    private final ContentNodeFactory factory;

    public ContentNodeConfiguration(String keyword, List<String> tags, Class<? extends Node> type, RuleFactory startRule, RuleFactory contentRule, RuleFactory endRule, ContentNodeFactory factory) {
        this.keyword = keyword;
        this.tags = tags;
        this.type = type;
        this.startRule = startRule;
        this.contentRule = contentRule;
        this.endRule = endRule;
        this.factory = factory;
    }

    public String getKeyword() {
        return keyword;
    }

    public Class<? extends Node> getType() {
        return type;
    }

    public RuleFactory getStartRule() {
        return startRule;
    }

    public RuleFactory getContentRule() {
        return contentRule;
    }

    public RuleFactory getEndRule() {
        return endRule;
    }

    public ContentNodeFactory getFactory() {
        return factory;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }
}
