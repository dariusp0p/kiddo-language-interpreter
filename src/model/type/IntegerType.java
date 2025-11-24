package model.type;

import model.value.IntegerValue;
import model.value.Value;

public class IntegerType implements Type {
    @Override
    public boolean isInstance(Object another) {
        return another instanceof IntegerValue;
    }

    @Override
    public Value getDefaultValue() {
        return new IntegerValue(0);
    }

    @Override
    public String toString() {
        return "Integer";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof IntegerType;
    }

    @Override
    public int hashCode() {
        return Integer.class.hashCode();
    }
}
