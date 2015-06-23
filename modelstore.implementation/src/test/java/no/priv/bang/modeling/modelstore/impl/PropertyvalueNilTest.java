package no.priv.bang.modeling.modelstore;

import static org.junit.Assert.*;
import org.junit.Test;
import static no.priv.bang.modeling.modelstore.impl.Propertyvalues.*;

/**
 * Unit tests for {@link PropertyvalueNil}.
 *
 * @author Steinar Bang
 *
 */
public class PropertyvalueNilTest {

    @Test
    public void testIsId() {
        assertFalse(PropertyvalueNil.getNil().isId());
    }

    @Test
    public void testIsBoolean() {
        assertFalse(PropertyvalueNil.getNil().isBoolean());
    }

    @Test
    public void testAsBoolean() {
        assertFalse(PropertyvalueNil.getNil().asBoolean());
        assertEquals(Boolean.valueOf(false), PropertyvalueNil.getNil().asBoolean());
    }

    @Test
    public void testIsLong() {
        assertFalse(PropertyvalueNil.getNil().isLong());
    }

    @Test
    public void testAsLong() {
        assertEquals(Long.valueOf(0), PropertyvalueNil.getNil().asLong());
    }

    @Test
    public void testIsDouble() {
        assertFalse(PropertyvalueNil.getNil().isDouble());
    }

    @Test
    public void testAsDouble() {
        assertEquals(Double.valueOf(0.0), PropertyvalueNil.getNil().asDouble());
    }

    @Test
    public void testIsString() {
        assertFalse(PropertyvalueNil.getNil().isString());
    }

    @Test
    public void testAsString() {
        assertEquals("", PropertyvalueNil.getNil().asString());
    }

    @Test
    public void testIsComplexProperty() {
        assertFalse(PropertyvalueNil.getNil().isComplexProperty());
    }

    @Test
    public void testAsComplexProperty() {
        assertEquals(PropertysetNil.getNil(), PropertyvalueNil.getNil().asComplexProperty());
    }

    @Test
    public void testIsReference() {
        assertFalse(PropertyvalueNil.getNil().isReference());
    }

    @Test
    public void testAsReference() {
        assertEquals(PropertysetNil.getNil(), PropertyvalueNil.getNil().asReference());
    }

    @Test
    public void testIsList() {
        assertFalse(PropertyvalueNil.getNil().isList());
    }

    @Test
    public void testAsList() {
    	PropertyvalueList emptyList = PropertyvalueNil.getNil().asList();
    	assertTrue(emptyList.isEmpty());
        assertEquals(0, emptyList.size());

        // Verify that the list can't be modified.
        emptyList.add(toBooleanValue(true));
        emptyList.add(toLongValue(13));
        emptyList.add(toDoubleValue(2.78));
        emptyList.add(toStringValue("foo bar!"));
    	assertTrue(emptyList.isEmpty());
        assertEquals(0, emptyList.size());
    }

    @Test
    public void testHashCode() {
        assertEquals(0, PropertyvalueNil.getNil().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("PropertyvalueNil []", PropertyvalueNil.getNil().toString());
    }

}
