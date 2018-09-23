package org.jtwig.parser.parsky;

public enum Keyword {
    TRUE("true"),
    FALSE("false"),
    IN("in"),
    AS("as"),
    NULL("null"),
    IS("is"),
    NOT("not"),
    ;

    private final String symbol;

    Keyword(String symbol) {
        this.symbol = symbol;
    }


    @Override
    public String toString() {
        return symbol;
    }
}
