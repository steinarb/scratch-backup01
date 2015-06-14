package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;

public class ReferencePropertyvalue extends PropertysetPropertyvalueBase {

    public ReferencePropertyvalue(Propertyset value) {
        super(value);
    }

    @Override
    public boolean isReference() {
        return true;
    }

    public Propertyset asComplexProperty() {
        return PropertysetNil.getNil();
    }

    public Propertyset asReference() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PropertysetImpl)) {
            return false;
        }

        try {
            Object objValue = obj.getClass().getDeclaredField("value").get(obj);
            Object objRefId = objValue.getClass().getMethod("getId", new Class<?>[0]).invoke(objValue, new Object[0]);
            return value.getId().equals(objRefId);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value != null && value.getId() != null ? value.getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return value != null && value.getId() != null ? value.getId().toString() : "null";
    }

}
