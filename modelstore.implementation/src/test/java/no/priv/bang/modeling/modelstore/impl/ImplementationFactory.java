package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;

/**
 * Static class making the package private implementation classes
 * available to unit tests not in this package.
 *
 * @author Steinar Bang
 *
 */
public class ImplementationFactory {

    static public Propertyvalue newBooleanPropertyvalue(boolean value) {
        return new BooleanPropertyvalue(Boolean.valueOf(value));
    }

    static public Propertyvalue newBooleanPropertyvalue(Boolean value) {
        return new BooleanPropertyvalue(value);
    }

    static public Propertyvalue newLongPropertyvalue(long value) {
        return new LongPropertyvalue(Long.valueOf(value));
    }

    static public Propertyvalue newLongPropertyvalue(Long value) {
        return new LongPropertyvalue(value);
    }

    static public Propertyvalue newDoublePropertyvalue(double value) {
        return new DoublePropertyvalue(Double.valueOf(value));
    }

    static public Propertyvalue newDoublePropertyvalue(Double value) {
        return new DoublePropertyvalue(value);
    }

    static public Propertyvalue newStringPropertyvalue(String value) {
        return new StringPropertyvalue(value);
    }

    static public PropertyvalueList newList() {
    	return new PropertyvalueArrayList();
    }

}
