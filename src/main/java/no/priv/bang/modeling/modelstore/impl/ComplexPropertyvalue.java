package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;

public class ComplexPropertyvalue extends PropertysetPropertyvalueBase {

    public ComplexPropertyvalue(Propertyset value) {
    	super(value);
    }

    @Override
    public boolean isComplexProperty() {
        return true;
    }

    public Propertyset asComplexProperty() {
        return value;
    }

    public Propertyset asReference() {
        return PropertysetNil.getNil();
    }

}
