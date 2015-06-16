package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

import org.junit.Test;

/**
 * Unit tests for {@link PropertyvalueArrayList}.
 *
 * @author Steinar Bang
 *
 */
public class PropertyvalueArrayListTest {
    /**
     * Test of basic list operations.
     */
    @Test
    public void testAddGetPutRemove() {
        PropertyvalueList list = new PropertyvalueArrayList();
        assertEquals(0, list.size());
        list.add(new StringPropertyvalue("a"));
        list.add(new LongPropertyvalue(4L));
        assertEquals(2, list.size());
        assertEquals(4L, list.get(1).asLong().longValue());
        list.set(1, new LongPropertyvalue(3L));
        list.remove(0);
        assertEquals(1, list.size());
        assertEquals(3L, list.get(0).asLong().longValue());
    }

    /**
     * Test av {@link PropertyvalueArrayList#hashCode()}.
     */
    @Test
    public void testHashCode() {
        PropertyvalueList list = new PropertyvalueArrayList();
        assertEquals(32, list.hashCode());
        list.add(new LongPropertyvalue(1L));
        assertEquals(2016, list.hashCode());
    }

    /**
     * Test av {@link PropertyvalueArrayList#equals(Object)}.
     */
    @Test
    public void testEquals() {
        PropertyvalueList list = new PropertyvalueArrayList();
        assertTrue(list.equals(list));
        assertFalse(list.equals(null));
        PropertyvalueList emptylist = new PropertyvalueArrayList();
        assertTrue(list.equals(emptylist));
        assertFalse(list.equals(PropertyvalueNil.getNil().asList()));
        assertFalse(list.equals(new EmptyPropertyvalueList()));
        list.add(new LongPropertyvalue(1L));
        assertFalse(list.equals(emptylist));
        PropertyvalueList otherlistWithSameItem = new PropertyvalueArrayList();
        otherlistWithSameItem.add(new LongPropertyvalue(1L));
        assertTrue(list.equals(otherlistWithSameItem));
    }
}
