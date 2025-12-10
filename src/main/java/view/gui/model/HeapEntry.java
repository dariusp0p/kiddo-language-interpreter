package view.gui.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HeapEntry {
    private final SimpleIntegerProperty address;
    private final SimpleStringProperty value;

    public HeapEntry(Integer address, String value) {
        this.address = new SimpleIntegerProperty(address);
        this.value = new SimpleStringProperty(value);
    }

    public int getAddress() {
        return address.get();
    }

    public String getValue() {
        return value.get();
    }
}
