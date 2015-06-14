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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DoublePropertyvalue)) {
            return false;
        }

        DoublePropertyvalue objVal = (DoublePropertyvalue) obj;
        return (asDouble().doubleValue() == objVal.asDouble().doubleValue());
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
