package model.type;

import model.value.Value;
import model.value.StringValue;

public class StringType implements Type {
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
        return StringType.class.hashCode();
    }
}
