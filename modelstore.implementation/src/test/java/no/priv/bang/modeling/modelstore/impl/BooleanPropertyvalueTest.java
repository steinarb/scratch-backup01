package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
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
        value = new BooleanPropertyvalue(Boolean.TRUE);
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

}
