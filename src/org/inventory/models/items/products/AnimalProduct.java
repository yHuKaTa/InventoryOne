package org.inventory.models.items.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.inventory.models.items.Item;
import org.inventory.models.items.Promotion;
import org.inventory.models.items.appearances.Discountable;
import org.inventory.models.items.appearances.Perishable;
import org.inventory.models.items.appearances.Promotable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnimalProduct extends Item implements Discountable, Promotable, Perishable {
    private float discount = 0.0f;
    private LocalDate dateOfExpiration;
    private List<Promotion> promotions;

    public AnimalProduct(String name, float price, float quantity, LocalDate dateOfExpiration) {
        super("Animal Product", name, price, quantity);
        this.dateOfExpiration = dateOfExpiration;
    }

    public AnimalProduct(@JsonProperty("id") long id, @JsonProperty("category") String category, @JsonProperty("name") String name, @JsonProperty("price") float price, @JsonProperty("quantity") float quantity, @JsonProperty("discount") float discount, @JsonProperty("dateOfExpiration") LocalDate dateOfExpiration, @JsonProperty("promotions") List<Promotion> promotions) {
        super(id, category, name, price, quantity);
        this.discount = discount;
        this.dateOfExpiration = dateOfExpiration;
        this.promotions = promotions;
    }

    @JsonProperty("discount")
    public float getDiscount() {
        return discount;
    }

    @JsonProperty("dateOfExpiration")
    public LocalDate getDateOfExpiration() {
        return dateOfExpiration;
    }

    @JsonProperty("promotions")
    public List<Promotion> getPromotions() {
        return promotions;
    }

    @Override
    public void applyDiscount(int discountPercentage) {
        super.setPrice(super.getPrice() * discount);
    }

    @Override
    public void setDiscount(int discountPercentage) {
        this.discount = (discountPercentage * 1.0f / 100);
    }

    @Override
    public void resetPrice() {
        super.setPrice(super.getPrice() / discount);
    }

    @Override
    public void setExpiration(String dateOfExpiration) {
        LocalDate date = LocalDate.parse(dateOfExpiration);
        if (date.isAfter(LocalDate.now())) {
            this.dateOfExpiration = date;
        } else {
            System.out.println("Insert valid date of expiration!");
        }
    }

    @Override
    public void handleExpiration() {
        if (dateOfExpiration.isEqual(LocalDate.now().plusDays(3))) {
            applyDiscount(30);
        }
    }

    @Override
    public void participateInPromotion(String promotionName, String period) {
        setExpiration(period);
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

