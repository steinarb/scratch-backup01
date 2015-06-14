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
public class DoublePropertyvalue extends PropertyvalueBase {
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

}
