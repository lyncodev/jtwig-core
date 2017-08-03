package org.jtwig.parser.dynamic.config;

public class ParserConfiguration {
    private final CodeIslandConfiguration codeIslandConfiguration;
    private final String trimWhiteSpace;

    public ParserConfiguration(CodeIslandConfiguration codeIslandConfiguration, String trimWhiteSpace) {
        this.codeIslandConfiguration = codeIslandConfiguration;
        this.trimWhiteSpace = trimWhiteSpace;
    }

    public CodeIslandConfiguration getCodeIslandConfiguration() {
        return codeIslandConfiguration;
    }

    public String getTrimWhiteSpace() {
        return trimWhiteSpace;
    }
}
