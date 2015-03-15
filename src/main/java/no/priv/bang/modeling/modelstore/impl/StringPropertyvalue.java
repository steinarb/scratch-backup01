package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;

/**
 * Wraps a {@link String} value in a {@link Propertyset}.
 *
 * Will return well defined values if asked to return a different
 * property type.
 *
 * @author Steinar Bang
 *
 */
public class StringPropertyvalue extends PropertyvalueBase {
    private String value;

    public StringPropertyvalue(String value) {
    	if (null == value) {
            this.value = PropertysetNil.getNil().getStringProperty("dummy");
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

    public Propertyset asReferenceProperty() {
        return PropertysetNil.getNil();
    }

}
