package model.value;

import model.type.Type;
import model.type.IntegerType;

public record IntegerValue(int value) implements Value {
    @Override
    public Type getType() {
        return new IntegerType();
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
