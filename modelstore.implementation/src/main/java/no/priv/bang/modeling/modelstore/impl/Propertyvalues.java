package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;

/**
 * A class with static methods for wrapping Java property values
 * in {@link Propertyvalue} objects.
 *
 * @author Steinar Bang
 *
 */
@SuppressWarnings("deprecation")
public class Propertyvalues {

    /**
     * Get the singleton {@link PropertyvalueNil} object.
     *
     * @return a reference to {@link PropertyvalueNil}.
     */
    static public Propertyvalue getNil() {
        return PropertyvalueNil.getNil();
    }

    /**
     * Create new instances of {@link BooleanPropertyvalue}
     *
     * @param boolValue the value to wrap
     * @return a {@link BooleanPropertyvalue} instance
     */
    static public Propertyvalue toBooleanValue(Boolean boolValue) {
        return new BooleanPropertyvalue(boolValue);
    }

    /**
     * Create new instances of {@link BooleanPropertyvalue}
     *
     * @param boolValue the value to wrap
     * @return a {@link BooleanPropertyvalue} instance
     */
    static public Propertyvalue toBooleanValue(boolean boolValue) {
        return toBooleanValue(Boolean.valueOf(boolValue));
    }

    /**
     * Create new instances of {@link LongPropertyvalue}
     *
     * @param intValue the value to wrap
     * @return a {@link LongPropertyvalue} instance
     */
    static public Propertyvalue toLongValue(Long intValue) {
        return new LongPropertyvalue(intValue);
    }

    /**
     * Create new instances of {@link LongPropertyvalue}
     *
     * @param intValue the value to wrap
     * @return a {@link LongPropertyvalue} instance
     */
    static public Propertyvalue toLongValue(long intValue) {
        return toLongValue(Long.valueOf(intValue));
    }

    /**
     * Create new instances of {@link DoublePropertyvalue}
     *
     * @param doubleValue the value to wrap
     * @return a {@link DoublePropertyvalue} instance
     */
    static public Propertyvalue toDoubleValue(Double doubleValue) {
        return new DoublePropertyvalue(doubleValue);
    }

    /**
     * Create new instances of {@link DoublePropertyvalue}
     *
     * @param doubleValue the value to wrap
     * @return a {@link DoublePropertyvalue} instance
     */
    static public Propertyvalue toDoubleValue(double doubleValue) {
        return toDoubleValue(Double.valueOf(doubleValue));
    }

    /**
     * Create new instances of {@link StringPropertyvalue}
     *
     * @param stringValue the value to wrap
     * @return a {@link StringPropertyvalue} instance
     */
    static public Propertyvalue toStringValue(String stringValue) {
        return new StringPropertyvalue(stringValue);
    }

    /**
     * Create new instances of {@link ComplexPropertyvalue}
     *
     * @param complexValue the value to wrap
     * @return a {@link ComplexPropertyvalue} instance
     */
    static public Propertyvalue toComplexValue(Propertyset complexValue) {
        return new ComplexPropertyvalue(complexValue);
    }

    /**
     * Create new instances of {@link ReferencePropertyvalue}
     *
     * @param referencedValue the value to wrap
     * @return a {@link ReferencePropertyvalue} instance
     */
    static public Propertyvalue toReferenceValue(Propertyset referencedValue) {
        return new ReferencePropertyvalue(referencedValue);
    }

    /**
     * Create new instances of {@link ListPropertyvalue}
     *
     * @param listValue the value to wrap
     * @return a {@link ListPropertyvalue} instance
     */
    static Propertyvalue toListValue(PropertyvalueList listValue) {
        return new ListPropertyvalue(listValue);
    }

    /**
     * Create new instances of {@link PropertyvalueArrayList}.
     *
     * @return a new empty instance of {@link PropertyvalueArrayList}.
     */
    static public PropertyvalueList newList() {
        return new PropertyvalueArrayList();
    }

}
