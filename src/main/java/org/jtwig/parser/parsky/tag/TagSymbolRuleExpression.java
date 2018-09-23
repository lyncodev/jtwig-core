package org.jtwig.parser.parsky.tag;

import org.jtwig.parser.parsky.ParserContext;
import org.parsky.engine.ParserRequest;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

public class TagSymbolRuleExpression implements RuleFactory {
    private final String tag;
    private final boolean start;

    public TagSymbolRuleExpression(String tag, boolean start) {
        this.tag = tag;
        this.start = start;
    }

    @Override
    public Rule create(Grammar context) {
        if (start) {
            return Rules.firstOf(
                    Rules.transform(
                            Rules.sequence(
                                    Rules.string(tag),
                                    Rules.string("-")
                            ),
                            new Transform() {
                                @Override
                                public Result transform(ParserRequest request, Object input) {
                                    if (!request.isTestMode()) {
                                        request.getContext().peek(ParserContext.class).get().start(true);
                                    }
                                    return Transform.Result.success(input);
                                }
                            }
                    ),
                    Rules.transform(
                            Rules.string(tag),
                            new Transform() {
                                @Override
                                public Result transform(ParserRequest request, Object input) {
                                    if (!request.isTestMode()) {
                                        request.getContext().peek(ParserContext.class).get().start(false);
                                    }
                                    return Transform.Result.success(input);
                                }
                            }
                    )
            );
        } else {
            return Rules.firstOf(
                    Rules.transform(
                            Rules.sequence(
                                    Rules.string("-"),
                                    Rules.string(tag)
                            ),
                            new Transform() {
                                @Override
                                public Result transform(ParserRequest request, Object input) {
                                    if (!request.isTestMode()) {
                                        request.getContext().peek(ParserContext.class).get().end(true);
                                    }
                                    return Transform.Result.success(input);
                                }
                            }
                    ),
                    Rules.transform(
                            Rules.string(tag),
                            new Transform() {
                                @Override
                                public Result transform(ParserRequest request, Object input) {
                                    if (!request.isTestMode()) {
                                        request.getContext().peek(ParserContext.class).get().end(false);
                                    }
                                    return Transform.Result.success(input);
                                }
                            }
                    )
            );
        }
    }
}
