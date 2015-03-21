package no.priv.bang.modeling.modelstore;

import static org.junit.Assert.*;

import java.util.UUID;

import no.priv.bang.modeling.modelstore.impl.ImplementationFactory;

import org.junit.Test;

/**
 * Unit test for the {@link PropertysetManager} interface and its
 * implementations.
 *
 * @author Steinar Bang
 *
 */
public class PropertysetManagerTest {

    @Test
    public void testCreatePropertySet() {
        PropertysetManager propertysetManager = DefaultPropertysetManager.getInstance();

        // Get a propertyset instance and verify that it is a non-nil instance
        // that can be modified.
        Propertyset propertyset = propertysetManager.createPropertyset();
        assertFalse(propertyset.isNil());
        assertFalse(propertyset.hasId());
        assertEquals(PropertyvalueNil.getNil().asId(), propertyset.getId());
        // First get the default value for a non-existing property
        assertEquals("", propertyset.getStringProperty("stringProperty"));
        // Set the value as a different type
        propertyset.setDoubleProperty("stringProperty", Double.valueOf(3.14));
        // Verify that the property now contains a non-default value
        assertEquals("3.14", propertyset.getStringProperty("stringProperty"));
    }

    @Test
    public void testFindPropertysetById() {
        PropertysetManager propertysetManager = DefaultPropertysetManager.getInstance();

        // Get a propertyset by id and verify that it is empty initially
        UUID newPropertysetId = UUID.randomUUID();
        Propertyset propertyset = propertysetManager.findPropertyset(newPropertysetId);
        assertTrue(propertyset.hasId());
        assertEquals(newPropertysetId, propertyset.getId());

        // Check that property values can be set and retrieved
        assertEquals("", propertyset.getStringProperty("stringProperty"));
        propertyset.setStringProperty("stringProperty", "foo bar");
        assertEquals("foo bar", propertyset.getStringProperty("stringProperty"));

        // Check that the "id" property can't be set
        propertyset.setStringProperty("id", "foo bar");
        assertEquals("Expected the \"id\" property to still hold the id", newPropertysetId.toString(), propertyset.getStringProperty("id"));
        propertyset.setBooleanProperty("id", Boolean.TRUE);
        assertEquals("Expected the \"id\" property to not be affected by setting a boolean value", Boolean.FALSE, propertyset.getBooleanProperty("id"));
        propertyset.setLongProperty("id", Long.valueOf(7));
        assertEquals("Expected the \"id\" property to not be affected by setting an integer value", Long.valueOf(0), propertyset.getLongProperty("id"));
        propertyset.setDoubleProperty("id", Double.valueOf(3.14));
        assertEquals("Expected the \"id\" property to not be affected by setting an integer value", Double.valueOf(0.0), propertyset.getDoubleProperty("id"));
        Propertyset complexValue = propertysetManager.createPropertyset();
        propertyset.setComplexProperty("id", complexValue);
        Propertyset returnedComplexProperty = propertyset.getComplexProperty("id");
        assertEquals("Expected the \"id\" property not to be affected by setting a complex value", PropertysetNil.getNil(), returnedComplexProperty);
        assertFalse(returnedComplexProperty.hasId());
        assertEquals(PropertyvalueNil.getNil().asId(), returnedComplexProperty.getId());
        Propertyset referencedPropertyset = propertysetManager.findPropertyset(UUID.randomUUID());
        referencedPropertyset.setReferenceProperty("id", referencedPropertyset);
        assertEquals("Expected the \"id\" property not to be affected by setting an object reference", PropertysetNil.getNil(), propertyset.getReferenceProperty("id"));
        PropertyvalueList listValue = ImplementationFactory.newList();
        propertyset.setListProperty("id", listValue);
        assertEquals("Expected the \"id\" property not to be affected by setting an object reference", PropertyvalueNil.getNil().asList(), propertyset.getListProperty("id"));

        // Verify that asking for the same id again will return the same object
        assertEquals(propertyset, propertysetManager.findPropertyset(newPropertysetId));
    }

}
