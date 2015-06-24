package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;

import java.util.List;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
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
        assertEquals(0, list.size());
        list.add(1024, null);
        assertEquals(0, list.size());
    }

    @Test
    public void testAddAll() {
        assertFalse(list.addAll(new EmptyPropertyvalueList()));
        assertFalse(list.addAll(0, new EmptyPropertyvalueList()));
    }

    @Test
    public void testContains() {
        assertFalse(list.contains(null));
    }

    @Test
    public void testContainsAll() {
        assertTrue(list.containsAll(new EmptyPropertyvalueList()));
    }

    @Test
    public void testGet() {
        assertEquals(PropertyvalueNil.getNil(), list.get(135));
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

    @Test(expected=IndexOutOfBoundsException.class)
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
