package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

/**
 * Wraps a {@link Long} value in a {@link Propertyset}.
 *
 * Will return well defined values if asked to return a different
 * property type.
 *
 * @author Steinar Bang
 *
 */
public class LongPropertyvalue extends PropertyvalueBase {
    private Long value;

    public LongPropertyvalue(Long value) {
    	if (null == value) {
            this.value = PropertyvalueNil.getNil().asLong();
    	} else {
            this.value = value;
    	}
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

    public Propertyset asReference() {
        return PropertysetNil.getNil();
    }

    public PropertyvalueList asList() {
        return PropertyvalueNil.getNil().asList();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LongPropertyvalue)) {
            return false;
        }

        LongPropertyvalue objVal = (LongPropertyvalue) obj;
        return (asLong().longValue() == objVal.asLong().longValue());
    }

    @Override
    public int hashCode() {
        return (value != null) ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return (value != null) ? value.toString() : "null";
    }

}
