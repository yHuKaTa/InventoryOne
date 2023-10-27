package org.inventory.models.items.appearances;

public interface Promotable {
    void participateInPromotion(String promotionName, String period);
    void setNewPromotion(String promotionName, int discount);
    float getPromotionalPrice(String promotionName);
}
