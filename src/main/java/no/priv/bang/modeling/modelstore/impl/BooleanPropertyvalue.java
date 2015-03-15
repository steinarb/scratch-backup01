package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;

/**
 * Wraps a {@link Boolean} value in a {@link Propertyset}.
 *
 * Will return well defined values if asked to return a different
 * property type.
 *
 * @author Steinar Bang
 *
 */
class BooleanPropertyvalue extends PropertyvalueBase {

    private Boolean value;

    BooleanPropertyvalue(Boolean value) {
    	if (null == value) {
            this.value = PropertysetNil.getNil().getBooleanProperty("dummy");
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

    public Propertyset asReferenceProperty() {
        return PropertysetNil.getNil();
    }

}
