package org.jtwig.parser.parsky.tag.content;

import org.jtwig.model.position.Position;
import org.parsky.grammar.rules.transform.Transform;

public interface ContentNodeFactory {
    Transform.Result create(Position position, Object start, Object content, Object end);
}
