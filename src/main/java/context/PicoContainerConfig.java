package context;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import stepDefinition.Hooks;
import stepDefinition.StepDefinitions;
import util.BrowserFactory;

public class PicoContainerConfig {

    private static MutablePicoContainer container;

    // Initialize the container and register components
    public static MutablePicoContainer getContainer() {
        if (container == null) {
            container = new DefaultPicoContainer();
            container.addComponent(BrowserFactory.class);
            container.addComponent(StepDefinitions.class);
            container.addComponent(Hooks.class);
        }
        return container;
    }
}
