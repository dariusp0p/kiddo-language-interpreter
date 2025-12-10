package model.type;

import model.value.Value;
import model.value.ReferenceValue;

import java.util.Objects;

public class ReferenceType implements Type {
    private final Type inner;

    public ReferenceType(Type inner) { this.inner = inner; }
    public Type getInner() { return inner; }

    @Override
    public Value getDefaultValue() {
        return new ReferenceValue(0, inner);
    }

    @Override
    public String toString() {
        return "Reference(" + inner.toString() + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ReferenceType that)) return false;
        return this.inner.equals(that.inner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inner);
    }
}
