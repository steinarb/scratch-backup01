package no.priv.bang.modeling.modelstore;

import java.util.List;

public interface ValueList extends List<Value> {

    void set(int i, Boolean value);
    void set(int i, boolean value);
    void set(int i, Long value);
    void set(int i, long value);
    void set(int i, Double value);
    void set(int i, double value);
    void set(int i, String value);
    void set(int i, Propertyset value);
    void set(int i, ValueList value);
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

