package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import static no.priv.bang.modeling.modelstore.impl.Propertyvalues.*;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
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
        PropertyvalueList list = newList();
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
     * Test av {@link PropertyvalueArrayList#hashCode()}.
     */
    @Test
    public void testHashCode() {
        PropertyvalueList list = newList();
        assertEquals(32, list.hashCode());
        list.add(toLongValue(1L));
        assertEquals(2016, list.hashCode());
    }

    /**
     * Test av {@link PropertyvalueArrayList#equals(Object)}.
     */
    @Test
    public void testEquals() {
        PropertyvalueList list = newList();
        assertTrue(list.equals(list));
        assertFalse(list.equals(null));
        PropertyvalueList emptylist = newList();
        assertTrue(list.equals(emptylist));
        assertFalse(list.equals(PropertyvalueNil.getNil().asList()));
        assertFalse(list.equals(new EmptyPropertyvalueList()));
        list.add(toLongValue(1L));
        assertFalse(list.equals(emptylist));
        PropertyvalueList otherlistWithSameItem = newList();
        otherlistWithSameItem.add(toLongValue(1L));
        assertTrue(list.equals(otherlistWithSameItem));
    }
}
