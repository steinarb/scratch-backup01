package no.priv.bang.modeling.modelstore.impl;

import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.ValueList;

/**
 * Wraps a {@link Boolean} value in a {@link Propertyset}.
 *
 * Will return well defined values if asked to return a different
 * property type.
 *
 * @author Steinar Bang
 *
 */
class BooleanPropertyvalue extends ValueBase {

    private Boolean value;

    /**
     * Create new instance of {@link BooleanPropertyvalue}
     *
     * @param value the value to wrap.
     * @deprecated Use {@link Values#toBooleanValue(Boolean)} instead
     */
    BooleanPropertyvalue(Boolean value) {
    	if (null == value) {
            this.value = PropertyvalueNil.getNil().asBoolean();
    	} else {
            this.value = value;
    	}
    }

    public boolean isBoolean() {
        return true;
    }

    public Boolean asBoolean() {
        return value;
    }

    public Long asLong() {
        boolean bool = value.booleanValue();
        return Long.valueOf(bool ? 1 : 0);
    }

    public Double asDouble() {
        boolean bool = value.booleanValue();
        return bool ? 1.0 : 0.0;
    }

    public String asString() {
        return value.toString();
    }

    public Propertyset asComplexProperty() {
        return getNilPropertyset();
    }

    public Propertyset asReference() {
        return getNilPropertyset();
    }

    public boolean isList() {
        return false;
    }

    public ValueList asList() {
        return PropertyvalueNil.getNil().asList();
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

        BooleanPropertyvalue other = (BooleanPropertyvalue) obj;
        return value.equals(other.value);
    }

    @Override
    public String toString() {
        return "BooleanPropertyvalue [value=" + value + "]";
    }

}
