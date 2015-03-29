package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.PropertyvalueNil;

public abstract class PropertysetPropertyvalueBase extends PropertyvalueBase {

    protected Propertyset value;

    public PropertysetPropertyvalueBase(Propertyset value) {
        if (null == value) {
            this.value = PropertysetNil.getNil();
        } else {
            this.value = value;
        }
    }

    public Boolean asBoolean() {
        return PropertyvalueNil.getNil().asBoolean();
    }

    public Long asLong() {
        return PropertyvalueNil.getNil().asLong();
    }

    public Double asDouble() {
        return PropertyvalueNil.getNil().asDouble();
    }

    public String asString() {
        return PropertyvalueNil.getNil().asString();
    }

    public PropertyvalueList asList() {
        return PropertyvalueNil.getNil().asList();
    }

}
