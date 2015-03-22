package no.priv.bang.modeling.modelstore;

import static org.junit.Assert.*;

import java.util.Collection;
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

    @Test
    public void testFindPropertysetOfAspect() {
        PropertysetManager propertysetManager = DefaultPropertysetManager.getInstance();

        buildModelWithAspects(propertysetManager);

        // Get all aspects currently in the manager
        Collection<Propertyset> aspects = propertysetManager.listAllAspects();
        assertEquals(3, aspects.size());

        Propertyset vehicle = findAspectByTitle(aspects, "vehicle");
        Collection<Propertyset> vehicles = propertysetManager.findObjectsOfAspect(vehicle);
        assertEquals(5, vehicles.size());

        Propertyset car = findAspectByTitle(aspects, "car");
        Collection<Propertyset> cars = propertysetManager.findObjectsOfAspect(car);
        assertEquals(3, cars.size());
    }

    private Propertyset findAspectByTitle(Collection<Propertyset> aspects, String aspectTitle) {
    	for (Propertyset aspect : aspects) {
            if (aspectTitle.equals(aspect.getStringProperty("title"))) {
                return aspect;
            }
        }

    	return PropertysetNil.getNil();
    }

    private void buildModelWithAspects(PropertysetManager propertysetManager) {
        // Base aspect "vehicle"
        UUID aspect1Id = UUID.randomUUID();
        Propertyset aspect1 = propertysetManager.findPropertyset(aspect1Id);
        aspect1.setStringProperty("title", "vehicle");
        aspect1.setStringProperty("aspect", "object");
        Propertyset aspect1Properties = propertysetManager.createPropertyset();
        Propertyset manufacturerDefinition = propertysetManager.createPropertyset();
        manufacturerDefinition.setStringProperty("aspect", "string");
        Propertyset modelnameDefinition = propertysetManager.createPropertyset();
        modelnameDefinition.setStringProperty("aspect", "string");
        aspect1Properties.setComplexProperty("modelname", modelnameDefinition);
        Propertyset wheelCountDefinition = propertysetManager.createPropertyset();
        wheelCountDefinition.setStringProperty("description", "Number of wheels on the vehicle");
        wheelCountDefinition.setStringProperty("aspect", "integer");
        wheelCountDefinition.setLongProperty("minimum", Long.valueOf(0));
        aspect1Properties.setComplexProperty("definition", wheelCountDefinition);
        aspect1.setComplexProperty("properties", aspect1Properties);

        // Subaspect "bicycle"
        UUID aspect2Id = UUID.randomUUID();
        Propertyset aspect2 = propertysetManager.findPropertyset(aspect2Id);
        aspect2.setStringProperty("title", "bicycle");
        aspect2.setStringProperty("aspect", "object");
        aspect2.setReferenceProperty("inherits", aspect1);
        Propertyset aspect2Properties = propertysetManager.createPropertyset();
        Propertyset frameNumber = propertysetManager.createPropertyset();
        frameNumber.setStringProperty("definition", "Unique identifier for the bicycle");
        aspect2.setComplexProperty("properties", aspect2Properties);

        // Subaspect "car"
        UUID aspect3Id = UUID.randomUUID();
        Propertyset aspect3 = propertysetManager.findPropertyset(aspect3Id);
        aspect3.setStringProperty("title", "car");
        aspect3.setStringProperty("aspect", "object");
        aspect3.setReferenceProperty("inherits", aspect1);
        Propertyset aspect3Properties = propertysetManager.createPropertyset();
        Propertyset engineSize = propertysetManager.createPropertyset();
        engineSize.setStringProperty("description", "Engine displacement in litres");
        engineSize.setStringProperty("aspect", "number");
        aspect3Properties.setComplexProperty("engineSize", engineSize);
        Propertyset enginePower = propertysetManager.createPropertyset();
        enginePower.setStringProperty("description", "Engine power in kW");
        enginePower.setStringProperty("aspect", "number");
        aspect3Properties.setComplexProperty("enginePower", enginePower);
        aspect3.setComplexProperty("properties", aspect3Properties);

        // Make some instances with aspects
        Propertyset head = propertysetManager.findPropertyset(UUID.randomUUID());
        head.addAspect(aspect2);
        head.setStringProperty("manufacturer", "HEAD");
        head.setStringProperty("model", "Tacoma I");
        head.setStringProperty("frameNumber", "001-234-509-374-331");
        Propertyset nakamura = propertysetManager.findPropertyset(UUID.randomUUID());
        nakamura.addAspect(aspect2);
        nakamura.setStringProperty("manufacturer", "Nakamura");
        nakamura.setStringProperty("model", "Fatbike 2015");
        nakamura.setStringProperty("frameNumber", "003-577-943-547-931");
        Propertyset ferrari = propertysetManager.findPropertyset(UUID.randomUUID());
        ferrari.addAspect(aspect3);
        ferrari.setStringProperty("manufacturer", "Ferrari");
        ferrari.setStringProperty("model", "550 Barchetta");
        ferrari.setDoubleProperty("engineSize", 5.5);
        ferrari.setDoubleProperty("enginePower", 357.0);
        Propertyset subaru = propertysetManager.findPropertyset(UUID.randomUUID());
        subaru.addAspect(aspect3);
        subaru.setStringProperty("manufacturer", "Subaru");
        subaru.setStringProperty("model", "Outback");
        subaru.setDoubleProperty("engineSize", 2.5);
        subaru.setDoubleProperty("enginePower", 170.0);
        Propertyset volvo = propertysetManager.findPropertyset(UUID.randomUUID());
        volvo.addAspect(aspect3);
        volvo.setStringProperty("manufacturer", "Volvo");
        volvo.setStringProperty("model", "P1800");
        volvo.setDoubleProperty("engineSize", 1.8);
        volvo.setDoubleProperty("enginePower", 84.58);
    }

}
