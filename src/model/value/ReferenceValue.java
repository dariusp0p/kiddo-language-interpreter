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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ReferenceValue(int address1, Type type))) {
            return false;
        }
        return address == address1 && locationType.equals(type);
    }
}
