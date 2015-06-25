package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;

import java.util.UUID;

import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link IdPropertyvalue}.
 *
 * @author Steinar Bang
 *
 */
public class IdValueTest {

    private Value value;

    @Before
    public void setUp() throws Exception {
        value = new IdPropertyvalue(UUID.fromString("e40fb164-3dd3-43b8-839f-8781bbcb2a15"));
    }

    @Test
    public void testIsId() {
    	assertTrue(value.isId());
    }

    @Test
    public void testAsId() {
    	assertNotEquals(NilValue.getNil().asId(), value.asId());
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
        assertEquals(Double.valueOf(0), value.asDouble());
    }

    @Test
    public void testIsString() {
        assertFalse(value.isString());
    }

    @Test
    public void testAsString() {
        assertEquals(value.asId().toString(), value.asString());
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
     * Test av {@link IdPropertyvalue#hashCode()}.
     */
    @Test
    public void testHashCode() {
        IdPropertyvalue nullIdPropertyValue = new IdPropertyvalue(null);
        assertEquals(31, nullIdPropertyValue.hashCode());
        assertEquals(-511156377, value.hashCode());
    }

    /**
     * Test av {@link IdPropertyvalue#equals(Object)}.
     */
    @Test
    public void testEquals() {
        IdPropertyvalue nullIdPropertyValue = new IdPropertyvalue(null);
        assertFalse(nullIdPropertyValue.equals(null));
        assertFalse(nullIdPropertyValue.equals(NilValue.getNil().asId()));
        assertTrue(nullIdPropertyValue.equals(nullIdPropertyValue));
        assertFalse(nullIdPropertyValue.equals(value));
        assertFalse(value.equals(nullIdPropertyValue));
        assertTrue(value.equals(value));

        // Different object with the same UUID compares as equal
        IdPropertyvalue value2 = new IdPropertyvalue(UUID.fromString(value.asString()));
        assertTrue(value.equals(value2));
    }

    /**
     * Test av {@link IdPropertyvalue#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals("IdPropertyvalue [value=e40fb164-3dd3-43b8-839f-8781bbcb2a15]", value.toString());
    }

}
