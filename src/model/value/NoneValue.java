package model.value;

import model.type.Type;
import model.type.NoneType;

public final class NoneValue implements Value {
    public static final NoneValue INSTANCE = new NoneValue();

    private NoneValue() {}

    public Object getValue() { return null; }

    @Override
    public Type getType() { return new NoneType(); }

    @Override
    public String toString() { return "None"; }
}
