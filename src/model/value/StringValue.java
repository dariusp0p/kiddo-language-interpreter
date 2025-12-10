package model.value;

import model.type.Type;
import model.type.StringType;

public record StringValue(String value) implements Value {
    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return value();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value());
    }
}
