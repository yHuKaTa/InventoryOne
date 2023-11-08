package org.inventory.models.items.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.inventory.models.items.Item;
import org.inventory.models.items.Promotion;
import org.inventory.models.items.appearances.Discountable;
import org.inventory.models.items.appearances.Perishable;
import org.inventory.models.items.appearances.Promotable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("Animal Product")
public class AnimalProduct extends Item implements Discountable, Promotable, Perishable {
    private float discount;
    private boolean discounted;
    private LocalDate dateOfExpiration;
    private final List<Promotion> promotions;

    public AnimalProduct(AnimalProduct otherProduct) {
        super(otherProduct);
        this.discount = otherProduct.discount;
        this.discounted = otherProduct.discounted;
        this.dateOfExpiration = LocalDate.from(otherProduct.dateOfExpiration);
        this.promotions = List.copyOf(otherProduct.promotions);
    }

    public AnimalProduct(String name, float price, float quantity, LocalDate dateOfExpiration) {
        super("Animal Product", name, price, quantity);
        this.dateOfExpiration = dateOfExpiration;
        this.discount = 0.0f;
        this.discounted = false;
        this.promotions = new ArrayList<>();
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AnimalProduct(@JsonProperty("id") long id, @JsonProperty("name") String name, @JsonProperty("price") float price, @JsonProperty("quantity") float quantity, @JsonProperty("discount") float discount, @JsonProperty("dateOfExpiration") LocalDate dateOfExpiration, @JsonProperty("promotions") List<Promotion> promotions, @JsonProperty("discounted") boolean discounted) {
        super(id, "Animal Product", name, price, quantity);
        this.discount = discount;
        this.discounted = discounted;
        this.dateOfExpiration = dateOfExpiration;
        this.promotions = promotions;
    }

    public boolean isDiscounted() {
        return discounted;
    }

    public float getDiscount() {
        return discount;
    }

    public LocalDate getDateOfExpiration() {
        return dateOfExpiration;
    }

    @Override
    public List<Promotion> getPromotions() {
        return promotions;
    }

    @Override
    public void applyDiscount(int discountPercentage) {
        super.setPrice(super.getPrice() - (super.getPrice() * ((discountPercentage * 1.0f) / 100)));
        discounted = true;
    }

    @Override
    public void setDiscount(int discountPercentage) {
        this.discount = discountPercentage;
    }

    @Override
    public void resetPrice() {
        super.setPrice(super.getPrice() + (super.getPrice() * this.discount));
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
        if (dateOfExpiration.isEqual(LocalDate.now().plusDays(3)) || dateOfExpiration.isBefore(LocalDate.now().plusDays(3)) && !discounted) {
            applyDiscount(30);
            this.discount = 30;
        } else if (dateOfExpiration.isAfter(LocalDate.now()) && discounted) {
            discounted = false;
            resetPrice();
        } else if (!promotions.isEmpty()) {
            boolean noPromo = true;
            for (Promotion promotion : promotions) {
                if (promotion.startDate().isAfter(LocalDate.now()) || promotion.startDate().isEqual(LocalDate.now()) &&
                        promotion.endDate().isBefore(LocalDate.now())) {
                    noPromo = false;
                    participateInPromotion(promotion.promotion());
                    discounted = true;
                }
            }
            if (noPromo) {
                discounted = false;
                resetPrice();
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
                setDiscount((int) promotion.discount());
                break;
            }
        }
        return newPrice;
    }
}

