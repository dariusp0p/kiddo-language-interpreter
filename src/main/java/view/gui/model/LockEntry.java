package view.gui.model;

public class LockEntry {
    private final Integer location;
    private final String value;

    public LockEntry(Integer location, String value) {
        this.location = location;
        this.value = value;
    }

    public Integer getLocation() {
        return location;
    }

    public String getValue() {
        return value;
    }
}
