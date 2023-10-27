package org.inventory.models.orders;

public enum Status {
    ACTIVE("Active"),
    CANCELED("Canceled"),
    FINISHED("Finished");
    public final String label;

    Status(String label) {
        this.label = label;
    }
}
