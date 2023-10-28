package org.inventory.models.items.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.inventory.models.items.Item;
import org.inventory.models.items.Promotion;
import org.inventory.models.items.appearances.Perishable;
import org.inventory.models.items.appearances.Promotable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("Dough Product")
public class DoughProduct extends Item implements Perishable, Promotable {
    private LocalDate dateOfExpiration;
    private final List<Promotion> promotions;

    public DoughProduct(DoughProduct otherDough) {
        super(otherDough);
        this.dateOfExpiration = LocalDate.from(otherDough.dateOfExpiration);
        this.promotions = List.copyOf(otherDough.promotions);
    }

    public DoughProduct(String name, float price, float quantity) {
        super("Dough Product", name, price, quantity);
        this.promotions = new ArrayList<>();
    }

    @JsonCreator
    public DoughProduct(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("price") float price, @JsonProperty("quantity") float quantity, @JsonProperty("dateOfExpiration") LocalDate dateOfExpiration, @JsonProperty("promotions") List<Promotion> promotions) {
        super(id, "Dough Product", name, price, quantity);
        this.dateOfExpiration = dateOfExpiration;
        this.promotions = promotions;
    }

    public LocalDate getDateOfExpiration() {
        return dateOfExpiration;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    @Override
    public void setExpiration(String dateOfExpiration) {
        LocalDate date = LocalDate.parse(dateOfExpiration);
        if (date.isAfter(LocalDate.now().plusDays(3))) {
            this.dateOfExpiration = date;
        } else {
            System.out.println("Insert date for promotion at least three days!");
        }
    }

    @Override
    public void handleExpiration() {
        if (dateOfExpiration.isEqual(LocalDate.now())) {
            super.setQuantity((float)Math.floor(super.getQuantity() * 0.7f));
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
