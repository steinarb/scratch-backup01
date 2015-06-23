package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import static no.priv.bang.modeling.modelstore.impl.Propertyvalues.*;
import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

import org.junit.Test;

public class PropertysetImplTest {

    /**
     * Test creating an empty {@link PropertysetImpl} class and verify
     * the expected empty values for all property types.
     */
    @Test
    public void testCreateEmptyPropertyset() {
        Propertyset propertyset = new PropertysetImpl();
        assertFalse(propertyset.isNil());

        Boolean boolProperty = propertyset.getBooleanProperty("boolProperty");
        assertEquals(Boolean.valueOf(false), boolProperty);
        Long intProperty = propertyset.getLongProperty("intPropName");
        assertEquals(Long.valueOf(0), intProperty);
        Double doubleProperty = propertyset.getDoubleProperty("doublePropName");
        assertEquals(Double.valueOf(0), doubleProperty);
        String stringProperty = propertyset.getStringProperty("stringProperty");
        assertEquals("", stringProperty);
        Propertyset complexProperty = propertyset.getComplexProperty("complexProperty");
        assertNotNull(complexProperty);
        assertTrue(complexProperty.isNil());
        Propertyset reference = propertyset.getReferenceProperty("reference");
        assertNotNull(reference);
        assertTrue(reference.isNil());
    }

    /**
     * Test getting a bool from various property types
     */
    @Test
    public void testGetBooleanProperty() {
        Propertyset propertyset = new PropertysetImpl();

        Boolean trueValue = Boolean.valueOf(true);
        propertyset.setBooleanProperty("trueValue", trueValue);
        Boolean boolValue = propertyset.getBooleanProperty("trueValue");
        assertEquals(trueValue, boolValue);

        // Set int values and read them back as bool values
        // 0 is false, everything else is true
        Long negativeInt = Long.valueOf(-1);
        propertyset.setLongProperty("negativeInt", negativeInt);
        Boolean negativeIntAsBool = propertyset.getBooleanProperty("negativeInt");
        assertEquals(Boolean.valueOf(true), negativeIntAsBool);
        Long nullInt = Long.valueOf(0);
        propertyset.setLongProperty("nullInt", nullInt);
        Boolean nullIntAsBool = propertyset.getBooleanProperty("nullInt");
        assertEquals(Boolean.valueOf(false), nullIntAsBool);
        Long positiveInt = Long.valueOf(1);
        propertyset.setLongProperty("positiveInt", positiveInt);
        Boolean positiveIntAsBool = propertyset.getBooleanProperty("positiveInt");
        assertEquals(Boolean.valueOf(true), positiveIntAsBool);

        // Set a null value and get a false boolean value back
        propertyset.setBooleanProperty("nullValue", null);
        Boolean nullValue = propertyset.getBooleanProperty("nullValue");
        assertEquals(Boolean.valueOf(false), nullValue);

        // Set float values and read them back as bool values
        // 0.0 is false, everything else is true
        Double negativeFloat = Double.valueOf(-1.0);
        propertyset.setDoubleProperty("negativeFloat", negativeFloat);
        Boolean negativeFloatAsBool = propertyset.getBooleanProperty("negativeFloat");
        assertEquals(Boolean.valueOf(true), negativeFloatAsBool);
        Double nullFloat = Double.valueOf(0.0);
        propertyset.setDoubleProperty("nullFloat", nullFloat);
        Boolean nullFloatAsBool = propertyset.getBooleanProperty("nullFloat");
        assertEquals(Boolean.valueOf(false), nullFloatAsBool);
        Double positiveFloat = Double.valueOf(1.0);
        propertyset.setDoubleProperty("positiveFloat", positiveFloat);
        Boolean positiveFloatAsBool = propertyset.getBooleanProperty("positiveFloat");
        assertEquals(Boolean.valueOf(true), positiveFloatAsBool);

        // Set string values and read them back as bool values
        // "true" is true, everything else is false
        String falseString = "false";
        propertyset.setStringProperty("falseString", falseString);
        Boolean falseStringAsBool = propertyset.getBooleanProperty("falseString");
        assertEquals(Boolean.valueOf(false), falseStringAsBool);
        String trueString = "true";
        propertyset.setStringProperty("trueString", trueString);
        Boolean trueStringAsBool = propertyset.getBooleanProperty("trueString");
        assertEquals(Boolean.valueOf(true), trueStringAsBool);
        String notABoolString = "hey there!";
        propertyset.setStringProperty("notABoolString", notABoolString);
        Boolean notABoolStringAsString = propertyset.getBooleanProperty("notABoolString");
        assertEquals(Boolean.valueOf(false), notABoolStringAsString);
    }

