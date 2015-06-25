package no.priv.bang.modeling.modelstore.impl;

import java.util.UUID;

import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;
import no.priv.bang.modeling.modelstore.impl.EmptyValueList;

/**
 * A nil property value object.  This is a singleton that can be
 * accessed using a static method.
 *
 * @author Steinar Bang
 *
 */
final class NilValue implements Value {

    private static final Value singleton = new NilValue();
    private final UUID nilId = new UUID(0, 0);
    private final Boolean nullBooleanValue = Boolean.valueOf(false);
    private final Long nullLongValue = Long.valueOf(0);
    private final Double nullDoubleValue = Double.valueOf(0);
    private final String emptyStringValue = "";
    private final ValueList emptyPropertyvalueList = new EmptyValueList();

    static Value getNil() {
        return singleton;
    }

    private NilValue() { }

    public boolean isId() {
        return false;
    }

    public UUID asId() {
        return nilId;
    }

    public boolean isBoolean() {
        return false;
    }

    public Boolean asBoolean() {
        return nullBooleanValue;
    }

    public boolean isLong() {
        return false;
    }

    public Long asLong() {
        return nullLongValue;
    }

    public boolean isDouble() {
        return false;
    }

    public Double asDouble() {
        return nullDoubleValue;
    }

    public boolean isString() {
        return false;
    }

    public String asString() {
        return emptyStringValue;
    }

    public boolean isComplexProperty() {
        return false;
    }

    public Propertyset asComplexProperty() {
        return getNilPropertyset();
    }

    public boolean isReference() {
        return false;
    }

    public Propertyset asReference() {
        return getNilPropertyset();
    }

    public boolean isList() {
        return false;
    }

    public ValueList asList() {
        return emptyPropertyvalueList;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NilValue;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "PropertyvalueNil []";
    }

}
