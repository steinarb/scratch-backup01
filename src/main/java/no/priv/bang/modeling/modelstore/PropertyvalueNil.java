package no.priv.bang.modeling.modelstore;

import no.priv.bang.modeling.modelstore.impl.EmptyPropertyvalueList;

/**
 * A nil property value object.  This is a singleton that can be
 * accessed using a static method.
 *
 * @author Steinar Bang
 *
 */
public final class PropertyvalueNil implements Propertyvalue {

    private static final Propertyvalue singleton = new PropertyvalueNil();
    private final Boolean nullBooleanValue = Boolean.valueOf(false);
    private final Long nullLongValue = Long.valueOf(0);
    private final Double nullDoubleValue = Double.valueOf(0);
    private final String emptyStringValue = "";
    private final PropertyvalueList emptyPropertyvalueList = new EmptyPropertyvalueList();

    public static Propertyvalue getNil() {
        return singleton;
    }

    private PropertyvalueNil() { }

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
        return PropertysetNil.getNil();
    }

    public boolean isReference() {
        return false;
    }

    public Propertyset asReference() {
        return PropertysetNil.getNil();
    }

    public boolean isList() {
        return false;
    }

    public PropertyvalueList asList() {
        return emptyPropertyvalueList;
    }

}
