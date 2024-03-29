package org.inventory.models.items.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.inventory.models.items.Item;
import org.inventory.models.items.Promotion;
import org.inventory.models.items.appearances.Breakable;
import org.inventory.models.items.appearances.Promotable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("Detergent")
public class Detergent extends Item implements Breakable, Promotable {
    private final List<Promotion> promotions;
    private float discount;
    private boolean discounted;

    public Detergent(Detergent otherDetergent) {
        super(otherDetergent);
        this.discount = otherDetergent.discount;
        this.discounted = otherDetergent.discounted;
        this.promotions = List.copyOf(otherDetergent.promotions);
    }

    public Detergent(String name, float price, float quantity) {
        super("Detergent", name, price, quantity);
        this.promotions = new ArrayList<>();
        this.discount = 0.0f;
        this.discounted = false;
    }

    @JsonCreator
    public Detergent(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("price") float price, @JsonProperty("quantity") float quantity, @JsonProperty("promotions") List<Promotion> promotions, @JsonProperty("discount") float discount, @JsonProperty("discounted") boolean discounted) {
        super(id, "Detergent", name, price, quantity);
        this.discount = discount;
        this.discounted = discounted;
        this.promotions = promotions;
    }

    @Override
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

    public void handleExpiration() {
        if (!promotions.isEmpty() && !this.discounted) {
            boolean noPromo = true;
            for (Promotion promotion : promotions) {
                if (promotion.startDate().isAfter(LocalDate.now()) || promotion.startDate().isEqual(LocalDate.now()) &&
                        promotion.endDate().isBefore(LocalDate.now())) {
                    noPromo = false;
                    participateInPromotion(promotion.promotion());
                }
            }
            if (noPromo && discount > 0) {
                this.discounted = false;
                super.setPrice(super.getPrice() + (super.getPrice() * discount));
            }
        }
    }

    @Override
    public void participateInPromotion(String promotionName) {
        super.setPrice(getPromotionalPrice(promotionName));
    }

    @Override
    public void setNewPromotion(String promotionName, LocalDate startDate, LocalDate endDate, int discount) {
        promotions.add(new Promotion(promotionName, startDate, endDate, discount));
    }

    @Override
    public float getPromotionalPrice(String promotionName) {
        float newPrice = super.getPrice();
        for (Promotion promotion : promotions) {
            if (promotionName.equals(promotion.promotion())) {
                newPrice *= promotion.discount();
                this.discount = promotion.discount();
                this.discounted = true;
                break;
            }
        }
        return newPrice;
    }
}
