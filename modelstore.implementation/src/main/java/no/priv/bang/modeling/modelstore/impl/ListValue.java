package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.ValueList;

class ListPropertyvalue extends ValueBase {

    private ValueList value;

    /**
     * Create a new instance of {@link ListPropertyvalue}.
     *
     * @param value the value to wrap
     * @deprecated use {@link Values#toListValue(ValueList)} instead
     */
    public ListPropertyvalue(ValueList value) {
    	if (null == value) {
            this.value = NilValue.getNil().asList();
    	} else {
            this.value = value;
    	}
    }

    public Boolean asBoolean() {
        return NilValue.getNil().asBoolean();
    }

    public Long asLong() {
        return NilValue.getNil().asLong();
    }

    public Double asDouble() {
        return NilValue.getNil().asDouble();
    }

    public String asString() {
        return NilValue.getNil().asString();
    }

    public Propertyset asComplexProperty() {
        return NilValue.getNil().asComplexProperty();
    }

    public Propertyset asReference() {
        return NilValue.getNil().asReference();
    }

    @Override
    public boolean isList() {
        return true;
    }

    public ValueList asList() {
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

        if (obj instanceof NilValue) {
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
