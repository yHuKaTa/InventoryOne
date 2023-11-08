package org.inventory.models.items.appearances;

public interface Discountable {
    void applyDiscount(int discountPercentage);

    void setDiscount(int discountPercentage);

    void resetPrice();
}
