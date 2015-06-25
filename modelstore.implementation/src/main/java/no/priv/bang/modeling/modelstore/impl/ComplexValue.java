package no.priv.bang.modeling.modelstore.impl;

import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Propertyset;

class ComplexPropertyvalue extends PropertysetValueBase {

    /**
     * Create a new instance of {@link ComplexPropertyvalue}.
     *
     * @param value the value to wrap
     * @deprecated use {@link Values#toComplexValue(Propertyset)} instead
     */
    public ComplexPropertyvalue(Propertyset value) {
    	super(value);
    }

    @Override
    public boolean isComplexProperty() {
        return true;
    }

    public Propertyset asComplexProperty() {
        return value;
    }

    public Propertyset asReference() {
        return getNilPropertyset();
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

        PropertysetValueBase other = (PropertysetValueBase) obj;
        return value.equals(other.value);
    }

    @Override
    public String toString() {
        return "ComplexPropertyvalue [value=" + value + "]";
    }

}
