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
 * Unit tests for {@link ListPropertyvalue}.
 *
 * @author Steinar Bang
 *
 */
public class ListPropertyvalueTest {

    private PropertyvalueList propertyvalueList;
    private Propertyvalue value;

    @Before
    public void setUp() throws Exception {
        propertyvalueList = newList();
        propertyvalueList.add(toBooleanValue(Boolean.TRUE));
        propertyvalueList.add(toLongValue(42));
        propertyvalueList.add(toDoubleValue(2.78));
        propertyvalueList.add(toStringValue("foo bar"));
        value = toListValue(propertyvalueList);
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
        assertFalse(value.isReference());
    }

    @Test
    public void testAsReference() {
        assertEquals(PropertysetNil.getNil(), value.asReference());
    }

    @Test
    public void testIsList() {
        assertTrue(value.isList());
    }

    @Test
    public void testAsList() {
    	PropertyvalueList list = value.asList();
    	assertFalse(list.isEmpty());
    	assertEquals(propertyvalueList.size(), list.size());
    }

    /**
     * Verify that a property value containing an empty list is
     * equal to the list extracted from a {@link PropertyvalueNil}.
     */
    @Test
    public void testEmptyListEqualsNilList() {
    	Propertyvalue emptylist = toListValue(newList());
    	Propertyvalue nil = PropertyvalueNil.getNil();
    	assertTrue(emptylist.equals(nil));

    	// TODO: should the equals be implemented in the nil object as well?
    	assertFalse(nil.equals(emptylist));
    }

    /**
     * Test av {@link ListPropertyvalue#hashCode()}.
     */
    @Test
    public void testHashCode() {
        Propertyvalue nullListValue = toListValue(null);
        assertEquals(32, nullListValue.hashCode());
        Propertyvalue foo = toListValue(newList());
        assertEquals(63, foo.hashCode());
        assertEquals(-24528609, value.hashCode());
    }

    /**
     * Test av {@link ListPropertyvalue#equals(Object)}.
     */
    @Test
    public void testEquals() {
        Propertyvalue nullListValue = toListValue(null);
        Propertyvalue emptyvalue = toListValue(newList());
        PropertyvalueList list = newList();
        list.add(toDoubleValue(3.14));
        Propertyvalue otherValue = toListValue(list);
        assertFalse(nullListValue.equals(null));
        assertTrue(nullListValue.equals(PropertyvalueNil.getNil()));
        assertTrue(nullListValue.equals(nullListValue));
        assertTrue(nullListValue.equals(emptyvalue));
        assertFalse(emptyvalue.equals(nullListValue));
        assertTrue(value.equals(value));
        assertFalse(value.equals(emptyvalue));
        assertFalse(value.equals(nullListValue));
        assertFalse(emptyvalue.equals(value));
        Propertyvalue stringvalue = toStringValue("foobar");
        assertFalse(value.equals(stringvalue));
        assertFalse(value.equals(otherValue));
    }

    /**
     * Test av {@link ListPropertyvalue#toString()}.
     */
    @Test
    public void testToString() {
        Propertyvalue nullListValue = toListValue(null);
        assertEquals("ListPropertyvalue [value=[]]", nullListValue.toString());
        PropertyvalueList list = newList();
        list.add(toStringValue("foo"));
        list.add(toStringValue("bar"));
        list.add(toDoubleValue(2.78));
        Propertyvalue otherValue = toListValue(list);
        assertEquals("ListPropertyvalue [value=[StringPropertyvalue [value=foo], StringPropertyvalue [value=bar], DoublePropertyvalue [value=2.78]]]", otherValue.toString());
        assertEquals("ListPropertyvalue [value=[BooleanPropertyvalue [value=true], LongPropertyvalue [value=42], DoublePropertyvalue [value=2.78], StringPropertyvalue [value=foo bar]]]", value.toString());
    }

}
