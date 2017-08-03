package org.jtwig.parser.dynamic.config;

public class DefaultCodeIslandConfiguration extends CodeIslandConfiguration {
    public DefaultCodeIslandConfiguration() {
        super("{%", "%}", "{{", "}}", "{#", "#}");
    }
}
