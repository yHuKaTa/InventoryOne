package org.inventory.models.items.appearances;

public interface Perishable {
    void setExpiration(String dateOfExpiration);
    void handleExpiration();
}
