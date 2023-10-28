package org.inventory.models.items.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.inventory.models.items.Item;
import org.inventory.models.items.Promotion;
import org.inventory.models.items.appearances.Breakable;
import org.inventory.models.items.appearances.Promotable;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("Detergent")
public class Detergent extends Item implements Breakable, Promotable {
    private final List<Promotion> promotions;

    public Detergent(Detergent otherDetergent) {
        super(otherDetergent);
        this.promotions = List.copyOf(otherDetergent.promotions);
    }

    public Detergent(String name, float price, float quantity) {
        super("Detergent", name, price, quantity);
        this.promotions = new ArrayList<>();
    }

    @JsonCreator
    public Detergent(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("price") float price, @JsonProperty("quantity") float quantity, @JsonProperty("promotions") List<Promotion> promotions) {
        super(id, "Detergent", name, price, quantity);
        this.promotions = promotions;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    @Override
    public void handleBreakage(int quantity) {
        if (super.getQuantity() < quantity || super.getQuantity() <= 0) {
            System.out.printf("Invalid quantity! Maximum quantity of item is %f\n", super.getQuantity());
        } else {
            super.setQuantity(super.getQuantity() - quantity);
        }
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
                break;
            }
        }
        return newPrice;
    }
}
