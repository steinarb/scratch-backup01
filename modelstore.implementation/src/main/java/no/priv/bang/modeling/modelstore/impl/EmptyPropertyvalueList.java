package no.priv.bang.modeling.modelstore.impl;

import java.util.AbstractList;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

/**
 * A list implementation that signifies the nil list value.
 * This list is empty and it cannot have objects added, and
 * it will throw no exceptions on value access.
 *
 * @author Steinar Bang
 *
 */
public final class EmptyPropertyvalueList extends AbstractList<Propertyvalue> implements PropertyvalueList {
    private final Propertyvalue[] emptyArray = new Propertyvalue[0];

    @Override
    public void add(int index, Propertyvalue element) {
        // Just drop the added elements on the floor.
    }

    @Override
    public boolean add(Propertyvalue e) {
        // Just drop the added elements on the floor. Always return false
        return false;
    }

    @Override
    public Propertyvalue set(int index, Propertyvalue element) {
        // Just drop the added elements on the floor. Always return PropertyvalueNil
        return PropertyvalueNil.getNil();
    }

    @Override
    public Propertyvalue get(int index) {
        return PropertyvalueNil.getNil();
    }

    @Override
    public Propertyvalue remove(int index) {
        // Always return PropertyvalueNil
        return PropertyvalueNil.getNil();
    }

    @Override
    public int size() {
        return 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) emptyArray;
    }

}
