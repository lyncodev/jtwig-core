package org.jtwig.parser.dynamic.config;

public class ParserConfigurationBuilder {
    public CodeIslandConfiguration codeIslandConfiguration;

    public ParserConfigurationBuilder () {}
    public ParserConfigurationBuilder (ParserConfiguration prototype) {
        this.codeIslandConfiguration = prototype.getCodeIslandConfiguration();
    }

    public ParserConfigurationBuilder withCodeIslandConfiguration(CodeIslandConfiguration codeIslandConfiguration) {
        this.codeIslandConfiguration = codeIslandConfiguration;
        return this;
    }

    public ParserConfiguration build () {
        return new ParserConfiguration(codeIslandConfiguration, trimWhiteSpace);
    }
}
