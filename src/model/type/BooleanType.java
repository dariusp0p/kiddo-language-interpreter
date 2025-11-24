package model.type;

import model.value.BooleanValue;
import model.value.Value;

public class BooleanType implements Type {
    @Override
    public boolean isInstance(Object another) {
        return another instanceof BooleanValue;
    }

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
