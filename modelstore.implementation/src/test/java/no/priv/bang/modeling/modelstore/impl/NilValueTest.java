package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import no.priv.bang.modeling.modelstore.ValueList;

import org.junit.Test;

import static no.priv.bang.modeling.modelstore.impl.Values.*;

/**
 * Unit tests for {@link NilValue}.
 *
 * @author Steinar Bang
 *
 */
public class NilValueTest {

    @Test
    public void testIsId() {
        assertFalse(NilValue.getNil().isId());
    }

    @Test
    public void testIsBoolean() {
        assertFalse(NilValue.getNil().isBoolean());
    }

    @Test
    public void testAsBoolean() {
        assertFalse(NilValue.getNil().asBoolean());
        assertEquals(Boolean.valueOf(false), NilValue.getNil().asBoolean());
    }

    @Test
    public void testIsLong() {
        assertFalse(NilValue.getNil().isLong());
    }

    @Test
    public void testAsLong() {
        assertEquals(Long.valueOf(0), NilValue.getNil().asLong());
    }

    @Test
    public void testIsDouble() {
        assertFalse(NilValue.getNil().isDouble());
    }

    @Test
    public void testAsDouble() {
        assertEquals(Double.valueOf(0.0), NilValue.getNil().asDouble());
    }

    @Test
    public void testIsString() {
        assertFalse(NilValue.getNil().isString());
    }

    @Test
    public void testAsString() {
        assertEquals("", NilValue.getNil().asString());
    }

    @Test
    public void testIsComplexProperty() {
        assertFalse(NilValue.getNil().isComplexProperty());
    }

    @Test
    public void testAsComplexProperty() {
        assertEquals(getNilPropertyset(), NilValue.getNil().asComplexProperty());
    }

    @Test
    public void testIsReference() {
        assertFalse(NilValue.getNil().isReference());
    }

    @Test
    public void testAsReference() {
        assertEquals(getNilPropertyset(), NilValue.getNil().asReference());
    }

    @Test
    public void testIsList() {
        assertFalse(NilValue.getNil().isList());
    }

    @Test
    public void testAsList() {
    	ValueList emptyList = NilValue.getNil().asList();
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
        assertEquals(0, NilValue.getNil().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("PropertyvalueNil []", NilValue.getNil().toString());
    }

}
