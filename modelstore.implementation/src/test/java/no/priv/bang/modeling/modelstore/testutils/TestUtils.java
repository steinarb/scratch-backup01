package no.priv.bang.modeling.modelstore.testutils;
import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetManager;
import no.priv.bang.modeling.modelstore.Value;

/**
 * Contains static methods used in more than one unit test.
 *
 * @author Steinar Bang
 *
 */
public class TestUtils {

    /**
     * Get a {@link File} referencing a resource.
     *
     * @param resource the name of the resource to get a File for
     * @return a {@link File} object referencing the resource
     * @throws URISyntaxException
     */
    public static File getResourceAsFile(String resource) throws URISyntaxException {
        return Paths.get(TestUtils.class.getResource(resource).toURI()).toFile();
    }

    /**
     * Iterate over all of the {@link Propertyset} instances of a
     * {@link PropertysetManager} and compare them to the propertyesets
     * of a different PropertysetManager and assert that they match.
     *
     * @param propertysetManager the {@link PropertysetManager} to iterate over
     * @param propertysetManager2 the {@link PropertysetManager} to compare with
     */
    public static void compareAllPropertysets(PropertysetManager propertysetManager, PropertysetManager propertysetManager2) {
        for (Propertyset propertyset : propertysetManager.listAllPropertysets()) {
            Propertyset parsedPropertyset = propertysetManager2.findPropertyset(propertyset.getId());
            for (String propertyname : propertyset.getPropertynames()) {
                Value originalValue = propertyset.getProperty(propertyname);
                Value parsedValue = parsedPropertyset.getProperty(propertyname);
                assertEquals(originalValue, parsedValue);
            }
        }
    }

}
