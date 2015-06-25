package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link ComplexPropertyvalue}.
 *
 * @author Steinar Bang
 *
 */
public class ComplexValueTest {

    private Propertyset complexProperty;
    private Value value;

    @Before
    public void setUp() throws Exception {
        complexProperty = new PropertysetImpl();
        complexProperty.setBooleanProperty("boolean", Boolean.TRUE);
        complexProperty.setLongProperty("long", Long.valueOf(42));
        complexProperty.setDoubleProperty("double", Double.valueOf(2.78));
        complexProperty.setStringProperty("string", "foo bar");
        value = toComplexValue(complexProperty);
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
     * Test av {@link ComplexPropertyvalue#hashCode()}.
     */
    @Test
    public void testHashCode() {
        Value nullComplexValue = toComplexValue(null);
        assertEquals(31, nullComplexValue.hashCode());
        assertEquals(1958288831, value.hashCode());
    }

    /**
     * Test av {@link ComplexPropertyvalue#equals(Object)}.
     */
    @Test
    public void testEquals() {
        Value nullComplexValue = toComplexValue(null);
        assertFalse(nullComplexValue.equals(null));
        assertFalse(nullComplexValue.equals(PropertyvalueNil.getNil().asComplexProperty()));
        assertTrue(nullComplexValue.equals(nullComplexValue));
        assertFalse(nullComplexValue.equals(value));
        assertFalse(value.equals(nullComplexValue));
        assertTrue(value.equals(value));
    }

    /**
     * Test av {@link ComplexPropertyvalue#toString()}.
     */
    @Test
    public void testToString() {
        Value nullComplexValue = toComplexValue(null);
        assertEquals("ComplexPropertyvalue [value=PropertysetNil []]", nullComplexValue.toString());
        assertEquals("ComplexPropertyvalue [value=PropertysetImpl [properties={string=StringPropertyvalue [value=foo bar], boolean=BooleanPropertyvalue [value=true], double=DoublePropertyvalue [value=2.78], long=LongPropertyvalue [value=42]}]]", value.toString());
    }

}
