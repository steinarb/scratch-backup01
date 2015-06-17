package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

/**
 * Wraps a {@link Double} value in a {@link Propertyset}.
 *
 * Will return well defined values if asked to return a different
 * property type.
 *
 * @author Steinar Bang
 *
 */
class DoublePropertyvalue extends PropertyvalueBase {
    private Double value;

    public DoublePropertyvalue(Double value) {
    	if (null == value) {
            this.value = PropertyvalueNil.getNil().asDouble();
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
        return PropertysetNil.getNil();
    }

    public Propertyset asReference() {
        return PropertysetNil.getNil();
    }

    public PropertyvalueList asList() {
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

        DoublePropertyvalue other = (DoublePropertyvalue) obj;
        return value.equals(other.value);
    }

    @Override
    public String toString() {
        return "DoublePropertyvalue [value=" + value + "]";
    }

}
