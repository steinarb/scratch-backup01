package no.priv.bang.modeling.modelstore;

import java.util.List;

public interface ValueList extends List<Value> {

    Value set(int i, Boolean value);
    Value set(int i, boolean value);
    Value set(int i, Long value);
    Value set(int i, long value);
    Value set(int i, Double value);
    Value set(int i, double value);
    Value set(int i, String value);
    Value set(int i, Propertyset value);
    Value set(int i, ValueList value);
    void add(Boolean value);
    void add(boolean value);
    void add(Long value);
    void add(long value);
    void add(Double value);
    void add(double value);
    void add(String value);
    void add(Propertyset value);
    void add(ValueList value);

}

