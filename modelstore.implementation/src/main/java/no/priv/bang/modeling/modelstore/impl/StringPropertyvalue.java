package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;
import no.priv.bang.modeling.modelstore.PropertyvalueList;

/**
 * Wraps a {@link String} value in a {@link Propertyset}.
 *
 * Will return well defined values if asked to return a different
 * property type.
 *
 * @author Steinar Bang
 *
 */
class StringPropertyvalue extends PropertyvalueBase {
    private String value;

    /**
     * Create a new instance of {@link StringPropertyvalue}.
     *
     * @param value the value to wrap
     * @deprecated use {@link Propertyvalues#toStringValue(String)} instead
     */
    public StringPropertyvalue(String value) {
    	if (null == value) {
            this.value = PropertyvalueNil.getNil().asString();
    	} else {
            this.value = value;
    	}
    }

    @Override
    public boolean isString() {
        return true;
    }

    public Boolean asBoolean() {
        return Boolean.valueOf(value);
    }

    public Long asLong() {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e1) {
            try {
                double doubleValue = Double.parseDouble(value);
                return Math.round(doubleValue);
            } catch (NumberFormatException e2) {
                return PropertysetNil.getNil().getLongProperty("dummy");
            }
        }
    }

    public Double asDouble() {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            return PropertysetNil.getNil().getDoubleProperty("dummy");
        }
    }

    public String asString() {
        return value;
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

        StringPropertyvalue other = (StringPropertyvalue) obj;
        return value.equals(other.value);
    }

    @Override
    public String toString() {
        return "StringPropertyvalue [value=" + value + "]";
    }

}
