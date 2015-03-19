package no.priv.bang.modeling.modelstore.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
public final class EmptyPropertyValueList implements PropertyvalueList {

    private final Propertyvalue[] emptyArray = new Propertyvalue[0];

    public boolean add(Propertyvalue e) {
        return false;
    }

    public void add(int index, Propertyvalue element) { }

    public boolean addAll(Collection<? extends Propertyvalue> c) {
        return false;
    }

    public boolean addAll(int index, Collection<? extends Propertyvalue> c) {
        return false;
    }

    public void clear() { }

    public boolean contains(Object o) {
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public Propertyvalue get(int index) {
        return PropertyvalueNil.getNil();
    }

    public int indexOf(Object o) {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }

    public Iterator<Propertyvalue> iterator() {
        return null;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator<Propertyvalue> listIterator() {
        return null;
    }

    public ListIterator<Propertyvalue> listIterator(int index) {
        return null;
    }

    public boolean remove(Object o) {
        return false;
    }

    public Propertyvalue remove(int index) {
        return PropertyvalueNil.getNil();
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public Propertyvalue set(int index, Propertyvalue element) {
        return PropertyvalueNil.getNil();
    }

    public int size() {
        return 0;
    }

    public List<Propertyvalue> subList(int fromIndex, int toIndex) {
        return PropertyvalueNil.getNil().asList();
    }

    public Object[] toArray() {
        return emptyArray ;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        return (T[]) new Propertyvalue[0];
    }

}
