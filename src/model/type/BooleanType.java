package model.type;

import model.value.Value;
import model.value.BooleanValue;

public class BooleanType implements Type {
    @Override
    public Value getDefaultValue() {
        return new BooleanValue(false);
    }

    @Override
    public String toString() {
        return "Boolean";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BooleanType;
    }

    @Override
    public int hashCode() {
        return BooleanType.class.hashCode();
    }
}
