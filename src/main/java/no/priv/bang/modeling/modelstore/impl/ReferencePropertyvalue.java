package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;

public class ReferencePropertyValue extends PropertysetPropertyValueBase {

    public ReferencePropertyValue(Propertyset value) {
        super(value);
    }

    @Override
    public boolean isReference() {
        return true;
    }

    public Propertyset asComplexProperty() {
        return PropertysetNil.getNil();
    }

    public Propertyset asReference() {
        return value;
    }

}
