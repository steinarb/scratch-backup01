package no.priv.bang.modeling.modelstore.impl;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;

class PropertyvalueArrayList extends AbstractList<Value> implements ValueList {

    List<Value> arrayList = new ArrayList<Value>();

    @Override
    public Value set(int paramInt, Value paramE) {
        return arrayList.set(paramInt, paramE);
    }

    @Override
    public void add(int paramInt, Value paramE) {
        arrayList.add(paramInt, paramE);
    }

    @Override
    public Value remove(int paramInt) {
        return arrayList.remove(paramInt);
    }

    @Override
    public Value get(int index) {
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
