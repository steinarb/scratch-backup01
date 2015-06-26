package no.priv.bang.modeling.modelstore.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;

/**
 * Implementation of {@link Propertyset} backed by a {@link Map}.
 *
 * @author Steinar Bang
 *
 */
public class PropertysetImpl implements Propertyset {
    final private String idKey = "id";
    final private String aspectsKey = "aspects";
    Map<String, Value> properties = new HashMap<String, Value>();

    public PropertysetImpl(UUID id) {
    	properties.put(idKey, new IdValue(id));
    }

    public PropertysetImpl() { }

    public Collection<String> getPropertynames() {
        return properties.keySet();
    }

    public Value getProperty(String propertyname) {
        if (properties.containsKey(propertyname)) {
            return properties.get(propertyname);
        }

        return getNil();
    }

    public void setProperty(String propertyname, Value property) {
        properties.put(propertyname, property);
    }

    public boolean isNil() {
        return false;
    }

    public boolean hasAspect() {
    	Value rawValue = properties.get(aspectsKey);
    	if (null != rawValue) {
            return !rawValue.asList().isEmpty();
    	}

    	return false;
    }

    public ValueList getAspects() {
    	Value rawValue = properties.get(aspectsKey);
    	if (null != rawValue) {
            return rawValue.asList();
    	}

    	return getNil().asList();
    }

    public void addAspect(Propertyset aspect) {
    	Value rawValue = properties.get(aspectsKey);
    	if (null != rawValue) {
            ValueList aspectList = rawValue.asList();
            if (!aspectContainedInList(aspectList, aspect)) {
                aspectList.add(Values.toReferenceValue(aspect));
            }
    	} else {
            ValueList aspectList = newList();
            aspectList.add(Values.toReferenceValue(aspect));
            properties.put(aspectsKey, Values.toListValue(aspectList));
    	}
    }

    private boolean aspectContainedInList(ValueList aspectList, Propertyset aspect) {
        for (Value value : aspectList) {
            if (value.asReference().equals(aspect)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasId() {
        return properties.containsKey(idKey);
    }

    public UUID getId() {
    	if (hasId()) {
            return properties.get(idKey).asId();
    	}

    	return getNil().asId();
    }

    public Boolean getBooleanProperty(String propertyname) {
        Value rawValue = properties.get(propertyname);
        if (null != rawValue) {
            return rawValue.asBoolean();
        }

        return getNilPropertyset().getBooleanProperty(propertyname);
    }

    public void setBooleanProperty(String propertyname, Boolean boolValue) {
    	if (!idKey.equals(propertyname)) {
            properties.put(propertyname, toBooleanValue(boolValue));
    	}
    }

    public void setBooleanProperty(String propertyname, boolean boolValue) {
    	if (!idKey.equals(propertyname)) {
            properties.put(propertyname, toBooleanValue(boolValue));
    	}
    }

    public Long getLongProperty(String propertyname) {
        Value rawValue = properties.get(propertyname);
        if (null != rawValue) {
            return rawValue.asLong();
        }

        return getNilPropertyset().getLongProperty(propertyname);
    }

    public void setLongProperty(String propertyname, Long intValue) {
    	if (!idKey.equals(propertyname)) {
            properties.put(propertyname, toLongValue(intValue));
    	}
    }

    public void setLongProperty(String propertyname, long intvalue) {
    	if (!idKey.equals(propertyname)) {
            properties.put(propertyname, toLongValue(intvalue));
    	}
    }

    public Double getDoubleProperty(String propertyname) {
        Value rawValue = properties.get(propertyname);
        if (null != rawValue) {
            return rawValue.asDouble();
        }

        return getNilPropertyset().getDoubleProperty(propertyname);
    }

    public void setDoubleProperty(String propertyname, Double doubleValue) {
    	if (!idKey.equals(propertyname)) {
            properties.put(propertyname, toDoubleValue(doubleValue));
    	}
    }

    public void setDoubleProperty(String propertyname, double doubleValue) {
    	if (!idKey.equals(propertyname)) {
            properties.put(propertyname, toDoubleValue(doubleValue));
    	}
    }

    public String getStringProperty(String propertyname) {
        Value rawValue = properties.get(propertyname);
        if (null != rawValue) {
            return rawValue.asString();
        }

        return getNilPropertyset().getStringProperty(propertyname);
    }

    public void setStringProperty(String propertyname, String stringValue) {
    	if (!idKey.equals(propertyname)) {
            properties.put(propertyname, toStringValue(stringValue));
    	}
    }

    public Propertyset getComplexProperty(String propertyname) {
    	Value rawValue = properties.get(propertyname);
    	if (null != rawValue) {
            return rawValue.asComplexProperty();
    	}

        return getNilPropertyset();
    }

    public void setComplexProperty(String propertyname, Propertyset complexProperty) {
    	if (!idKey.equals(propertyname)) {
            properties.put(propertyname, toComplexValue(complexProperty));
    	}
    }

    public Propertyset getReferenceProperty(String propertyname) {
    	Value rawValue = properties.get(propertyname);
    	if (null != rawValue) {
            return rawValue.asReference();
    	}

        return getNilPropertyset();
    }

    public void setReferenceProperty(String propertyname, Propertyset referencedObject) {
    	if (!idKey.equals(propertyname)) {
            properties.put(propertyname, toReferenceValue(referencedObject));
    	}
    }

    public ValueList getListProperty(String propertyname) {
    	Value rawValue = properties.get(propertyname);
    	if (null != rawValue) {
            return rawValue.asList();
    	}

        return getNilPropertyset().getListProperty(propertyname);
    }

    public void setListProperty(String propertyname, ValueList listValue) {
    	if (!idKey.equals(propertyname)) {
            properties.put(propertyname, toListValue(listValue));
    	}
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
            + ((properties == null) ? 0 : properties.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj instanceof PropertysetNil) {
            // A nil propertyset is equal to an empty propertyset
            if (properties == null || properties.isEmpty()) {
                return true;
            }
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        PropertysetImpl other = (PropertysetImpl) obj;
        if (properties == null) {
            if (other.properties != null) {
                return false;
            }
        } else if (!properties.equals(other.properties)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "PropertysetImpl [properties=" + properties + "]";
    }

}
