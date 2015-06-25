package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link LongPropertyvalue}.
 *
 * @author Steinar Bang
 *
 */
public class LongValueTest {

    private Value value;

    @Before
    public void setUp() throws Exception {
        value = toLongValue(42);
    }

    @Test
    public void testIsId() {
    	assertFalse(value.isId());
    }

    @Test
    public void testAsId() {
    	assertEquals(NilValue.getNil().asId(), value.asId());
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
        assertEquals(getNilPropertyset(), value.asComplexProperty());
    }

    @Test
    public void testIsReference() {
        assertFalse(value.isReference());
    }

    @Test
    public void testAsReference() {
        assertEquals(getNilPropertyset(), value.asReference());
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
     * Test av {@link LongPropertyvalue#hashCode()}.
     */
    @Test
    public void testHashCode() {
        Value nullLongValue = toLongValue(null);
        assertEquals(31, nullLongValue.hashCode());
        assertEquals(73, value.hashCode());
    }

    /**
     * Test av {@link LongPropertyvalue#equals(Object)}.
     */
    @Test
    public void testEquals() {
        Value nullLongValue = toLongValue(null);
        assertFalse(nullLongValue.equals(null));
        assertFalse(nullLongValue.equals(NilValue.getNil()));
        assertTrue(nullLongValue.equals(nullLongValue));
        assertFalse(nullLongValue.equals(value));
        assertFalse(value.equals(nullLongValue));
    }

    /**
     * Test av {@link LongPropertyvalue#toString()}.
     */
    @Test
    public void testToString() {
        Value nullLongValue = toLongValue(null);
        assertEquals("LongPropertyvalue [value=0]", nullLongValue.toString());
        assertEquals("LongPropertyvalue [value=42]", value.toString());
    }

}
