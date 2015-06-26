package no.priv.bang.modeling.modelstore.impl;

import java.util.AbstractList;

import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Propertyset;
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
    public Value set(int index, Value element) {
        // Just drop the added elements on the floor. Always return NilValue
        return getNil();
    }

    public void set(int i, Boolean value) {
        // Just drop the added elements on the floor.
    }

    public void set(int i, boolean value) {
        // Just drop the added elements on the floor.
    }

    public void set(int i, Long value) {
        // Just drop the added elements on the floor.
    }

    public void set(int i, long value) {
        // Just drop the added elements on the floor.
    }

    public void set(int i, Double value) {
        // Just drop the added elements on the floor.
    }

    public void set(int i, double value) {
        // Just drop the added elements on the floor.
    }

    public void set(int i, String value) {
        // Just drop the added elements on the floor.
    }

    public void set(int i, Propertyset value) {
        // Just drop the added elements on the floor.
    }

    public void set(int i, ValueList value) {
        // Just drop the added elements on the floor.
    }

    @Override
    public void add(int index, Value element) {
        // Just drop the added elements on the floor.
    }

    @Override
    public boolean add(Value e) {
        // Just drop the added elements on the floor. Always return false
        return false;
    }

    public void add(Boolean value) {
        // Just drop the added elements on the floor.
    }

    public void add(boolean value) {
        // Just drop the added elements on the floor.
    }

    public void add(Long value) {
        // Just drop the added elements on the floor.
    }

    public void add(long value) {
        // Just drop the added elements on the floor.
    }

    public void add(Double value) {
        // Just drop the added elements on the floor.
    }

    public void add(double value) {
        // Just drop the added elements on the floor.
    }

    public void add(String value) {
        // Just drop the added elements on the floor.
    }

    public void add(Propertyset value) {
        // Just drop the added elements on the floor.
    }

    public void add(ValueList value) {
        // Just drop the added elements on the floor.
    }

    @Override
    public Value get(int index) {
        return getNil();
    }

    @Override
    public Value remove(int index) {
        // Always return NilValue
        return getNil();
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
