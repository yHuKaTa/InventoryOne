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
    private boolean discounted = false;
    private float discount = 0.0f;
    public DoughProduct(DoughProduct otherDough) {
        super(otherDough);
        this.discount = otherDough.discount;
        this.discounted = otherDough.discounted;
        this.dateOfExpiration = LocalDate.from(otherDough.dateOfExpiration);
        this.promotions = List.copyOf(otherDough.promotions);
    }

    public DoughProduct(String name, float price, float quantity) {
        super("Dough Product", name, price, quantity);
        this.promotions = new ArrayList<>();
    }

    @JsonCreator
    public DoughProduct(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("price") float price, @JsonProperty("quantity") float quantity, @JsonProperty("dateOfExpiration") LocalDate dateOfExpiration, @JsonProperty("promotions") List<Promotion> promotions, @JsonProperty("discount") float discount, @JsonProperty("discounted") boolean discounted) {
        super(id, "Dough Product", name, price, quantity);
        this.dateOfExpiration = dateOfExpiration;
        this.promotions = promotions;
        this.discount = discount;
        this.discounted = discounted;
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
        if (dateOfExpiration.isEqual(LocalDate.now()) && !this.discounted) {
            super.setQuantity((float)Math.floor(super.getQuantity() * 0.7f));
        } else if (dateOfExpiration.isAfter(LocalDate.now()) && this.discounted) {
            discounted = false;
        } else if (!promotions.isEmpty() && !this.discounted) {
            boolean noPromo = true;
            for (Promotion promotion : promotions) {
                if (promotion.startDate().isAfter(LocalDate.now()) || promotion.startDate().isEqual(LocalDate.now()) &&
                        promotion.endDate().isBefore(LocalDate.now())) {
                    noPromo = false;
                    participateInPromotion(promotion.promotion());
                }
            }
            if (noPromo && discount > 0) {
                super.setPrice((super.getPrice() / discount));
                this.discounted = false;
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
