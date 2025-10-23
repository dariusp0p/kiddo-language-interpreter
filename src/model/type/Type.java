package model.type;

import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;

public enum Type {
    NONE,
    INTEGER,
    BOOLEAN;

    public boolean isInstance(Object another) {
        return switch (this) {
            case NONE -> another == Value.NONE;
            case INTEGER -> another instanceof IntegerValue;
            case BOOLEAN -> another instanceof BooleanValue;
        };
    }

    public Value getDefaultValue() {
        return switch (this) {
            case NONE -> Value.NONE;
            case INTEGER -> new IntegerValue(0);
            case BOOLEAN -> new BooleanValue(false);
        };
    }

    public String toString() {
        return switch (this) {
            case NONE -> "None";
            case INTEGER -> "Integer";
            case BOOLEAN -> "Boolean";
        };
    }
}
