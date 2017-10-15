package org.jtwig.parser.parsky.position;

public class IsNewLineService {
    public boolean isNewLine(char[] content, int index) {
        return content[index] == '\n';
    }
}
