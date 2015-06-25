package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;

/**
 * A class with static methods for wrapping Java property values
 * in {@link Value} objects.
 *
 * @author Steinar Bang
 *
 */
@SuppressWarnings("deprecation")
public class Values {

    /**
     * Get the singleton {@link PropertysetNil} object.
     *
     * @return a reference to {@link PropertysetNil}.
     */
    static public Propertyset getNilPropertyset() {
        return PropertysetNil.getNil();
    }

    /**
     * Get the singleton {@link PropertyvalueNil} object.
     *
     * @return a reference to {@link PropertyvalueNil}.
     */
    static public Value getNil() {
        return PropertyvalueNil.getNil();
    }

    /**
     * Create new instances of {@link BooleanPropertyvalue}
     *
     * @param boolValue the value to wrap
     * @return a {@link BooleanPropertyvalue} instance
     */
    static public Value toBooleanValue(Boolean boolValue) {
        return new BooleanPropertyvalue(boolValue);
    }

    /**
     * Create new instances of {@link BooleanPropertyvalue}
     *
     * @param boolValue the value to wrap
     * @return a {@link BooleanPropertyvalue} instance
     */
    static public Value toBooleanValue(boolean boolValue) {
        return toBooleanValue(Boolean.valueOf(boolValue));
    }

    /**
     * Create new instances of {@link LongPropertyvalue}
     *
     * @param intValue the value to wrap
     * @return a {@link LongPropertyvalue} instance
     */
    static public Value toLongValue(Long intValue) {
        return new LongPropertyvalue(intValue);
    }

    /**
     * Create new instances of {@link LongPropertyvalue}
     *
     * @param intValue the value to wrap
     * @return a {@link LongPropertyvalue} instance
     */
    static public Value toLongValue(long intValue) {
        return toLongValue(Long.valueOf(intValue));
    }

    /**
     * Create new instances of {@link DoublePropertyvalue}
     *
     * @param doubleValue the value to wrap
     * @return a {@link DoublePropertyvalue} instance
     */
    static public Value toDoubleValue(Double doubleValue) {
        return new DoublePropertyvalue(doubleValue);
    }

    /**
     * Create new instances of {@link DoublePropertyvalue}
     *
     * @param doubleValue the value to wrap
     * @return a {@link DoublePropertyvalue} instance
     */
    static public Value toDoubleValue(double doubleValue) {
        return toDoubleValue(Double.valueOf(doubleValue));
    }

    /**
     * Create new instances of {@link StringPropertyvalue}
     *
     * @param stringValue the value to wrap
     * @return a {@link StringPropertyvalue} instance
     */
    static public Value toStringValue(String stringValue) {
        return new StringPropertyvalue(stringValue);
    }

    /**
     * Create new instances of {@link ComplexPropertyvalue}
     *
     * @param complexValue the value to wrap
     * @return a {@link ComplexPropertyvalue} instance
     */
    static public Value toComplexValue(Propertyset complexValue) {
        return new ComplexPropertyvalue(complexValue);
    }

    /**
     * Create new instances of {@link ReferencePropertyvalue}
     *
     * @param referencedValue the value to wrap
     * @return a {@link ReferencePropertyvalue} instance
     */
    static public Value toReferenceValue(Propertyset referencedValue) {
        return new ReferencePropertyvalue(referencedValue);
    }

    /**
     * Create new instances of {@link ListPropertyvalue}
     *
     * @param listValue the value to wrap
     * @return a {@link ListPropertyvalue} instance
     */
    static Value toListValue(ValueList listValue) {
        return new ListPropertyvalue(listValue);
    }

    /**
     * Create new instances of {@link PropertyvalueArrayList}.
     *
     * @return a new empty instance of {@link PropertyvalueArrayList}.
     */
    static public ValueList newList() {
        return new PropertyvalueArrayList();
    }

}
