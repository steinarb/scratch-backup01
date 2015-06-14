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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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

        PropertysetPropertyvalueBase other = (PropertysetPropertyvalueBase) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        }

        if (value.getId() != null) {
            return value.getId().equals(other.value.getId());
        }


        if (other.value.getId() != null) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ReferencePropertyvalue [value=" + value + "]";
    }

}
