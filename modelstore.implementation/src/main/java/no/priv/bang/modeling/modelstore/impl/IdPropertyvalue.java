package no.priv.bang.modeling.modelstore.impl;

import java.util.UUID;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

class IdPropertyvalue implements Propertyvalue {

    private UUID value;

    public IdPropertyvalue(UUID value) {
        this.value = value;
    }

    public boolean isId() {
        return true;
    }

    public UUID asId() {
        return value;
    }

    public boolean isBoolean() {
        return false;
    }

    public Boolean asBoolean() {
        return PropertyvalueNil.getNil().asBoolean();
    }

    public boolean isLong() {
        return false;
    }

    public Long asLong() {
        return PropertyvalueNil.getNil().asLong();
    }

    public boolean isDouble() {
        return false;
    }

    public Double asDouble() {
        return PropertyvalueNil.getNil().asDouble();
    }

    public boolean isString() {
        return false;
    }

    public String asString() {
        return value.toString();
    }

    public boolean isComplexProperty() {
        return false;
    }

    public Propertyset asComplexProperty() {
        return PropertyvalueNil.getNil().asComplexProperty();
    }

    public boolean isReference() {
        return false;
    }

    public Propertyset asReference() {
        return PropertyvalueNil.getNil().asReference();
    }

    public boolean isList() {
        return false;
    }

    public PropertyvalueList asList() {
        return PropertyvalueNil.getNil().asList();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IdPropertyvalue)) {
            return false;
        }

        IdPropertyvalue objVal = (IdPropertyvalue) obj;
        return (asId().equals(objVal.asId()));
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
