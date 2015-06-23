package no.priv.bang.modeling.modelstore;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import no.priv.bang.modeling.modelstore.impl.Propertyvalues;

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

    public Propertyvalue getProperty(String propertyname) {
        return Propertyvalues.getNil();
    }

    public void setProperty(String propertyname, Propertyvalue property) {
        // No-op
    }

    public boolean hasAspect() {
        return false;
    }

    public PropertyvalueList getAspects() {
        return Propertyvalues.getNil().asList();
    }

    public void addAspect(Propertyset aspect) {
        // No-op
    }

    public boolean hasId() {
        return false;
    }

    public UUID getId() {
        return Propertyvalues.getNil().asId();
    }

    public Boolean getBooleanProperty(String propertyName) {
        return Propertyvalues.getNil().asBoolean();
    }

    public void setBooleanProperty(String propertyName, Boolean boolValue) {
        // No-op
    }

    public Long getLongProperty(String propertyName) {
        return Propertyvalues.getNil().asLong();
    }

    public void setLongProperty(String propertyName, Long intValue) {
        // No-op
    }

    public Double getDoubleProperty(String propertyName) {
        return Propertyvalues.getNil().asDouble();
    }

    public void setDoubleProperty(String propertyName, Double doubleValue) {
        // No-op
    }

    public String getStringProperty(String propertyName) {
        return Propertyvalues.getNil().asString();
    }

    public void setStringProperty(String propertyName, String stringValue) {
        // No-op
    }

    public Propertyset getComplexProperty(String propertyName) {
        return Propertyvalues.getNil().asComplexProperty();
    }

    public void setComplexProperty(String propertyName, Propertyset complexProperty) {
        // No-op
    }

    public Propertyset getReferenceProperty(String propertyName) {
        return Propertyvalues.getNil().asReference();
    }

    public void setReferenceProperty(String string, Propertyset referencedObject) {
        // No-op
    }

    public PropertyvalueList getListProperty(String propertyName) {
        return Propertyvalues.getNil().asList();
    }

    public void setListProperty(String propertyName, PropertyvalueList listValue) {
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
