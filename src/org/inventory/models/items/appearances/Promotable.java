package org.inventory.models.items.appearances;

import org.inventory.models.items.Promotion;

import java.time.LocalDate;
import java.util.List;

public interface Promotable {
    void participateInPromotion(String promotionName);
    void setNewPromotion(String promotionName, LocalDate startDate, LocalDate endDate, int discount);
    List<Promotion> getPromotions();
    float getPromotionalPrice(String promotionName);
}
