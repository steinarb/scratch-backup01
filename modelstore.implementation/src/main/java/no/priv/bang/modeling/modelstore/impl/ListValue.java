package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertyvalueList;

class ListPropertyvalue extends PropertyvalueBase {

    private PropertyvalueList value;

    /**
     * Create a new instance of {@link ListPropertyvalue}.
     *
     * @param value the value to wrap
     * @deprecated use {@link Propertyvalues#toListValue(PropertyvalueList)} instead
     */
    public ListPropertyvalue(PropertyvalueList value) {
    	if (null == value) {
            this.value = PropertyvalueNil.getNil().asList();
    	} else {
            this.value = value;
    	}
    }

    public Boolean asBoolean() {
        return PropertyvalueNil.getNil().asBoolean();
    }

    public Long asLong() {
        return PropertyvalueNil.getNil().asLong();
    }

    public Double asDouble() {
        return PropertyvalueNil.getNil().asDouble();
    }

    public String asString() {
        return PropertyvalueNil.getNil().asString();
    }

    public Propertyset asComplexProperty() {
        return PropertyvalueNil.getNil().asComplexProperty();
    }

    public Propertyset asReference() {
        return PropertyvalueNil.getNil().asReference();
    }

    @Override
    public boolean isList() {
        return true;
    }

    public PropertyvalueList asList() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof PropertyvalueNil) {
            // If this list is empty test equal to a nil property value.
            // If not empty, they are not equal
            return value.isEmpty();
        }

        if (obj == null) {
            return false;
        }


        if (getClass() != obj.getClass()) {
            return false;
        }

        ListPropertyvalue other = (ListPropertyvalue) obj;
        return value.equals(other.value);
    }

    @Override
    public String toString() {
        return "ListPropertyvalue [value=" + value + "]";
    }

}
