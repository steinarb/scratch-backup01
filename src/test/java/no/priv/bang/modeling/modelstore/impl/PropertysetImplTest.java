package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import no.priv.bang.modeling.modelstore.Propertyset;

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

        // Set some string values and read them back as Long values
        String stringValueContainingInt = "11";
        propertyset.setStringProperty("stringValueContainingInt", stringValueContainingInt);
        Long stringValueReadBackAsInt1 = propertyset.getLongProperty("stringValueContainingInt");
        assertEquals(Long.valueOf(11), stringValueReadBackAsInt1);
        String stringValueContainingFloat = "2.78";
        propertyset.setStringProperty("stringValueContainingInt", stringValueContainingFloat);
        Long stringValueReadBackAsInt2 = propertyset.getLongProperty("stringValueContainingInt");
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

}
