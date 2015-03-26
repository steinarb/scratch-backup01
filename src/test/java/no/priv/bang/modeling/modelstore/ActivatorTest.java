package no.priv.bang.modeling.modelstore;

import static org.junit.Assert.*;

import java.util.UUID;

import org.apache.sling.testing.mock.osgi.junit.OsgiContext;
import org.junit.Rule;
import org.junit.Test;

public class ActivatorTest {

    @Rule
    public final OsgiContext context = new OsgiContext();

    @Test
    public void testPropertysetManagerLifecycle() throws Exception {
        Activator activator = new Activator();

        // Verify that no service is present before the activation.
        PropertysetManager propertysetManagerServiceBeforeActivation = context.getService(PropertysetManager.class);
        assertNull(propertysetManagerServiceBeforeActivation);

        // Test that after activation a live PropertysetManager service exists in the context.
        activator.start(context.bundleContext());
        PropertysetManager propertysetManagerService = context.getService(PropertysetManager.class);
        assertNotNull(propertysetManagerService);
        Propertyset propertyset = propertysetManagerService.findPropertyset(UUID.randomUUID());
        propertyset.setStringProperty("stringPropertyOfPi", "3.14");
        assertEquals(Double.valueOf(3.14), propertyset.getDoubleProperty("stringPropertyOfPi"));

        // Test that bundle deactivation will unregister the service
        activator.stop(context.bundleContext());
        PropertysetManager propertysetManagerServiceAfterActivation = context.getService(PropertysetManager.class);
        assertNull(propertysetManagerServiceAfterActivation);
    }

}
