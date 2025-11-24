package model.type;

import model.value.Value;

public class NoneType implements Type {
    @Override
    public boolean isInstance(Object another) {
        return another == Value.NONE;
    }

    @Override
    public Value getDefaultValue() {
        return Value.NONE;
    }

    @Override
    public String toString() {
        return "None";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof NoneType;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
