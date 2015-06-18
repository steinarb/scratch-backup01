package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;

import java.util.UUID;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link ReferencePropertyvalue}.
 *
 * @author Steinar Bang
 *
 */
public class ReferencePropertyvalueTest {

    private Propertyset referencedObject;
    private Propertyvalue value;

    @Before
    public void setUp() throws Exception {
        referencedObject = new PropertysetImpl(UUID.fromString("276dbd6e-dc46-4c14-af9e-83c63c10e0b3"));
        referencedObject.setBooleanProperty("boolean", Boolean.TRUE);
        referencedObject.setLongProperty("long", Long.valueOf(42));
        referencedObject.setDoubleProperty("double", Double.valueOf(2.78));
        referencedObject.setStringProperty("string", "foo bar");
        value = new ReferencePropertyvalue(referencedObject);
    }

    @Test
    public void testIsId() {
    	assertFalse(value.isId());
    }

    @Test
    public void testAsId() {
    	assertEquals(PropertyvalueNil.getNil().asId(), value.asId());
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
        assertEquals(PropertysetNil.getNil(), value.asComplexProperty());
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
    	PropertyvalueList emptyList = value.asList();
    	assertTrue(emptyList.isEmpty());
    }

    /**
     * Test av {@link ReferencePropertyvalue#hashCode()}.
     */
    @Test
    public void testHashCode() {
        ReferencePropertyvalue nullReferenceValue = new ReferencePropertyvalue(null);
        assertEquals(31, nullReferenceValue.hashCode());
        assertEquals(1755681326, value.hashCode());
    }

    /**
     * Test av {@link ReferencePropertyvalue#equals(Object)}.
     */
    @Test
    public void testEquals() {
        ReferencePropertyvalue nullReferenceValue = new ReferencePropertyvalue(null);
        assertFalse(nullReferenceValue.equals(null));
        assertFalse(nullReferenceValue.equals(PropertyvalueNil.getNil().asComplexProperty()));
        assertTrue(nullReferenceValue.equals(nullReferenceValue));
        assertFalse(nullReferenceValue.equals(value));
        assertFalse(value.equals(nullReferenceValue));
        assertTrue(value.equals(value));

        // Compare two different object with no id property
        ReferencePropertyvalue refToNoId = new ReferencePropertyvalue(new PropertysetImpl());
        ReferencePropertyvalue refToNoId2 = new ReferencePropertyvalue(new PropertysetImpl());
        assertTrue(refToNoId.equals(refToNoId2));

    }

    /**
     * Test av {@link ReferencePropertyvalue#toString()}.
     */
    @Test
    public void testToString() {
        ReferencePropertyvalue nullReferenceValue = new ReferencePropertyvalue(null);
        assertEquals("ReferencePropertyvalue [value=PropertysetNil []]", nullReferenceValue.toString());
        assertEquals("ReferencePropertyvalue [value=PropertysetImpl [properties={id=IdPropertyvalue [value=276dbd6e-dc46-4c14-af9e-83c63c10e0b3], string=StringPropertyvalue [value=foo bar], boolean=BooleanPropertyvalue [value=true], double=DoublePropertyvalue [value=2.78], long=LongPropertyvalue [value=42]}]]", value.toString());
    }

}
