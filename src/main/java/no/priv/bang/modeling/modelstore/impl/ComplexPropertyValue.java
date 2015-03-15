package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;

public class ComplexPropertyValue extends PropertyvalueBase {

    private Propertyset value;

    public ComplexPropertyValue(Propertyset value) {
        if (null == value) {
            this.value = PropertysetNil.getNil();
        } else {
            this.value = value;
        }
    }

    @Override
    public boolean isComplexProperty() {
        return true;
    }

    public Boolean asBoolean() {
        return PropertysetNil.getNil().getBooleanProperty("dummy");
    }

    public Long asLong() {
        return PropertysetNil.getNil().getLongProperty("dummy");
    }

    public Double asDouble() {
        return PropertysetNil.getNil().getDoubleProperty("dummy");
    }

    public String asString() {
        return PropertysetNil.getNil().getStringProperty("dummy");
    }

    public Propertyset asComplexProperty() {
        return value;
    }

    public Propertyset asReferenceProperty() {
        return PropertysetNil.getNil();
    }

}
