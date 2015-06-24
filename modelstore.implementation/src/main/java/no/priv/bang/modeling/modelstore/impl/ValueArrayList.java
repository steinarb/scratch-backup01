package no.priv.bang.modeling.modelstore.impl;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;

class PropertyvalueArrayList extends AbstractList<Propertyvalue> implements PropertyvalueList {

    List<Propertyvalue> arrayList = new ArrayList<Propertyvalue>();

    @Override
    public Propertyvalue set(int paramInt, Propertyvalue paramE) {
        return arrayList.set(paramInt, paramE);
    }

    @Override
    public void add(int paramInt, Propertyvalue paramE) {
        arrayList.add(paramInt, paramE);
    }

    @Override
    public Propertyvalue remove(int paramInt) {
        return arrayList.remove(paramInt);
    }

    @Override
    public Propertyvalue get(int index) {
        return arrayList.get(index);
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + arrayList.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        PropertyvalueArrayList other = (PropertyvalueArrayList) obj;
        return arrayList.equals(other.arrayList);
    }

}
