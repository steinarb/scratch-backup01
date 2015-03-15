package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;

public abstract class PropertysetPropertyValueBase extends PropertyvalueBase {

    protected Propertyset value;

    public PropertysetPropertyValueBase(Propertyset value) {
        if (null == value) {
            this.value = PropertysetNil.getNil();
        } else {
            this.value = value;
        }
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

}
