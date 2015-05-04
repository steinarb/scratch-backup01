package no.priv.bang.modeling.modelstore.impl;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;

public class PropertyvalueArrayList extends AbstractList<Propertyvalue> implements PropertyvalueList {

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

}
