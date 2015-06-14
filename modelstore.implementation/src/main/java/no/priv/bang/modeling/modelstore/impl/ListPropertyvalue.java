package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

public class ListPropertyvalue extends PropertyvalueBase {

    private PropertyvalueList value;

    public ListPropertyvalue(PropertyvalueList value) {
    	if (null == value) {
            this.value = PropertyvalueNil.getNil().asList();
    	} else {
            this.value = value;
    	}
    }

    public Boolean asBoolean() {
        return PropertyvalueNil.getNil().asBoolean();
    }

    public Long asLong() {
        return PropertyvalueNil.getNil().asLong();
    }

    public Double asDouble() {
        return PropertyvalueNil.getNil().asDouble();
    }

    public String asString() {
        return PropertyvalueNil.getNil().asString();
    }

    public Propertyset asComplexProperty() {
        return PropertyvalueNil.getNil().asComplexProperty();
    }

    public Propertyset asReference() {
        return PropertyvalueNil.getNil().asReference();
    }

    @Override
    public boolean isList() {
        return true;
    }

    public PropertyvalueList asList() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ListPropertyvalue)) {
            return false;
        }

        ListPropertyvalue objVal = (ListPropertyvalue) obj;
        return (asList().equals(objVal.asList()));
    }

    @Override
    public int hashCode() {
        return (value != null) ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return (value != null) ? value.toString() : "null";
    }

}
