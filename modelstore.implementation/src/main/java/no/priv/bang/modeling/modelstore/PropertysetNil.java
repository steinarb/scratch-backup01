package no.priv.bang.modeling.modelstore;

import java.util.UUID;

/**
 * Singleton implementation of {@link Propertyset} intended to be used
 * instead of null, for undefined property values, or values that
 * cannot be cast to a {@link Propertyset}
 *
 * @author Steinar Bang
 *
 */
public final class PropertysetNil implements Propertyset {

    private static Propertyset singleton;

    public static Propertyset getNil() {
        if (null == singleton) {
            singleton = new PropertysetNil();
        }

        return singleton;
    }

    private PropertysetNil() {}

    public boolean isNil() {
        return true;
    }

    public boolean hasAspect() {
        return false;
    }

    public PropertyvalueList getAspects() {
        return PropertyvalueNil.getNil().asList();
    }

    public void addAspect(Propertyset aspect) {
        // No-op
    }

    public boolean hasId() {
        return false;
    }

    public UUID getId() {
        return PropertyvalueNil.getNil().asId();
    }

    public Boolean getBooleanProperty(String propertyName) {
        return PropertyvalueNil.getNil().asBoolean();
    }

    public void setBooleanProperty(String propertyName, Boolean boolValue) {
        // No-op
    }

    public Long getLongProperty(String propertyName) {
        return PropertyvalueNil.getNil().asLong();
    }

    public void setLongProperty(String propertyName, Long intValue) {
        // No-op
    }

    public Double getDoubleProperty(String propertyName) {
        return PropertyvalueNil.getNil().asDouble();
    }

    public void setDoubleProperty(String propertyName, Double doubleValue) {
        // No-op
    }

    public String getStringProperty(String propertyName) {
        return PropertyvalueNil.getNil().asString();
    }

    public void setStringProperty(String propertyName, String stringValue) {
        // No-op
    }

    public Propertyset getComplexProperty(String propertyName) {
        return PropertyvalueNil.getNil().asComplexProperty();
    }

    public void setComplexProperty(String propertyName, Propertyset complexProperty) {
        // No-op
    }

    public Propertyset getReferenceProperty(String propertyName) {
        return PropertyvalueNil.getNil().asReference();
    }

    public void setReferenceProperty(String string, Propertyset referencedObject) {
        // No-op
    }

    public PropertyvalueList getListProperty(String propertyName) {
        return PropertyvalueNil.getNil().asList();
    }

    public void setListProperty(String propertyName, PropertyvalueList listValue) {
        // No-op
    }

}
