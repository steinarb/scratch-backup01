package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link EmptyValueList}.
 *
 * @author Steinar Bang
 *
 */
public class EmptyValueListTest {

    private ValueList list;

    @Before
    public void setUp() throws Exception {
        list = new EmptyValueList();
    }

    @Test
    public void testAdd() {
        assertFalse(list.add((Value)null));
        assertEquals(0, list.size());
        list.add(1024, null);
        assertEquals(0, list.size());
        list.add(Boolean.TRUE);
        assertEquals(0, list.size());
        list.add(true);
        assertEquals(0, list.size());
        list.add(Long.valueOf(3));
        assertEquals(0, list.size());
        list.add(37);
        assertEquals(0, list.size());
        list.add(Double.valueOf(3.14));
        assertEquals(0, list.size());
        list.add(2.78);
        assertEquals(0, list.size());
        list.add("foo");
        assertEquals(0, list.size());
        list.add(new PropertysetImpl());
        assertEquals(0, list.size());
        list.add(newList());
        assertEquals(0, list.size());
    }

    @Test
    public void testAddAll() {
        assertFalse(list.addAll(new EmptyValueList()));
        assertFalse(list.addAll(0, new EmptyValueList()));
    }

    @Test
    public void testContains() {
        assertFalse(list.contains(null));
    }

    @Test
    public void testContainsAll() {
        assertTrue(list.containsAll(new EmptyValueList()));
    }

    @Test
    public void testGet() {
        assertEquals(getNil(), list.get(135));
    }

    @Test
    public void testIndexOf() {
        assertEquals(-1, list.indexOf(null));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
    }

    @Test
    public void testIterator() {
        assertNotNull(list.iterator());
    }

    @Test
    public void testLastIndexOf() {
        assertEquals(-1, list.lastIndexOf(null));
    }

    @Test
    public void testListIterator() {
        assertNotNull(list.listIterator());
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testListIteratorWithArg() {
        assertNotNull(list.listIterator(135));
    }

    @Test
    public void testRemove() {
        assertFalse(list.remove(null));
        assertEquals(getNil(), list.remove(135));
    }

    @Test
    public void testRemoveAll() {
        assertFalse(list.removeAll(Collections.emptyList()));
    }

    @Test
    public void testRetainAll() {
        assertFalse(list.retainAll(Collections.emptyList()));
    }

    @Test
    public void testSet() {
        assertEquals(getNil(), list.set(135, (Value)null));
        assertEquals(getNil(), list.set(135, Boolean.TRUE));
        assertEquals(getNil(), list.set(135, true));
        assertEquals(getNil(), list.set(135, Long.valueOf(1)));
        assertEquals(getNil(), list.set(135, 2));
        assertEquals(getNil(), list.set(135, Double.valueOf(1.1)));
        assertEquals(getNil(), list.set(135, 2.4));
        assertEquals(getNil(), list.set(135, "foobar"));
        assertEquals(getNil(), list.set(135, new PropertysetImpl()));
        assertEquals(getNil(), list.set(135, newList()));
    }

    @Test
    public void testSize() {
        assertEquals(0, list.size());
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testSubList() {
        List<Value> sublist = list.subList(1, 2);
        assertTrue(sublist.isEmpty());
    }

    @Test
    public void testToArray() {
        Object[] array1 = list.toArray();
        assertEquals(0, array1.length);
        Value[] valueArray = new Value[10];
        Value[] array2 = list.toArray(valueArray);
        assertEquals(0, array2.length);
    }

}
