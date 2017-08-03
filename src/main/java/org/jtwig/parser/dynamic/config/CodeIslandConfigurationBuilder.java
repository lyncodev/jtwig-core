package org.jtwig.parser.dynamic.config;

public class CodeIslandConfigurationBuilder {
    private String startCode;
    private String endCode;

    private String startOutput;
    private String endOutput;

    private String startComment;
    private String endComment;

    public CodeIslandConfigurationBuilder () {}
    public CodeIslandConfigurationBuilder (CodeIslandConfiguration prototype) {
        this.startCode = prototype.getStartCode();
        this.endCode = prototype.getEndCode();
        this.startOutput = prototype.getStartOutput();
        this.endOutput = prototype.getEndOutput();
        this.startComment = prototype.getStartComment();
        this.endComment = prototype.getEndComment();
    }

    public CodeIslandConfigurationBuilder withCode (String start, String end) {
        this.startCode = start;
        this.endCode = end;
        return this;
    }

    public CodeIslandConfigurationBuilder withComment (String start, String end) {
        this.startComment = start;
        this.endComment = end;
        return this;
    }

    public CodeIslandConfigurationBuilder withOutput (String start, String end) {
        this.startOutput = start;
        this.endOutput = end;
        return this;
    }

    public CodeIslandConfiguration build () {
        return new CodeIslandConfiguration(startCode, endCode, startOutput, endOutput, startComment, endComment);
    }
}
