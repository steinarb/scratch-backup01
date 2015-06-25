package no.priv.bang.modeling.modelstore.impl;

import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.ValueList;

/**
 * Wraps a {@link Double} value in a {@link Propertyset}.
 *
 * Will return well defined values if asked to return a different
 * property type.
 *
 * @author Steinar Bang
 *
 */
class DoublePropertyvalue extends ValueBase {
    private Double value;

    /**
     * Create new instances of {@link DoublePropertyvalue}.
     *
     * @param value the value to wrap
     * @deprecated use {@link Values#toDoubleValue(Double)} instead
     */
    public DoublePropertyvalue(Double value) {
    	if (null == value) {
            this.value = NilValue.getNil().asDouble();
    	} else {
            this.value = value;
    	}
    }

    public boolean isDouble() {
        return true;
    }

    public Boolean asBoolean() {
        double floatValue = value.doubleValue();
        return floatValue != 0.0;
    }

    public Long asLong() {
        double floatValue = value.doubleValue();
        return Math.round(floatValue);
    }

    public Double asDouble() {
        return value;
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

        DoublePropertyvalue other = (DoublePropertyvalue) obj;
        return value.equals(other.value);
    }

    @Override
    public String toString() {
        return "DoublePropertyvalue [value=" + value + "]";
    }

}
