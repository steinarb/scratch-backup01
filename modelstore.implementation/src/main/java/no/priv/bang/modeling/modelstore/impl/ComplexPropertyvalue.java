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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ComplexPropertyvalue)) {
            return false;
        }

        ComplexPropertyvalue objVal = (ComplexPropertyvalue) obj;
        return (asComplexProperty().equals(objVal.asComplexProperty()));
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
