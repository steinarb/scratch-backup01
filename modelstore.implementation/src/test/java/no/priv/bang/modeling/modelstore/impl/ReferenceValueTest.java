package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;

import java.util.UUID;

import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link ReferenceValue}.
 *
 * @author Steinar Bang
 *
 */
public class ReferenceValueTest {

    private Propertyset referencedObject;
    private Value value;

    @Before
    public void setUp() throws Exception {
        referencedObject = new PropertysetImpl(UUID.fromString("276dbd6e-dc46-4c14-af9e-83c63c10e0b3"));
        referencedObject.setBooleanProperty("boolean", Boolean.TRUE);
        referencedObject.setLongProperty("long", Long.valueOf(42));
        referencedObject.setDoubleProperty("double", Double.valueOf(2.78));
        referencedObject.setStringProperty("string", "foo bar");
        value = toReferenceValue(referencedObject);
    }

    @Test
    public void testIsId() {
    	assertFalse(value.isId());
    }

    @Test
    public void testAsId() {
    	assertEquals(getNil().asId(), value.asId());
    }

    @Test
    public void testIsBoolean() {
        assertFalse(value.isBoolean());
    }

    @Test
    public void testAsBoolean() {
        assertFalse(value.asBoolean());
    }

    @Test
    public void testIsLong() {
        assertFalse(value.isLong());
    }

    @Test
    public void testAsLong() {
        assertEquals(Long.valueOf(0), value.asLong());
    }

    @Test
    public void testIsDouble() {
        assertFalse(value.isDouble());
    }

    @Test
    public void testAsDouble() {
        assertEquals(Double.valueOf(0.0), value.asDouble());
    }

    @Test
    public void testIsString() {
        assertFalse(value.isString());
    }

    @Test
    public void testAsString() {
        assertEquals("", value.asString());
    }

    @Test
    public void testIsComplexProperty() {
        assertFalse(value.isComplexProperty());
    }

    @Test
    public void testAsComplexProperty() {
        assertEquals(getNilPropertyset(), value.asComplexProperty());
    }

    @Test
    public void testIsReference() {
        assertTrue(value.isReference());
    }

    @Test
    public void testAsReference() {
        assertEquals(referencedObject, value.asReference());
    }

    @Test
    public void testIsList() {
        assertFalse(value.isList());
    }

    @Test
    public void testAsList() {
    	ValueList emptyList = value.asList();
    	assertTrue(emptyList.isEmpty());
    }

    /**
     * Test av {@link ReferenceValue#hashCode()}.
     */
    @Test
    public void testHashCode() {
        Value nullReferenceValue = toReferenceValue(null);
        assertEquals(31, nullReferenceValue.hashCode());
        assertEquals(1755681326, value.hashCode());
    }

    /**
     * Test av {@link ReferenceValue#equals(Object)}.
     */
    @Test
    public void testEquals() {
        Value nullReferenceValue = toReferenceValue(null);
        assertFalse(nullReferenceValue.equals(null));
        assertFalse(nullReferenceValue.equals(getNil().asComplexProperty()));
        assertTrue(nullReferenceValue.equals(nullReferenceValue));
        assertFalse(nullReferenceValue.equals(value));
        assertFalse(value.equals(nullReferenceValue));
        assertTrue(value.equals(value));

        // Compare two different object with no id property
        Value refToNoId = toReferenceValue(new PropertysetImpl());
        Value refToNoId2 = toReferenceValue(new PropertysetImpl());
        assertTrue(refToNoId.equals(refToNoId2));

    }

    /**
     * Test av {@link ReferenceValue#toString()}.
     */
    @Test
    public void testToString() {
        Value nullReferenceValue = toReferenceValue(null);
        assertEquals("ReferenceValue [value=PropertysetNil []]", nullReferenceValue.toString());
        assertEquals("ReferenceValue [value=PropertysetImpl [properties={id=IdValue [value=276dbd6e-dc46-4c14-af9e-83c63c10e0b3], string=StringValue [value=foo bar], boolean=BooleanValue [value=true], double=DoubleValue [value=2.78], long=LongValue [value=42]}]]", value.toString());
    }

}
