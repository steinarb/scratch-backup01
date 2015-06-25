package no.priv.bang.modeling.modelstore;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import no.priv.bang.modeling.modelstore.impl.Values;

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

    public Collection<String> getPropertynames() {
        return Collections.emptyList();
    }

    public Value getProperty(String propertyname) {
        return Values.getNil();
    }

    public void setProperty(String propertyname, Value property) {
        // No-op
    }

    public boolean hasAspect() {
        return false;
    }

    public ValueList getAspects() {
        return Values.getNil().asList();
    }

    public void addAspect(Propertyset aspect) {
        // No-op
    }

    public boolean hasId() {
        return false;
    }

    public UUID getId() {
        return Values.getNil().asId();
    }

    public Boolean getBooleanProperty(String propertyName) {
        return Values.getNil().asBoolean();
    }

    public void setBooleanProperty(String propertyName, Boolean boolValue) {
        // No-op
    }

    public Long getLongProperty(String propertyName) {
        return Values.getNil().asLong();
    }

    public void setLongProperty(String propertyName, Long intValue) {
        // No-op
    }

    public Double getDoubleProperty(String propertyName) {
        return Values.getNil().asDouble();
    }

    public void setDoubleProperty(String propertyName, Double doubleValue) {
        // No-op
    }

    public String getStringProperty(String propertyName) {
        return Values.getNil().asString();
    }

    public void setStringProperty(String propertyName, String stringValue) {
        // No-op
    }

    public Propertyset getComplexProperty(String propertyName) {
        return Values.getNil().asComplexProperty();
    }

    public void setComplexProperty(String propertyName, Propertyset complexProperty) {
        // No-op
    }

    public Propertyset getReferenceProperty(String propertyName) {
        return Values.getNil().asReference();
    }

    public void setReferenceProperty(String string, Propertyset referencedObject) {
        // No-op
    }

    public ValueList getListProperty(String propertyName) {
        return Values.getNil().asList();
    }

    public void setListProperty(String propertyName, ValueList listValue) {
        // No-op
    }

    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
            return true;
    	}

    	if (obj == null) {
            return false;
    	}

    	if (obj instanceof Propertyset) {
            Propertyset other = (Propertyset) obj;
            if (other.getPropertynames().isEmpty()) {
                return true;
            }
    	}

    	return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "PropertysetNil []";
    }

}
