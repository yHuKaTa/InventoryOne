package org.inventory.models.items.appearances;

public interface Sellable {
    void setPrice(float price);

    float getPrice();

    float getTotalPrice(float quantity);
}
