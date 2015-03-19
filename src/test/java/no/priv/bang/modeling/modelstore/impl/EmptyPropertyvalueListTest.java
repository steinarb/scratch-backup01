package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;

import java.util.List;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link EmptyPropertyvalueList}.
 *
 * @author Steinar Bang
 *
 */
public class EmptyPropertyvalueListTest {

    private PropertyvalueList value;

    @Before
    public void setUp() throws Exception {
        value = new EmptyPropertyvalueList();
    }

    @Test
    public void testAdd() {
        assertFalse(value.add(null));
    }

    @Test
    public void testAddAll() {
        assertFalse(value.addAll(null));
        assertFalse(value.addAll(1, null));
    }

    @Test
    public void testContains() {
        assertFalse(value.contains(null));
    }

    @Test
    public void testContainsAll() {
        assertFalse(value.containsAll(null));
    }

    @Test
    public void testGet() {
        assertEquals(PropertyvalueNil.getNil(), value.get(135));
    }

    @Test
    public void testIndexOf() {
        assertEquals(0, value.indexOf(null));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(value.isEmpty());
    }

    @Test
    public void testIterator() {
        assertNull(value.iterator());
    }

    @Test
    public void testLastIndexOf() {
        assertEquals(0, value.lastIndexOf(null));
    }

    @Test
    public void testListIterator() {
        assertNull(value.listIterator());
        assertNull(value.listIterator(135));
    }

    @Test
    public void testRemove() {
        assertFalse(value.remove(null));
        assertEquals(PropertyvalueNil.getNil(), value.remove(135));
    }

    @Test
    public void testRemoveAll() {
        assertFalse(value.removeAll(null));
    }

    @Test
    public void testRetainAll() {
        assertFalse(value.retainAll(null));
    }

    @Test
    public void testSet() {
        assertEquals(PropertyvalueNil.getNil(), value.set(135, null));
    }

    @Test
    public void testSize() {
        assertEquals(0, value.size());
    }

    @Test
    public void testSubList() {
        List<Propertyvalue> list = value.subList(1, 2);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testToArray() {
        Object[] array1 = value.toArray();
        assertEquals(0, array1.length);
        Propertyvalue[] valueArray = new Propertyvalue[10];
        Propertyvalue[] array2 = value.toArray(valueArray);
        assertEquals(0, array2.length);
    }

}
