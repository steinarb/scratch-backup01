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

    private PropertyvalueList list;

    @Before
    public void setUp() throws Exception {
        list = new EmptyPropertyvalueList();
    }

    @Test
    public void testAdd() {
        assertFalse(list.add(null));
    }

    @Test
    public void testAddAll() {
        assertFalse(list.addAll(null));
        assertFalse(list.addAll(1, null));
    }

    @Test
    public void testContains() {
        assertFalse(list.contains(null));
    }

    @Test
    public void testContainsAll() {
        assertFalse(list.containsAll(null));
    }

    @Test
    public void testGet() {
        assertEquals(PropertyvalueNil.getNil(), list.get(135));
    }

    @Test
    public void testIndexOf() {
        assertEquals(0, list.indexOf(null));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
    }

    @Test
    public void testIterator() {
        assertNull(list.iterator());
    }

    @Test
    public void testLastIndexOf() {
        assertEquals(0, list.lastIndexOf(null));
    }

    @Test
    public void testListIterator() {
        assertNull(list.listIterator());
        assertNull(list.listIterator(135));
    }

    @Test
    public void testRemove() {
        assertFalse(list.remove(null));
        assertEquals(PropertyvalueNil.getNil(), list.remove(135));
    }

    @Test
    public void testRemoveAll() {
        assertFalse(list.removeAll(null));
    }

    @Test
    public void testRetainAll() {
        assertFalse(list.retainAll(null));
    }

    @Test
    public void testSet() {
        assertEquals(PropertyvalueNil.getNil(), list.set(135, null));
    }

    @Test
    public void testSize() {
        assertEquals(0, list.size());
    }

    @Test
    public void testSubList() {
        List<Propertyvalue> sublist = list.subList(1, 2);
        assertTrue(sublist.isEmpty());
    }

    @Test
    public void testToArray() {
        Object[] array1 = list.toArray();
        assertEquals(0, array1.length);
        Propertyvalue[] valueArray = new Propertyvalue[10];
        Propertyvalue[] array2 = list.toArray(valueArray);
        assertEquals(0, array2.length);
    }

}
