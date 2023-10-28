package org.inventory.models.items.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.inventory.models.items.Item;
import org.inventory.models.items.Promotion;
import org.inventory.models.items.appearances.Breakable;
import org.inventory.models.items.appearances.Promotable;

import java.util.List;

public class Detergent extends Item implements Breakable, Promotable {
    private List<Promotion> promotions;

    public Detergent(String name, float price, float quantity) {
        super("Detergent", name, price, quantity);
    }

    @JsonCreator
    public Detergent(@JsonProperty("id") long id, @JsonProperty("category") String category, @JsonProperty("name") String name, @JsonProperty("price") float price, @JsonProperty("quantity") float quantity, @JsonProperty("promotions") List<Promotion> promotions) {
        super(id, category, name, price, quantity);
        this.promotions = promotions;
    }

    @JsonProperty("promotions")
    public List<Promotion> getPromotions() {
        return promotions;
    }

    @Override
    public void handleBreakage(int quantity) {
        super.setQuantity(super.getQuantity() - quantity);
    }

    @Override
    public void participateInPromotion(String promotionName, String period) {
        super.setPrice(getPromotionalPrice(promotionName));
    }

    @Override
    public void setNewPromotion(String promotionName, int discount) {
        promotions.add(new Promotion(promotionName, (discount * 0.01f)));
    }

    @Override
    public float getPromotionalPrice(String promotionName) {
        float newPrice = super.getPrice();
        for (Promotion promotion : promotions) {
            if (promotionName.equals(promotion.promotion())) {
                newPrice *= promotion.discount();
            }
        }
        return newPrice;
    }
}
