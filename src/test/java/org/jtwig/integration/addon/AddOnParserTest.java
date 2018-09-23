package org.jtwig.integration.addon;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.integration.AbstractIntegrationTest;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.config.DefaultJtwigParserConfiguration;
import org.jtwig.parser.config.command.CommandNodeConfiguration;
import org.jtwig.parser.parsky.JtwigGrammar;
import org.jtwig.parser.parsky.tag.command.CommandNodeFactory;
import org.jtwig.render.RenderRequest;
import org.jtwig.render.node.renderer.NodeRender;
import org.jtwig.renderable.Renderable;
import org.jtwig.renderable.impl.StringRenderable;
import org.junit.Test;
import org.parsky.engine.print.PrintParserEngine;
import org.parsky.grammar.Grammar;
import org.parsky.grammar.RuleFactory;
import org.parsky.grammar.rules.Rule;
import org.parsky.grammar.rules.Rules;
import org.parsky.grammar.rules.transform.Transform;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jtwig.environment.EnvironmentConfigurationBuilder.configuration;

public class AddOnParserTest extends AbstractIntegrationTest {
    @Test
    public void addOn() throws Exception {
        String result = JtwigTemplate.inlineTemplate("{% hello %}", configuration()
                .parser().commands().add(new CommandNodeConfiguration(
                        SimpleAddOn.class,
                        "hello",
                        new RuleFactory() {
                            @Override
                            public Rule create(Grammar context) {
                                return Rules.empty();
                            }
                        },
                        new CommandNodeFactory() {
                            @Override
                            public Transform.Result create(Position position, Object input) {
                                return Transform.Result.success(
                                        new SimpleAddOn(position)
                                );
                            }
                        }
                )).and().and()
                .render().nodeRenders().add(SimpleAddOn.class, new AddOnNodeRender()).and().and()
                .build()).render(JtwigModel.newModel());

        assertThat(result, is("Hello World!"));
    }

    public static class AddOnNodeRender implements NodeRender<SimpleAddOn> {

        @Override
        public Renderable render(RenderRequest renderRequest, SimpleAddOn node) {
            return new StringRenderable("Hello World!");
        }
    }

    public static class SimpleAddOn extends Node {
        protected SimpleAddOn(Position position) {
            super(position);
        }
    }

    @Test
    public void grammar() throws Exception {
        System.out.println(PrintParserEngine.print(JtwigGrammar.jtwigGrammar().create(new DefaultJtwigParserConfiguration())));
    }
}
