package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import static no.priv.bang.modeling.modelstore.impl.Values.*;

import java.util.UUID;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetManager;
import no.priv.bang.modeling.modelstore.ValueList;

import org.junit.Test;

/**
 * Unit tests for {@link ValueArrayList}.
 *
 * @author Steinar Bang
 *
 */
public class ValueArrayListTest {
    /**
     * Test of basic list operations.
     */
    @Test
    public void testAddGetPutRemove() {
        ValueList list = newList();
        assertEquals(0, list.size());
        list.add(toStringValue("a"));
        list.add(toLongValue(4L));
        assertEquals(2, list.size());
        assertEquals(4L, list.get(1).asLong().longValue());
        list.set(1, toLongValue(3L));
        list.remove(0);
        assertEquals(1, list.size());
        assertEquals(3L, list.get(0).asLong().longValue());
    }

    /**
     * Test of basic list operations for boolean values.
     */
    @Test
    public void testAddSetGetBoolean() {
        ValueList list = newList();
        assertEquals(0, list.size());
        list.add(Boolean.TRUE);
        list.add(true);
        assertTrue(list.get(0).asBoolean());
        assertTrue(list.get(1).asBoolean());
        list.set(0, false);
        list.set(1, Boolean.FALSE);
        assertFalse(list.get(0).asBoolean());
        assertFalse(list.get(1).asBoolean());
    }

    /**
     * Test of basic list operations for long values.
     */
    @Test
    public void testAddSetGetLong() {
        ValueList list = newList();
        assertEquals(0, list.size());
        list.add(Long.valueOf(1));
        list.add(2L);
        assertEquals(1, list.get(0).asLong().longValue());
        assertEquals(2, list.get(1).asLong().longValue());
        list.set(0, 3L);
        list.set(1, Long.valueOf(4));
        assertEquals(3, list.get(0).asLong().longValue());
        assertEquals(4, list.get(1).asLong().longValue());
    }

    /**
     * Test of basic list operations for double values.
     */
    @Test
    public void testAddSetGetDouble() {
        ValueList list = newList();
        assertEquals(0, list.size());
        list.add(Double.valueOf(1.0));
        list.add(2.0);
        assertEquals(1.0, list.get(0).asDouble().doubleValue(), 0.0);
        assertEquals(2.0, list.get(1).asDouble().doubleValue(), 0.0);
        list.set(0, 3.0);
        list.set(1, Double.valueOf(4));
        assertEquals(3.0, list.get(0).asDouble().doubleValue(), 0.0);
        assertEquals(4.0, list.get(1).asDouble().doubleValue(), 0.0);
    }

    /**
     * Test of basic list operations for string values.
     */
    @Test
    public void testAddSetGetString() {
        ValueList list = newList();
        assertEquals(0, list.size());
        list.add("foo");
        list.add("bar");
        assertEquals("foo", list.get(0).asString());
        assertEquals("bar", list.get(1).asString());
        list.set(1, "foobar");
        assertEquals("foobar", list.get(1).asString());
    }

    /**
     * Test of basic list operations for {@link Propertyset} values.
     *
     * A {@link Propertyset} value where {@link Propertyset#hasId()} is
     * true, will become a {@link ReferenceValue}, if hasId is
     * false, it will become a {@link ComplexValue}.
     *
     * A null argument will become whatever {@link Values#toComplexValue(Propertyset)}
     * does with a null.
     */
    @Test
    public void testAddSetGetPropertyset() {
        PropertysetManager propertysetManager = new PropertysetManagerProvider().get();
        Propertyset objectWithoutId = propertysetManager.createPropertyset();
        objectWithoutId.setDoubleProperty("c", 3.14);
        UUID id = UUID.randomUUID();
        Propertyset objectWithId = propertysetManager.findPropertyset(id);
        objectWithId.setLongProperty("a", Long.valueOf(2));
        objectWithId.setStringProperty("b", "foo bar");
        Propertyset nullObject = null;
        ValueList list = newList();
        assertEquals(0, list.size());
        list.add(objectWithoutId);
        list.add(objectWithId);
        list.add(nullObject);
        assertTrue(list.get(0).isComplexProperty());
        assertTrue(list.get(1).isReference());
        assertTrue(list.get(2).isComplexProperty());
        list.set(0, nullObject);
        list.set(1, objectWithoutId);
        list.set(2, objectWithId);
        assertTrue(list.get(0).isComplexProperty());
        assertTrue(list.get(1).isComplexProperty());
        assertTrue(list.get(2).isReference());
    }

    /**
     * Test of basic list operations for {@link ValueList} values.
     *
     * A null argument will become whatever {@link Values#toListValue(ValueList)}
     * does with a null.
     */
    @Test
    public void testAddSetGetValueList() {
        ValueList value1 = newList();
        value1.add(3.14);
        value1.add(2.78);
        value1.add("foo");
        ValueList value2 = newList();
        value2.add(true);
        value2.add(false);
        ValueList nullList = null;

        // Create the list and insert lists into the list
        ValueList list = newList();
        assertEquals(0, list.size());
        list.add(value1);
        list.add(value2);
        list.add(nullList);
        assertEquals(3, list.size());
        assertEquals(3, list.get(0).asList().size());
        assertEquals(2, list.get(1).asList().size());
        assertEquals(0, list.get(2).asList().size());
        list.set(0, nullList);
        list.set(1, value1);
        list.set(2, value2);
        assertEquals(0, list.get(0).asList().size());
        assertEquals(3, list.get(1).asList().size());
        assertEquals(2, list.get(2).asList().size());
    }

    /**
     * Test av {@link ValueArrayList#hashCode()}.
     */
    @Test
    public void testHashCode() {
        ValueList list = newList();
        assertEquals(32, list.hashCode());
        list.add(toLongValue(1L));
        assertEquals(2016, list.hashCode());
    }

    /**
     * Test av {@link ValueArrayList#equals(Object)}.
     */
    @Test
    public void testEquals() {
        ValueList list = newList();
        assertTrue(list.equals(list));
        assertFalse(list.equals(null));
        ValueList emptylist = newList();
        assertTrue(list.equals(emptylist));
        assertFalse(list.equals(getNil().asList()));
        assertFalse(list.equals(new EmptyValueList()));
        list.add(toLongValue(1L));
        assertFalse(list.equals(emptylist));
        ValueList otherlistWithSameItem = newList();
        otherlistWithSameItem.add(toLongValue(1L));
        assertTrue(list.equals(otherlistWithSameItem));
    }
}
