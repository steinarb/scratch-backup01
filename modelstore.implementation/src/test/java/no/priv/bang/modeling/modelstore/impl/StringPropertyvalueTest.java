package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import no.priv.bang.modeling.modelstore.PropertysetNil;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link StringPropertyvalue}.
 *
 * @author Steinar Bang
 *
 */
public class StringPropertyvalueTest {

    private Propertyvalue valueWithNumber;
    private Propertyvalue valueNotANumber;

    @Before
    public void setUp() throws Exception {
        valueWithNumber = new StringPropertyvalue("13");
        valueNotANumber = new StringPropertyvalue("Not a number");
    }

    @Test
    public void testIsId() {
    	assertFalse(valueWithNumber.isId());
    	assertFalse(valueNotANumber.isId());
    }

    @Test
    public void testAsId() {
    	assertEquals(PropertyvalueNil.getNil().asId(), valueWithNumber.asId());
    	assertEquals(PropertyvalueNil.getNil().asId(), valueNotANumber.asId());
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
        assertEquals(PropertysetNil.getNil(), valueWithNumber.asComplexProperty());
        assertEquals(PropertysetNil.getNil(), valueNotANumber.asComplexProperty());
    }

    @Test
    public void testIsReference() {
        assertFalse(valueWithNumber.isReference());
        assertFalse(valueNotANumber.isReference());
    }

    @Test
    public void testAsReference() {
        assertEquals(PropertysetNil.getNil(), valueWithNumber.asReference());
        assertEquals(PropertysetNil.getNil(), valueNotANumber.asReference());
    }

    @Test
    public void testIsList() {
        assertFalse(valueWithNumber.isList());
        assertFalse(valueNotANumber.isList());
    }

    @Test
    public void testAsList() {
    	PropertyvalueList emptyList1 = valueWithNumber.asList();
    	assertTrue(emptyList1.isEmpty());
    	PropertyvalueList emptyList2 = valueNotANumber.asList();
    	assertTrue(emptyList2.isEmpty());
    }

    /**
     * Test av {@link StringPropertyvalue#hashCode()}.
     */
    @Test
    public void testHashCode() {
        StringPropertyvalue nullStringValue = new StringPropertyvalue(null);
        assertEquals(31, nullStringValue.hashCode());
        StringPropertyvalue foo = new StringPropertyvalue("foo");
        assertEquals(101605, foo.hashCode());
    }

    /**
     * Test av {@link StringPropertyvalue#equals(Object)}.
     */
    @Test
    public void testEquals() {
        StringPropertyvalue nullStringValue = new StringPropertyvalue(null);
        StringPropertyvalue value = new StringPropertyvalue("foobar");
        assertFalse(nullStringValue.equals(null));
        assertFalse(nullStringValue.equals(PropertyvalueNil.getNil()));
        assertTrue(nullStringValue.equals(nullStringValue));
        assertFalse(nullStringValue.equals(value));
        assertFalse(value.equals(nullStringValue));
    }

    /**
     * Test av {@link StringPropertyvalue#toString()}.
     */
    @Test
    public void testToString() {
        StringPropertyvalue nullLongValue = new StringPropertyvalue(null);
        assertEquals("StringPropertyvalue [value=]", nullLongValue.toString());
        StringPropertyvalue value = new StringPropertyvalue("bar");
        assertEquals("StringPropertyvalue [value=bar]", value.toString());
    }

}
