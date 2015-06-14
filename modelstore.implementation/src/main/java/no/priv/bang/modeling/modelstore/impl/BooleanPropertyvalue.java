package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

/**
 * Wraps a {@link Boolean} value in a {@link Propertyset}.
 *
 * Will return well defined values if asked to return a different
 * property type.
 *
 * @author Steinar Bang
 *
 */
public class BooleanPropertyvalue extends PropertyvalueBase {

    private Boolean value;

    public BooleanPropertyvalue(Boolean value) {
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
        return PropertysetNil.getNil();
    }

    public Propertyset asReference() {
        return PropertysetNil.getNil();
    }

    public boolean isList() {
        return false;
    }

    public PropertyvalueList asList() {
        return PropertyvalueNil.getNil().asList();
    }

    @Override
    public String toString() {
        return (value != null) ? value.toString() : "null";
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

        BooleanPropertyvalue other = (BooleanPropertyvalue) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }

        return true;
    }

}
