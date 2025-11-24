package model.value;

import model.type.Type;
import model.type.ReferenceType;

public record ReferenceValue(int address, Type locationType) implements Value {
    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }
}