    /**
     * Test getting an integer from various property types
     */
    @Test
    public void testGetLongProperty() {
        Propertyset propertyset = new PropertysetImpl();
        assertFalse(propertyset.isNil());

        // Verify that a value set as an integer is returned as the same Long value
        Long intValue1 = Long.valueOf(1234);
        propertyset.setLongProperty("intValue", intValue1);
        Long intValue2 = propertyset.getLongProperty("intValue");
        assertEquals(intValue1, intValue2);

        // Set some double values and read them back as Long values
        Double doubleValueContainingInt = Double.valueOf(7);
        propertyset.setDoubleProperty("doubleValueContainingInt", doubleValueContainingInt);
        Long doubleValueReadBackAsInt1 = propertyset.getLongProperty("doubleValueContainingInt");
        assertEquals(Long.valueOf(7), doubleValueReadBackAsInt1);
        Double doubleValueContainingNonInt = Double.valueOf(2.78);
        propertyset.setDoubleProperty("doubleValueReadBackAsInt", doubleValueContainingNonInt);
        Long doubleValueReadBackAsInt2 = propertyset.getLongProperty("doubleValueReadBackAsInt");
        assertEquals(Long.valueOf(3), doubleValueReadBackAsInt2);

        // Set a null value and verify that a zero int value is returned
        propertyset.setLongProperty("nullValue", null);
        Long nullValue = propertyset.getLongProperty("nullValue");
        assertEquals(Long.valueOf(0), nullValue);

        // Set some string values and read them back as Long values
        String stringValueContainingInt = "11";
        propertyset.setStringProperty("stringValueContainingInt", stringValueContainingInt);
        Long stringValueReadBackAsInt1 = propertyset.getLongProperty("stringValueContainingInt");
        assertEquals(Long.valueOf(11), stringValueReadBackAsInt1);
        String stringValueContainingFloat = "2.78";
        propertyset.setStringProperty("stringValueContainingFloat", stringValueContainingFloat);
        Long stringValueReadBackAsInt2 = propertyset.getLongProperty("stringValueContainingFloat");
        assertEquals(Long.valueOf(3), stringValueReadBackAsInt2);
        String stringValueNotParsableAsANumber = "not a number";
        propertyset.setStringProperty("stringValueNotParsableAsANumber", stringValueNotParsableAsANumber);
        Long stringValueReadBackAsInt3 = propertyset.getLongProperty("stringValueNotParsableAsANumber");
        assertEquals(Long.valueOf(0), stringValueReadBackAsInt3);

        // set boolean values and read them back as integers.
        Boolean falseValue = Boolean.valueOf(false);
        propertyset.setBooleanProperty("falseValue", falseValue);
        Long falseAsLong = propertyset.getLongProperty("falseValue");
        assertEquals(Long.valueOf(0), falseAsLong);
        Boolean trueValue = Boolean.valueOf(true);
        propertyset.setBooleanProperty("trueValue", trueValue);
        Long trueAsLong = propertyset.getLongProperty("trueValue");
        assertEquals(Long.valueOf(1), trueAsLong);
    }

