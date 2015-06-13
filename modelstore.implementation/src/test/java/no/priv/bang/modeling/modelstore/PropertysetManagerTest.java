package no.priv.bang.modeling.modelstore;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.UUID;

import no.priv.bang.modeling.modelstore.impl.ImplementationFactory;
import no.priv.bang.modeling.modelstore.impl.JsonGeneratorWithReferences;
import no.priv.bang.modeling.modelstore.impl.PropertysetManagerProvider;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

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
    public void testCreatePropertySet() {
        PropertysetManager propertysetManager = new PropertysetManagerProvider();

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
        PropertysetManager propertysetManager = new PropertysetManagerProvider();

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
        PropertysetManager propertysetManager = new PropertysetManagerProvider();

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

    /**
     * Test setting multiple aspects on a Propertyset
     */
    @Test
    public void testPropertysetWithMultipleAspects() {
        PropertysetManager propertysetManager = new PropertysetManagerProvider();

        // Create two aspects
        Propertyset generalObjectAspect = buildGeneralObjectAspect(propertysetManager);
        Propertyset positionAspect = buildPositionAspect(propertysetManager);

        // Get a brand new aspectless Propertyset
        Propertyset propertyset = propertysetManager.findPropertyset(UUID.randomUUID());

        // Verify that the propertyset has no aspects
        assertEquals(0, propertyset.getAspects().size());

        // Add an aspect and verify that the propertyset now has an aspect
        propertyset.addAspect(generalObjectAspect);
        assertEquals(1, propertyset.getAspects().size());

        // Add a new aspect and verify that the propertyset now has two aspects
        propertyset.addAspect(positionAspect);
        assertEquals(2, propertyset.getAspects().size());

        // Add an already existing aspect again and observe that the propertyset
        // still only has two aspects.
        propertyset.addAspect(generalObjectAspect);
        assertEquals(2, propertyset.getAspects().size());
    }

    @Test
    public void experimentalJacksonPersist() throws IOException {
        PropertysetManager propertysetManager = new PropertysetManagerProvider();
        buildModelWithAspects(propertysetManager);

        Collection<Propertyset> propertysets = propertysetManager.listAllPropertysets();

        JsonFactory jsonFactory = new JsonFactory();
        File propertysetsFile = folder.newFile("propertysets.json");
        String propertysetsFileNameFullPath = propertysetsFile.getAbsolutePath();
        JsonGenerator generator = new JsonGeneratorWithReferences(jsonFactory.createGenerator(propertysetsFile, JsonEncoding.UTF8));
        assertTrue(generator.canWriteObjectId());
        generator.useDefaultPrettyPrinter();
        generator.writeStartArray();
        for (Propertyset propertyset : propertysets) {
            outputPropertyset(generator, propertyset);
        }

        generator.writeEndArray();
        generator.close();

        String contents = new String(Files.readAllBytes(Paths.get(propertysetsFileNameFullPath)));
        System.out.println(contents);
    }

    @Test
    public void testJsonGeneratorWithReference() throws IOException {
        // Create two propertysets with ids, and make a reference to propertyset
    	// "b" from propertyset "a".
    	PropertysetManager propertysetManager = new PropertysetManagerProvider();
        UUID idA = UUID.randomUUID();
        Propertyset a = propertysetManager.findPropertyset(idA);
        UUID idB = UUID.randomUUID();
        Propertyset b = propertysetManager.findPropertyset(idB);
        a.setReferenceProperty("b", b);

        // Create a factory
        JsonFactory jsonFactory = new JsonFactory();

        // Write an objectId
        File objectIdFile = folder.newFile("objectid.json");
        String ojectIdFileNameFullPath = objectIdFile.getAbsolutePath();
        JsonGenerator generator = new JsonGeneratorWithReferences(jsonFactory.createGenerator(objectIdFile, JsonEncoding.UTF8));
        assertTrue(generator.canWriteObjectId());
        generator.writeObjectId(a.getId());
        generator.close();

        // Check that the written objectId looks like expected (a JSON quoted string)
        String expectedObjectIdAsJson = "\"" + idA.toString() + "\"";
        String objectId = new String(Files.readAllBytes(Paths.get(ojectIdFileNameFullPath)));
        assertEquals(expectedObjectIdAsJson, objectId);

        // Write an object reference
        File objectReferenceFile = folder.newFile("objectreference.json");
        String objectReferenceFilenameFullPath = objectReferenceFile.getAbsolutePath();
        JsonGenerator generator2 = new JsonGeneratorWithReferences(jsonFactory.createGenerator(objectReferenceFile, JsonEncoding.UTF8));
        assertTrue(generator2.canWriteObjectId());
        generator2.writeObjectRef(a.getReferenceProperty("b").getId());
        generator2.close();

        // Check that the written objectReference looks like the expected JSON
        String expectedObjectReferenceAsJson = "{\"ref\":\"" + idB.toString() + "\"}";
        String objectReference = new String(Files.readAllBytes(Paths.get(objectReferenceFilenameFullPath)));
        assertEquals(expectedObjectReferenceAsJson, objectReference);
    }

    private void outputPropertyset(JsonGenerator generator, Propertyset propertyset) throws IOException {
        generator.writeStartObject();
        propertyset.getPropertynames();
        Collection<String> propertynames = propertyset.getPropertynames();
        for (String propertyname : propertynames) {
            Propertyvalue propertyvalue = propertyset.getProperty(propertyname);
            outputPropertyvalue(generator, propertyname, propertyvalue);
        }

        generator.writeEndObject();
    }

    private void outputPropertyvalue(JsonGenerator generator, String propertyname, Propertyvalue propertyvalue) throws IOException {
        if (propertyvalue.isId()) {
            generator.writeFieldName(propertyname);
            generator.writeObjectId(propertyvalue.asId());
        } else if (propertyvalue.isReference()) {
            generator.writeFieldName(propertyname);
            generator.writeObjectRef(propertyvalue.asReference().getId());
        } else if (propertyvalue.isString()) {
            generator.writeStringField(propertyname, propertyvalue.asString());
        } else if (propertyvalue.isDouble()) {
            generator.writeNumberField(propertyname, propertyvalue.asDouble());
        } else if (propertyvalue.isLong()) {
            generator.writeNumberField(propertyname, propertyvalue.asLong());
        } else if (propertyvalue.isBoolean()) {
            generator.writeBooleanField(propertyname, propertyvalue.asBoolean());
        } else if (propertyvalue.isComplexProperty()) {
            generator.writeFieldName(propertyname);
            Propertyset complexPropertyvalue = propertyvalue.asComplexProperty();
            outputPropertyset(generator, complexPropertyvalue);
        } else if (propertyvalue.isList()) {
            PropertyvalueList listvalue = propertyvalue.asList();
            generator.writeFieldName(propertyname);
            outputArray(generator, listvalue);
        }
    }

    private void outputArray(JsonGenerator generator, PropertyvalueList listvalue) throws IOException {
        generator.writeStartArray(listvalue.size());
        for (Propertyvalue listElement : listvalue) {
            if (listElement.isReference()) {
                generator.writeObjectRef(listElement.asReference().getId());
            } else if (listElement.isString()) {
                generator.writeNumber(listElement.asDouble());
            } else if (listElement.isString()) {
                generator.writeNumber(listElement.asLong());
            } else if (listElement.isBoolean()) {
                generator.writeBoolean(listElement.asBoolean());
            } else if (listElement.isString()) {
                outputPropertyset(generator, listElement.asComplexProperty());
            } else if (listElement.isList()) {
                outputArray(generator, listElement.asList());
            }
        }

        generator.writeEndArray();
    }

    private Propertyset findAspectByTitle(Collection<Propertyset> aspects, String aspectTitle) {
    	for (Propertyset aspect : aspects) {
            if (aspectTitle.equals(aspect.getStringProperty("title"))) {
                return aspect;
            }
        }

    	return PropertysetNil.getNil();
    }

    private Propertyset buildGeneralObjectAspect(PropertysetManager propertysetManager) {
        UUID generalObjectAspectId = UUID.randomUUID();
        Propertyset generalObjectAspect = propertysetManager.findPropertyset(generalObjectAspectId);
        generalObjectAspect.setStringProperty("title", "general object");
        generalObjectAspect.setStringProperty("aspect", "object");
        Propertyset generalObjectAspectProperties = propertysetManager.createPropertyset();
        Propertyset nameProperty = propertysetManager.createPropertyset();
        nameProperty.setStringProperty("aspect", "string");
        generalObjectAspectProperties.setComplexProperty("name", nameProperty);
        Propertyset descriptionProperty = propertysetManager.createPropertyset();
        descriptionProperty.setStringProperty("aspect", "string");
        generalObjectAspectProperties.setComplexProperty("description", descriptionProperty);
        generalObjectAspect.setComplexProperty("properties", generalObjectAspectProperties);
        return generalObjectAspect;
    }

    private Propertyset buildPositionAspect(PropertysetManager propertysetManager) {
        UUID positionAspectId = UUID.randomUUID();
        Propertyset positionAspect = propertysetManager.findPropertyset(positionAspectId);
        positionAspect.setStringProperty("title", "position");
        positionAspect.setStringProperty("aspect", "object");
        Propertyset positionAspectProperties = propertysetManager.createPropertyset();
        Propertyset xposProperty = propertysetManager.createPropertyset();
        xposProperty.setStringProperty("aspect", "number");
        positionAspectProperties.setComplexProperty("xpos", xposProperty);
        Propertyset yposProperty = propertysetManager.createPropertyset();
        yposProperty.setStringProperty("aspect", "number");
        positionAspectProperties.setComplexProperty("ypos", yposProperty);
        positionAspect.setComplexProperty("properties", positionAspectProperties);
        return positionAspect;
    }

    private void buildModelWithAspects(PropertysetManager propertysetManager) {
        // Base aspect "vehicle"
        UUID vechicleAspectId = UUID.randomUUID();
        Propertyset vehicleAspect = propertysetManager.findPropertyset(vechicleAspectId);
        vehicleAspect.setStringProperty("title", "vehicle");
        vehicleAspect.setStringProperty("aspect", "object");
        Propertyset vehicleAspectProperties = propertysetManager.createPropertyset();
        Propertyset manufacturerDefinition = propertysetManager.createPropertyset();
        manufacturerDefinition.setStringProperty("aspect", "string");
        Propertyset modelnameDefinition = propertysetManager.createPropertyset();
        modelnameDefinition.setStringProperty("aspect", "string");
        vehicleAspectProperties.setComplexProperty("modelname", modelnameDefinition);
        Propertyset wheelCountDefinition = propertysetManager.createPropertyset();
        wheelCountDefinition.setStringProperty("description", "Number of wheels on the vehicle");
        wheelCountDefinition.setStringProperty("aspect", "integer");
        wheelCountDefinition.setLongProperty("minimum", Long.valueOf(0));
        vehicleAspectProperties.setComplexProperty("definition", wheelCountDefinition);
        vehicleAspect.setComplexProperty("properties", vehicleAspectProperties);

        // Subaspect "bicycle"
        UUID bicycleAspectId = UUID.randomUUID();
        Propertyset bicycleAspect = propertysetManager.findPropertyset(bicycleAspectId);
        bicycleAspect.setStringProperty("title", "bicycle");
        bicycleAspect.setStringProperty("aspect", "object");
        bicycleAspect.setReferenceProperty("inherits", vehicleAspect);
        Propertyset bicycleAspectProperties = propertysetManager.createPropertyset();
        Propertyset frameNumber = propertysetManager.createPropertyset();
        frameNumber.setStringProperty("definition", "Unique identifier for the bicycle");
        bicycleAspect.setComplexProperty("properties", bicycleAspectProperties);

        // Subaspect "car"
        UUID carAspectId = UUID.randomUUID();
        Propertyset carAspect = propertysetManager.findPropertyset(carAspectId);
        carAspect.setStringProperty("title", "car");
        carAspect.setStringProperty("aspect", "object");
        carAspect.setReferenceProperty("inherits", vehicleAspect);
        Propertyset carAspectProperties = propertysetManager.createPropertyset();
        Propertyset engineSize = propertysetManager.createPropertyset();
        engineSize.setStringProperty("description", "Engine displacement in litres");
        engineSize.setStringProperty("aspect", "number");
        carAspectProperties.setComplexProperty("engineSize", engineSize);
        Propertyset enginePower = propertysetManager.createPropertyset();
        enginePower.setStringProperty("description", "Engine power in kW");
        enginePower.setStringProperty("aspect", "number");
        carAspectProperties.setComplexProperty("enginePower", enginePower);
        carAspect.setComplexProperty("properties", carAspectProperties);

        // Make some instances with aspects
        Propertyset head = propertysetManager.findPropertyset(UUID.randomUUID());
        head.addAspect(bicycleAspect);
        head.setStringProperty("manufacturer", "HEAD");
        head.setStringProperty("model", "Tacoma I");
        head.setStringProperty("frameNumber", "001-234-509-374-331");
        Propertyset nakamura = propertysetManager.findPropertyset(UUID.randomUUID());
        nakamura.addAspect(bicycleAspect);
        nakamura.setStringProperty("manufacturer", "Nakamura");
        nakamura.setStringProperty("model", "Fatbike 2015");
        nakamura.setStringProperty("frameNumber", "003-577-943-547-931");
        Propertyset ferrari = propertysetManager.findPropertyset(UUID.randomUUID());
        ferrari.addAspect(carAspect);
        ferrari.setStringProperty("manufacturer", "Ferrari");
        ferrari.setStringProperty("model", "550 Barchetta");
        ferrari.setDoubleProperty("engineSize", 5.5);
        ferrari.setDoubleProperty("enginePower", 357.0);
        Propertyset subaru = propertysetManager.findPropertyset(UUID.randomUUID());
        subaru.addAspect(carAspect);
        subaru.setStringProperty("manufacturer", "Subaru");
        subaru.setStringProperty("model", "Outback");
        subaru.setDoubleProperty("engineSize", 2.5);
        subaru.setDoubleProperty("enginePower", 125.0);
        Propertyset volvo = propertysetManager.findPropertyset(UUID.randomUUID());
        volvo.addAspect(carAspect);
        volvo.setStringProperty("manufacturer", "Volvo");
        volvo.setStringProperty("model", "P1800");
        volvo.setDoubleProperty("engineSize", 1.8);
        volvo.setDoubleProperty("enginePower", 84.58);
    }

}
