package no.priv.bang.modeling.modelstore.impl;

import java.util.AbstractList;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;

/**
 * A list implementation that signifies the nil list value.
 * This list is empty and it cannot have objects added, and
 * it will throw no exceptions on value access.
 *
 * @author Steinar Bang
 *
 */
public final class EmptyValueList extends AbstractList<Value> implements ValueList {
    private final Value[] emptyArray = new Value[0];

    @Override
    public void add(int index, Value element) {
        // Just drop the added elements on the floor.
    }

    @Override
    public boolean add(Value e) {
        // Just drop the added elements on the floor. Always return false
        return false;
    }

    @Override
    public Value set(int index, Value element) {
        // Just drop the added elements on the floor. Always return PropertyvalueNil
        return NilValue.getNil();
    }

    @Override
    public Value get(int index) {
        return NilValue.getNil();
    }

    @Override
    public Value remove(int index) {
        // Always return PropertyvalueNil
        return NilValue.getNil();
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