    /**
     * Test getting a double from various property types
     */
    @Test
    public void testGetDoubleProperty() {
        Propertyset propertyset = new PropertysetImpl();
        assertFalse(propertyset.isNil());

        // Verify that a value set as a double is returned as the same Double value
        Double doubleValue1 = Double.valueOf(3.14);
        propertyset.setDoubleProperty("doubleValue", doubleValue1);
        Double doubleValue2 = propertyset.getDoubleProperty("doubleValue");
        assertEquals(doubleValue1, doubleValue2);

        // Verify that setting a null value will return a zero double value.
        propertyset.setDoubleProperty("nullDoubleValue", null);
        Double nullDoubleValue = propertyset.getDoubleProperty("nullDoubleValue");
        assertEquals(Double.valueOf(0.0), nullDoubleValue);

        // Verify that a value set as an int can be returned as a Double containing the same value
        Long intValue1 = Long.valueOf(17);
        propertyset.setLongProperty("intValue1", intValue1);
        Double doubleValue3 = propertyset.getDoubleProperty("intValue1");
        assertEquals(Double.valueOf(17), doubleValue3);

        // Verify that a float value in a string can be returned as a Double containing the same value.
        String stringValueWithFloat = "3.14";
        propertyset.setStringProperty("stringValueWithFloat", stringValueWithFloat);
        Double doubleValue4 = propertyset.getDoubleProperty("stringValueWithFloat");
        assertEquals(Double.valueOf(3.14), doubleValue4);

        // Verify that an int value in a string can be returned as a Double containing the same value
        String stringValueWithInt = "27";
        propertyset.setStringProperty("stringValueWithFloat", stringValueWithInt);
        Double doubleValue5 = propertyset.getDoubleProperty("stringValueWithFloat");
        assertEquals(Double.valueOf(27), doubleValue5);

        // Verify that string not parseable as a numeric value can be returned as a Double with value 0.0
        String stringValueNotParsableAsANumber = "This is not a number";
        propertyset.setStringProperty("stringValueNotParsableAsANumber", stringValueNotParsableAsANumber);
        Double doubleValue6 = propertyset.getDoubleProperty("stringValueNotParsableAsANumber");
        assertEquals(Double.valueOf(0.0), doubleValue6);

        // set boolean values and read them back as floating point numbers.
        Boolean falseValue = Boolean.valueOf(false);
        propertyset.setBooleanProperty("falseValue", falseValue);
        Double falseAsDouble = propertyset.getDoubleProperty("falseValue");
        assertEquals(Double.valueOf(0.0), falseAsDouble);
        Boolean trueValue = Boolean.valueOf(true);
        propertyset.setBooleanProperty("trueValue", trueValue);
        Double trueAsDouble = propertyset.getDoubleProperty("trueValue");
        assertEquals(Double.valueOf(1.0), trueAsDouble);
    }

    /**
     * Test getting a string back from various property types.
     */
    @Test
    public void testGetStringProperty() {
        Propertyset propertyset = new PropertysetImpl();

        // Set a string property and get it back as a string
        String stringValue = "Hello world!";
        propertyset.setStringProperty("stringValue", stringValue);
        String stringValueReadBackFromProperty = propertyset.getStringProperty("stringValue");
        assertEquals(stringValue, stringValueReadBackFromProperty);

        // Set a null string property and verify that it is returned as an empty string
        propertyset.setStringProperty("nullValue", null);
        String nullValue = propertyset.getStringProperty("nullValue");
        assertEquals("", nullValue);

        // Set an int property and read it back as a string
        Long intValue = Long.valueOf(42);
        propertyset.setLongProperty("intValue", intValue);
        String intValueAsString = propertyset.getStringProperty("intValue");
        assertEquals("42", intValueAsString);

        // Set a double property and read it back as a string
        Double doubleValue = Double.valueOf(37.5);
        propertyset.setDoubleProperty("doubleValue", doubleValue);
        String doubleValueAsString = propertyset.getStringProperty("doubleValue");
        assertEquals("37.5", doubleValueAsString);

        // set boolean values and read them back as floating point numbers.
        Boolean falseValue = Boolean.valueOf(false);
        propertyset.setBooleanProperty("falseValue", falseValue);
        String falseAsString = propertyset.getStringProperty("falseValue");
        assertEquals("false", falseAsString);
        Boolean trueValue = Boolean.valueOf(true);
        propertyset.setBooleanProperty("trueValue", trueValue);
        String trueAsString = propertyset.getStringProperty("trueValue");
        assertEquals("true", trueAsString);
    }


