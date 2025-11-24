package model.type;

import model.value.StringValue;
import model.value.Value;

public class StringType implements Type {
    @Override
    public boolean isInstance(Object another) {
        return another instanceof StringValue;
    }

    @Override
    public Value getDefaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "String";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StringType;
    }

    @Override
    public int hashCode() {
        return String.class.hashCode();
    }
}
