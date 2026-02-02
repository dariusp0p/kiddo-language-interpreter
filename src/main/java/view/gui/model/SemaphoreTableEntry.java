package view.gui.model;

public class SemaphoreTableEntry {
    private final Integer index;
    private final Integer value;
    private final String owners;

    public SemaphoreTableEntry(Integer index, Integer value, String owners) {
        this.index = index;
        this.value = value;
        this.owners = owners;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getValue() {
        return value;
    }

    public String getOwners() {
        return owners;
    }

    @Override
    public String toString() {
        return "SemaphoreTableEntry{" +
                "index=" + index +
                ", value=" + value +
                ", owners='" + owners + '\'' +
                '}';
    }
}
