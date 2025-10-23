package model.value;

import model.type.Type;

public final class NoneValue implements Value {
    public static final NoneValue INSTANCE = new NoneValue();
    private NoneValue() {}

    public Object getValue() { return null; }

    @Override
    public Type getType() { return Type.NONE; }

    @Override
    public String toString() { return "None"; }
}
