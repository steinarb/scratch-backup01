package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link StringPropertyvalue}.
 *
 * @author Steinar Bang
 *
 */
public class StringValueTest {

    private Value valueWithNumber;
    private Value valueNotANumber;

    @Before
    public void setUp() throws Exception {
        valueWithNumber = toStringValue("13");
        valueNotANumber = toStringValue("Not a number");
    }

    @Test
    public void testIsId() {
    	assertFalse(valueWithNumber.isId());
    	assertFalse(valueNotANumber.isId());
    }

    @Test
    public void testAsId() {
    	assertEquals(NilValue.getNil().asId(), valueWithNumber.asId());
    	assertEquals(NilValue.getNil().asId(), valueNotANumber.asId());
    }

    @Test
    public void testIsBoolean() {
        assertFalse(valueWithNumber.isBoolean());
        assertFalse(valueNotANumber.isBoolean());
    }

    @Test
    public void testAsBoolean() {
        assertFalse(valueWithNumber.asBoolean());
        assertFalse(valueNotANumber.asBoolean());
    }

    @Test
    public void testIsLong() {
        assertFalse(valueWithNumber.isLong());
        assertFalse(valueNotANumber.isLong());
    }

    @Test
    public void testAsLong() {
        assertEquals(Long.valueOf(13), valueWithNumber.asLong());
        assertEquals(Long.valueOf(0), valueNotANumber.asLong());
    }

    @Test
    public void testIsDouble() {
        assertFalse(valueWithNumber.isDouble());
        assertFalse(valueNotANumber.isDouble());
    }

    @Test
    public void testAsDouble() {
        assertEquals(Double.valueOf(13.0), valueWithNumber.asDouble());
        assertEquals(Double.valueOf(0.0), valueNotANumber.asDouble());
    }

    @Test
    public void testIsString() {
        assertTrue(valueWithNumber.isString());
        assertTrue(valueNotANumber.isString());
    }

    @Test
    public void testAsString() {
        assertEquals("13", valueWithNumber.asString());
        assertEquals("Not a number", valueNotANumber.asString());
    }

    @Test
    public void testIsComplexProperty() {
        assertFalse(valueWithNumber.isComplexProperty());
        assertFalse(valueNotANumber.isComplexProperty());
    }

    @Test
    public void testAsComplexProperty() {
        assertEquals(getNilPropertyset(), valueWithNumber.asComplexProperty());
        assertEquals(getNilPropertyset(), valueNotANumber.asComplexProperty());
    }

    @Test
    public void testIsReference() {
        assertFalse(valueWithNumber.isReference());
        assertFalse(valueNotANumber.isReference());
    }

    @Test
    public void testAsReference() {
        assertEquals(getNilPropertyset(), valueWithNumber.asReference());
        assertEquals(getNilPropertyset(), valueNotANumber.asReference());
    }

    @Test
    public void testIsList() {
        assertFalse(valueWithNumber.isList());
        assertFalse(valueNotANumber.isList());
    }

    @Test
    public void testAsList() {
    	ValueList emptyList1 = valueWithNumber.asList();
    	assertTrue(emptyList1.isEmpty());
    	ValueList emptyList2 = valueNotANumber.asList();
    	assertTrue(emptyList2.isEmpty());
    }

    /**
     * Test av {@link StringPropertyvalue#hashCode()}.
     */
    @Test
    public void testHashCode() {
        Value nullStringValue = toStringValue(null);
        assertEquals(31, nullStringValue.hashCode());
        Value foo = toStringValue("foo");
        assertEquals(101605, foo.hashCode());
    }

    /**
     * Test av {@link StringPropertyvalue#equals(Object)}.
     */
    @Test
    public void testEquals() {
        Value nullStringValue = toStringValue(null);
        Value value = toStringValue("foobar");
        assertFalse(nullStringValue.equals(null));
        assertFalse(nullStringValue.equals(NilValue.getNil()));
        assertTrue(nullStringValue.equals(nullStringValue));
        assertFalse(nullStringValue.equals(value));
        assertFalse(value.equals(nullStringValue));
    }

    /**
     * Test av {@link StringPropertyvalue#toString()}.
     */
    @Test
    public void testToString() {
        Value nullLongValue = toStringValue(null);
        assertEquals("StringPropertyvalue [value=]", nullLongValue.toString());
        Value value = toStringValue("bar");
        assertEquals("StringPropertyvalue [value=bar]", value.toString());
    }

}
