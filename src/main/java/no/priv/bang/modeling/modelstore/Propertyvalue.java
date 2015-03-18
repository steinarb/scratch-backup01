package no.priv.bang.modeling.modelstore;


/**
 * Defines a wrapper for a property in a {@link PropertySet}.
 *
 * @author Steinar Bang
 *
 */
public interface Propertyvalue {
    boolean isBoolean();
    Boolean asBoolean();
    boolean isLong();
    Long asLong();
    boolean isDouble();
    Double asDouble();
    boolean isString();
    String asString();
    boolean isComplexProperty();
    Propertyset asComplexProperty();
    boolean isReference();
    Propertyset asReference();
    boolean isList();
    PropertyvalueList asList();
}
