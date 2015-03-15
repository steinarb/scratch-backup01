package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;

/**
 * Wraps a {@link Long} value in a {@link Propertyset}.
 *
 * Will return well defined values if asked to return a different
 * property type.
 *
 * @author Steinar Bang
 *
 */
class LongPropertyvalue extends PropertyvalueBase {
    private Long value;

    public LongPropertyvalue(Long value) {
        this.value = value;
    }

    public boolean isLong() {
        return true;
    }

    public Boolean asBoolean() {
        long intValue = value.longValue();
        return (intValue != 0);
    }

    public Long asLong() {
        return value;
    }

    public Double asDouble() {
        long intValue = value.longValue();
        return (double) intValue;
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
