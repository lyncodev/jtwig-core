package org.jtwig.parser.parsky.tag.command;

import org.jtwig.model.position.Position;
import org.parsky.grammar.rules.transform.Transform;

public interface CommandNodeFactory {
    Transform.Result create(Position position, Object input);
}
