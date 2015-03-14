package no.priv.bang.modeling.modelstore;

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

    private final Long nullLongValue = Long.valueOf(0);
    private final Double nullDoubleValue = Double.valueOf(0);
    private final String emptyStringValue = "";

    private PropertysetNil() {}

    public boolean isNil() {
        return true;
    }

    public Long getLongProperty(String propertyName) {
        return nullLongValue;
    }
    public void setLongProperty(String propertyName, Long intValue) {
        // No-op
    }

    public Double getDoubleProperty(String propertyName) {
        return nullDoubleValue;
    }

    public void setDoubleProperty(String propertyName, Double doubleValue) {
        // No-op
    }

    public String getStringProperty(String propertyName) {
        return emptyStringValue;
    }

    public void setStringProperty(String propertyName, String stringValue) {
        // No-op
    }

    public Propertyset getComplexProperty(String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    public Propertyset getReferenceProperty(String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

}
