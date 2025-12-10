package view.gui.model;

import javafx.beans.property.SimpleStringProperty;

public class SymbolTableEntry {
    private final SimpleStringProperty variableName;
    private final SimpleStringProperty value;

    public SymbolTableEntry(String variableName, String value) {
        this.variableName = new SimpleStringProperty(variableName);
        this.value = new SimpleStringProperty(value);
    }

    public String getVariableName() {
        return variableName.get();
    }

    public String getValue() {
        return value.get();
    }
}
