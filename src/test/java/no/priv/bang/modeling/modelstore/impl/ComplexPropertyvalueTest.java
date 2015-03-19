package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link ComplexPropertyvalue}.
 *
 * @author Steinar Bang
 *
 */
public class ComplexPropertyvalueTest {

    private Propertyset complexProperty;
    private Propertyvalue value;

    @Before
    public void setUp() throws Exception {
        complexProperty = new PropertysetImpl();
        value = new ComplexPropertyvalue(complexProperty);
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
        assertTrue(value.isComplexProperty());
    }

    @Test
    public void testAsComplexProperty() {
        assertEquals(complexProperty, value.asComplexProperty());
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
