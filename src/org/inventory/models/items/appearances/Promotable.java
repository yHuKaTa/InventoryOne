package org.inventory.models.items.appearances;

import java.time.LocalDate;

public interface Promotable {
    void participateInPromotion(String promotionName);
    void setNewPromotion(String promotionName, LocalDate startDate, LocalDate endDate, int discount);
    float getPromotionalPrice(String promotionName);
}
