package org.jtwig.parser.dynamic.config;

public class ParserConfigurationBuilder {
    private  CodeIslandConfiguration codeIslandConfiguration;
    private String trimWhiteSpace;

    public ParserConfigurationBuilder () {}
    public ParserConfigurationBuilder (ParserConfiguration prototype) {
        this.codeIslandConfiguration = prototype.getCodeIslandConfiguration();
    }

    public ParserConfigurationBuilder withCodeIslandConfiguration(CodeIslandConfiguration codeIslandConfiguration) {
        this.codeIslandConfiguration = codeIslandConfiguration;
        return this;
    }

    public ParserConfigurationBuilder withTrimWhiteSpace(String trimWhiteSpace) {
        this.trimWhiteSpace = trimWhiteSpace;
        return this;
    }

    public ParserConfiguration build () {
        return new ParserConfiguration(codeIslandConfiguration, trimWhiteSpace);
    }
}
