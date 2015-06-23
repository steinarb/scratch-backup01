package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import static no.priv.bang.modeling.modelstore.impl.Propertyvalues.*;
import no.priv.bang.modeling.modelstore.PropertysetNil;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link BooleanPropertyvalue}.
 *
 * @author Steinar Bang
 *
 */
public class BooleanPropertyvalueTest {

    private Propertyvalue value;

    @Before
    public void setUp() throws Exception {
        value = toBooleanValue(Boolean.TRUE);
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
        assertTrue(value.isBoolean());
    }

    @Test
    public void testAsBoolean() {
        assertTrue(value.asBoolean());
    }

    @Test
    public void testIsLong() {
        assertFalse(value.isLong());
    }

    @Test
    public void testAsLong() {
        assertEquals(Long.valueOf(1), value.asLong());
    }

    @Test
    public void testIsDouble() {
        assertFalse(value.isDouble());
    }

    @Test
    public void testAsDouble() {
        assertEquals(Double.valueOf(1), value.asDouble());
    }

    @Test
    public void testIsString() {
        assertFalse(value.isString());
    }

    @Test
    public void testAsString() {
        assertEquals("true", value.asString());
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
        assertFalse(value.isReference());
    }

    @Test
    public void testAsReference() {
        assertEquals(PropertysetNil.getNil(), value.asReference());
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
     * Test av {@link BooleanPropertyvalue#hashCode()}.
     */
    @Test
    public void testHashCode() {
        Propertyvalue nullBooleanValue = toBooleanValue(null);
        assertEquals(1268, nullBooleanValue.hashCode());
        Propertyvalue trueBooleanValue = toBooleanValue(Boolean.TRUE);
        assertEquals(1262, trueBooleanValue.hashCode());
    }

    /**
     * Test av {@link BooleanPropertyvalue#equals(Object)}.
     */
    @Test
    public void testEquals() {
        Propertyvalue nullBooleanValue = toBooleanValue(null);
        Propertyvalue falseBooleanValue = toBooleanValue(Boolean.FALSE);
        Propertyvalue trueBooleanValue = toBooleanValue(Boolean.TRUE);
        assertFalse(nullBooleanValue.equals(null));
        assertFalse(nullBooleanValue.equals(PropertyvalueNil.getNil()));
        assertTrue(nullBooleanValue.equals(nullBooleanValue));
        assertTrue(nullBooleanValue.equals(falseBooleanValue));
        assertFalse(nullBooleanValue.equals(trueBooleanValue));
    }

    /**
     * Test av {@link BooleanPropertyvalue#toString()}.
     */
    @Test
    public void testToString() {
        Propertyvalue nullBooleanValue = toBooleanValue(null);
        assertEquals("BooleanPropertyvalue [value=false]", nullBooleanValue.toString());
        Propertyvalue falseBooleanValue = toBooleanValue(Boolean.FALSE);
        assertEquals("BooleanPropertyvalue [value=false]", falseBooleanValue.toString());
        Propertyvalue trueBooleanValue = toBooleanValue(Boolean.TRUE);
        assertEquals("BooleanPropertyvalue [value=true]", trueBooleanValue.toString());
    }
}
