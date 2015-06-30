package no.priv.bang.modeling.modelstore;


import static org.junit.Assert.*;

import no.priv.bang.modeling.modelstore.impl.PropertysetManagerProvider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Unit test for the {@link PropertysetManager} interface and its
 * implementations.
 *
 * @author Steinar Bang
 *
 */
public class PropertysetManagerTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testGetPropertysetContext() {
        PropertysetManager propertysetManager = new PropertysetManagerProvider().get();
        PropertysetContext context = propertysetManager.getDefaultContext();
        assertNotNull(context);
    }

}