    /**
     * Test getting a complex property back from various property types.
     *
     * Only a property actually set as a complex property should return
     * something other than {@link PropertysetNil#getNil()},
     */
    @Test
    public void testGetComplexProperty() {
        Propertyset propertyset = new PropertysetImpl();

        // Set a complex property and retrieve it.
        Propertyset point = new PropertysetImpl();
        point.setDoubleProperty("x", 75.3);
        point.setDoubleProperty("y", 145.3);
        propertyset.setComplexProperty("upperLeftCorner", point);
        Propertyset upperLeftCorner = propertyset.getComplexProperty("upperLeftCorner");
        assertEquals(Double.valueOf(75.3), upperLeftCorner.getDoubleProperty("x"));
        assertEquals(Double.valueOf(145.3), upperLeftCorner.getDoubleProperty("y"));

        // Set a null complex property value and verify that it results in a nil Propertyset
        propertyset.setComplexProperty("nullValue", null);
        Propertyset nullValue = propertyset.getComplexProperty("nullValue");
        assertEquals(PropertysetNil.getNil(), nullValue);

        // Check that accessing a complex property as other property types
        // gives null values (a complex property can't be autoconverted to
        // a different type).
        Boolean upperLeftCornerAsBoolean = propertyset.getBooleanProperty("upperLeftCorner");
        assertEquals(Boolean.valueOf(false), upperLeftCornerAsBoolean);
        Long upperLeftCornerAsLong = propertyset.getLongProperty("upperLeftCorner");
        assertEquals(Long.valueOf(0), upperLeftCornerAsLong);
        Double upperLeftCornerAsDouble = propertyset.getDoubleProperty("upperLeftCorner");
        assertEquals(Double.valueOf(0.0), upperLeftCornerAsDouble);
        String upperLeftCornerAsString = propertyset.getStringProperty("upperLeftCorner");
        assertEquals("", upperLeftCornerAsString);
        Propertyset upperLeftCornerAsReference = propertyset.getReferenceProperty("upperLeftCorner");
        assertEquals(PropertysetNil.getNil(), upperLeftCornerAsReference);

        // Set a some properties with different types and try reading them back as complex properties
        // They all result in a nil complex property (ie. no value conversion).
        propertyset.setBooleanProperty("boolValue", Boolean.valueOf(true));
        assertEquals(PropertysetNil.getNil(), propertyset.getComplexProperty("boolValue"));
        propertyset.setLongProperty("longValue", Long.valueOf(42));
        assertEquals(PropertysetNil.getNil(), propertyset.getComplexProperty("longValue"));
        propertyset.setDoubleProperty("doubleValue", Double.valueOf(2.78));
        assertEquals(PropertysetNil.getNil(), propertyset.getComplexProperty("doubleValue"));
        propertyset.setStringProperty("stringValue", "foo bar");
        assertEquals(PropertysetNil.getNil(), propertyset.getComplexProperty("stringValue"));
        Propertyset referencedObject = new PropertysetImpl();
        propertyset.setReferenceProperty("referenceValue", referencedObject);
        assertEquals(PropertysetNil.getNil(), propertyset.getComplexProperty("referenceValue"));
    }

    /**
     * Test getting a complex property back from various property types.
     *
     * Only a property actually set as a complex property should return
     * something other than {@link PropertysetNil#getNil()},
     */
    @Test
    public void testGetReferenceProperty() {
        Propertyset propertyset = new PropertysetImpl();

        // Set an object reference property value and read it back
        Propertyset aDifferentPropset = new PropertysetImpl();
        aDifferentPropset.setStringProperty("foo", "bar");
        propertyset.setReferenceProperty("aLinkedObject", aDifferentPropset);
        Propertyset aLinkedObject = propertyset.getReferenceProperty("aLinkedObject");
        assertEquals(aDifferentPropset, aLinkedObject);

        // Check that accessing a reference property as other property types
        // gives null values (a reference property can't be autoconverted to
        // a different type).
        Boolean aLinkedObjectAsBoolean = propertyset.getBooleanProperty("aLinkedObject");
        assertEquals(Boolean.valueOf(false), aLinkedObjectAsBoolean);
        Long aLinkedObjectAsLong = propertyset.getLongProperty("aLinkedObject");
        assertEquals(Long.valueOf(0), aLinkedObjectAsLong);
        Double aLinkedObjectAsDouble = propertyset.getDoubleProperty("aLinkedObject");
        assertEquals(Double.valueOf(0.0), aLinkedObjectAsDouble);
        String aLinkedObjectAsString = propertyset.getStringProperty("aLinkedObject");
        assertEquals("", aLinkedObjectAsString);
        Propertyset aLinkedObjectAsComplexProperty = propertyset.getComplexProperty("aLinkedObject");
        assertEquals(PropertysetNil.getNil(), aLinkedObjectAsComplexProperty);

        // Set a some properties with different types and try reading them back as reference properties
        // They all result in a nil reference property (ie. no value conversion).
        propertyset.setBooleanProperty("boolValue", Boolean.valueOf(true));
        assertEquals(PropertysetNil.getNil(), propertyset.getReferenceProperty("boolValue"));
        propertyset.setLongProperty("longValue", Long.valueOf(42));
        assertEquals(PropertysetNil.getNil(), propertyset.getReferenceProperty("longValue"));
        propertyset.setDoubleProperty("doubleValue", Double.valueOf(2.78));
        assertEquals(PropertysetNil.getNil(), propertyset.getReferenceProperty("doubleValue"));
        propertyset.setStringProperty("stringValue", "foo bar");
        assertEquals(PropertysetNil.getNil(), propertyset.getReferenceProperty("stringValue"));
        Propertyset complexdObject = new PropertysetImpl();
        propertyset.setComplexProperty("complexValue", complexdObject);
        assertEquals(PropertysetNil.getNil(), propertyset.getReferenceProperty("complexValue"));
    }

