package model.type;

import model.value.ReferenceValue;
import model.value.Value;

public class ReferenceType implements Type {
    private final Type inner;

    public ReferenceType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public boolean isInstance(Object another) {
        if (another instanceof ReferenceValue) {
            return inner.isInstance(((ReferenceValue) another).locationType());
        }
        return false;
    }

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
        if (!(other instanceof ReferenceType)) return false;
        ReferenceType that = (ReferenceType) other;
        return this.inner.equals(that.inner);
    }

    @Override
    public int hashCode() {
        return 31 * inner.hashCode();
    }
}
