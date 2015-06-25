package no.priv.bang.modeling.modelstore.impl;

import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Propertyset;

/**
 * A property value that references a {@link Propertyset} (a "pointer"
 * value).  This type is essential for building graphs.
 *
 * @author Steinar Bang
 *
 */
class ReferencePropertyvalue extends PropertysetValueBase {

    /**
     * Create a new instance of {@link ReferencePropertyvalue}.
     *
     * @param value the value to wrap
     * @deprecated use {@link Values#toReferenceValue(Propertyset)} instead
     */
    public ReferencePropertyvalue(Propertyset value) {
        super(value);
    }

    @Override
    public boolean isReference() {
        return true;
    }

    public Propertyset asComplexProperty() {
        return getNilPropertyset();
    }

    public Propertyset asReference() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value.getId().hashCode();
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

        return value.getId().equals(other.value.getId());
    }

    @Override
    public String toString() {
        return "ReferencePropertyvalue [value=" + value + "]";
    }

}
