package no.priv.bang.modeling.modelstore.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;

public class PropertyvalueArrayList implements PropertyvalueList {

    List<Propertyvalue> arrayList = new ArrayList<Propertyvalue>();

    public boolean add(Propertyvalue e) {
        return arrayList.add(e);
    }

    public void add(int index, Propertyvalue element) {
        arrayList.add(index, element);
    }

    public boolean addAll(Collection<? extends Propertyvalue> c) {
        return arrayList.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends Propertyvalue> c) {
        return arrayList.addAll(index, c);
    }

    public void clear() {
        arrayList.clear();
    }

    public boolean contains(Object o) {
        return arrayList.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return arrayList.containsAll(c);
    }

    public boolean equals(Object o) {
        return arrayList.equals(o);
    }

    public Propertyvalue get(int index) {
        return arrayList.get(index);
    }

    public int hashCode() {
        return arrayList.hashCode();
    }

    public int indexOf(Object o) {
        return arrayList.indexOf(o);
    }

    public boolean isEmpty() {
        return arrayList.isEmpty();
    }

    public Iterator<Propertyvalue> iterator() {
        return arrayList.iterator();
    }

    public int lastIndexOf(Object o) {
        return arrayList.lastIndexOf(o);
    }

    public ListIterator<Propertyvalue> listIterator() {
        return arrayList.listIterator();
    }

    public ListIterator<Propertyvalue> listIterator(int index) {
        return arrayList.listIterator(index);
    }

    public boolean remove(Object o) {
        return arrayList.remove(o);
    }

    public Propertyvalue remove(int index) {
        return arrayList.remove(index);
    }

    public boolean removeAll(Collection<?> c) {
        return arrayList.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return arrayList.retainAll(c);
    }

    public Propertyvalue set(int index, Propertyvalue element) {
        return arrayList.set(index, element);
    }

    public int size() {
        return arrayList.size();
    }

    public List<Propertyvalue> subList(int fromIndex, int toIndex) {
        return arrayList.subList(fromIndex, toIndex);
    }

    public Object[] toArray() {
        return arrayList.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return arrayList.toArray(a);
    }

}