    /**
     * Test getting a list property back from various types.
     */
    @Test
    public void testGetListProperty() {
        Propertyset propertyset = new PropertysetImpl();

        // Set and get a list value, and verify that its members can be accessed.
        PropertyvalueList listValue = newList();
        listValue.add(toBooleanValue(Boolean.valueOf(true)));
        listValue.add(toLongValue(Long.valueOf(42)));
        propertyset.setListProperty("listValue", listValue);
        PropertyvalueList retrievedListValue = propertyset.getListProperty("listValue");
        assertEquals(2, retrievedListValue.size());
        assertEquals(Long.valueOf(42), retrievedListValue.get(1).asLong());

        // Expect and empty and unmodifiable list when accessing a property set with a null value
        propertyset.setListProperty("nullValue", null);
        PropertyvalueList nullValue = propertyset.getListProperty("nullValue");
        assertEquals(0, nullValue.size());

        // Expect an empty and unmodifiable list when accessing a non-existing property.
        PropertyvalueList emptyList = propertyset.getListProperty("noSuchList");
        assertEquals(0, emptyList.size());

        // Expect all non-list properties to result in an empty list when accessing them
        propertyset.setBooleanProperty("boolValue", Boolean.valueOf(true));
        assertEquals(PropertyvalueNil.getNil().asList(), propertyset.getListProperty("boolValue"));
        propertyset.setLongProperty("longValue", Long.valueOf(42));
        assertEquals(PropertyvalueNil.getNil().asList(), propertyset.getListProperty("longValue"));
        propertyset.setDoubleProperty("doubleValue", Double.valueOf(2.78));
        assertEquals(PropertyvalueNil.getNil().asList(), propertyset.getListProperty("doubleValue"));
        propertyset.setStringProperty("stringValue", "foo bar");
        assertEquals(PropertyvalueNil.getNil().asList(), propertyset.getListProperty("stringValue"));
        Propertyset complexdObject = new PropertysetImpl();
        propertyset.setComplexProperty("complexValue", complexdObject);
        assertEquals(PropertyvalueNil.getNil().asList(), propertyset.getListProperty("complexValue"));
        Propertyset referencedObject = new PropertysetImpl();
        propertyset.setReferenceProperty("referencedObject", referencedObject);
        assertEquals(PropertyvalueNil.getNil().asList(), propertyset.getListProperty("referencedObject"));
    }

    @Test
    public void testEmptyPropertysetEqualsPropertysetNil() {
    	Propertyset emptypropertyset = new PropertysetImpl();
    	Propertyset nil = PropertysetNil.getNil();
    	assertTrue(emptypropertyset.equals(nil));
    	assertTrue(nil.equals(emptypropertyset));
    }

    @Test
    public void testGetProperty() {
        Propertyset emptypropertyset = new PropertysetImpl();
        Propertyvalue nosuchproperty = emptypropertyset.getProperty("nosuchproperty");
        assertEquals(PropertyvalueNil.getNil(), nosuchproperty);
    }

}
