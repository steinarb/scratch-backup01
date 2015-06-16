package no.priv.bang.modeling.modelstore.testutils;
import static org.junit.Assert.*;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetManager;
import no.priv.bang.modeling.modelstore.Propertyvalue;

/**
 * Contains static methods used in more than one unit test.
 *
 * @author Steinar Bang
 *
 */
public class TestUtils {

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
                Propertyvalue originalValue = propertyset.getProperty(propertyname);
                Propertyvalue parsedValue = parsedPropertyset.getProperty(propertyname);
                assertEquals(originalValue, parsedValue);
            }
        }
    }

}
