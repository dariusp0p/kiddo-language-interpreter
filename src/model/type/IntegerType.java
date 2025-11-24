package model.type;

import model.value.Value;
import model.value.IntegerValue;

public class IntegerType implements Type {
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
        return IntegerType.class.hashCode();
    }
}
