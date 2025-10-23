package model.value;

import model.type.Type;

public interface Value {
    Type getType();
    String toString();

    Value NONE = NoneValue.INSTANCE;
}
