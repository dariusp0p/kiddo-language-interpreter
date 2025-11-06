package model.type;

import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;
import model.value.Value;

public enum Type {
    NONE,
    INTEGER,
    BOOLEAN,
    STRING;

    public boolean isInstance(Object another) {
        return switch (this) {
            case NONE -> another == Value.NONE;
            case INTEGER -> another instanceof IntegerValue;
            case BOOLEAN -> another instanceof BooleanValue;
            case STRING -> another instanceof StringValue;
        };
    }

    public Value getDefaultValue() {
        return switch (this) {
            case NONE -> Value.NONE;
            case INTEGER -> new IntegerValue(0);
            case BOOLEAN -> new BooleanValue(false);
            case STRING -> new StringValue("");
        };
    }

    public String toString() {
        return switch (this) {
            case NONE -> "None";
            case INTEGER -> "Integer";
            case BOOLEAN -> "Boolean";
            case STRING -> "String";
        };
    }
}
