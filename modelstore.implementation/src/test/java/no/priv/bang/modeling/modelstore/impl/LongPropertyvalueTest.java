package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import no.priv.bang.modeling.modelstore.PropertysetNil;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link LongPropertyvalue}.
 *
 * @author Steinar Bang
 *
 */
public class LongPropertyvalueTest {

    private Propertyvalue value;

    @Before
    public void setUp() throws Exception {
        value = new LongPropertyvalue(Long.valueOf(42));
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
        assertTrue(value.asBoolean());
    }

    @Test
    public void testIsLong() {
        assertTrue(value.isLong());
    }

    @Test
    public void testAsLong() {
        assertEquals(Long.valueOf(42), value.asLong());
    }

    @Test
    public void testIsDouble() {
        assertFalse(value.isDouble());
    }

    @Test
    public void testAsDouble() {
        assertEquals(Double.valueOf(42.0), value.asDouble());
    }

    @Test
    public void testIsString() {
        assertFalse(value.isString());
    }

    @Test
    public void testAsString() {
        assertEquals("42", value.asString());
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
     * Test av {@link LongPropertyvalue#hashCode()}.
     */
    @Test
    public void testHashCode() {
        LongPropertyvalue nullLongValue = new LongPropertyvalue(null);
        assertEquals(31, nullLongValue.hashCode());
        LongPropertyvalue value = new LongPropertyvalue(42L);
        assertEquals(73, value.hashCode());
    }

    /**
     * Test av {@link LongPropertyvalue#equals(Object)}.
     */
    @Test
    public void testEquals() {
        LongPropertyvalue nullLongValue = new LongPropertyvalue(null);
        LongPropertyvalue value = new LongPropertyvalue(21L);
        assertFalse(nullLongValue.equals(null));
        assertFalse(nullLongValue.equals(PropertyvalueNil.getNil()));
        assertTrue(nullLongValue.equals(nullLongValue));
        assertFalse(nullLongValue.equals(value));
        assertFalse(value.equals(nullLongValue));
    }

    /**
     * Test av {@link LongPropertyvalue#toString()}.
     */
    @Test
    public void testToString() {
        LongPropertyvalue nullLongValue = new LongPropertyvalue(null);
        assertEquals("LongPropertyvalue [value=0]", nullLongValue.toString());
        LongPropertyvalue value = new LongPropertyvalue(1024L);
        assertEquals("LongPropertyvalue [value=1024]", value.toString());
    }

}
