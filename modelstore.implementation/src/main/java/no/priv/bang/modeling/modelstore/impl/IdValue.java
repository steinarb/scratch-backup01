package no.priv.bang.modeling.modelstore.impl;

import java.util.UUID;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;

class IdPropertyvalue implements Value {

    private UUID value;

    public IdPropertyvalue(UUID value) {
    	if (value != null) {
            this.value = value;
    	} else {
            this.value = NilValue.getNil().asId();
    	}
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
        return NilValue.getNil().asBoolean();
    }

    public boolean isLong() {
        return false;
    }

    public Long asLong() {
        return NilValue.getNil().asLong();
    }

    public boolean isDouble() {
        return false;
    }

    public Double asDouble() {
        return NilValue.getNil().asDouble();
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
        return NilValue.getNil().asComplexProperty();
    }

    public boolean isReference() {
        return false;
    }

    public Propertyset asReference() {
        return NilValue.getNil().asReference();
    }

    public boolean isList() {
        return false;
    }

    public ValueList asList() {
        return NilValue.getNil().asList();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value.hashCode();
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

        IdPropertyvalue other = (IdPropertyvalue) obj;
        return value.equals(other.value);
    }

    @Override
    public String toString() {
        return "IdPropertyvalue [value=" + value + "]";
    }

}
