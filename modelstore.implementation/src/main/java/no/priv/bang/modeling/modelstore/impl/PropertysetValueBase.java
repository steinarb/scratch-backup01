package no.priv.bang.modeling.modelstore.impl;

import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.ValueList;

public abstract class PropertysetValueBase extends ValueBase {

    protected Propertyset value;

    public PropertysetValueBase(Propertyset value) {
        if (null == value) {
            this.value = getNilPropertyset();
        } else {
            this.value = value;
        }
    }

    public Boolean asBoolean() {
        return NilValue.getNil().asBoolean();
    }

    public Long asLong() {
        return NilValue.getNil().asLong();
    }

    public Double asDouble() {
        return NilValue.getNil().asDouble();
    }

    public String asString() {
        return NilValue.getNil().asString();
    }

    public ValueList asList() {
        return NilValue.getNil().asList();
    }

}
