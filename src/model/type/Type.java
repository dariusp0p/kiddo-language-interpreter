package model.type;

import model.value.Value;

public interface Type {
    boolean isInstance(Object another);
    Value getDefaultValue();
    String toString();
}
